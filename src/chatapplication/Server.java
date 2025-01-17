package chatapplication;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

public class Server {

    private static HashMap<String, Socket> clientInfo = new HashMap<>();
    private static HashMap<String, String> currentReceiver = new HashMap<>();
    private static Connection connection = MySQLConnect.getConnection();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8761)) {
            System.out.println("Server is waiting for clients to connect...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected. Waiting for username...");

                BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String username = clientIn.readLine();
                System.out.println("Username assigned to client at port " + clientSocket.getPort() + ": " + username);

                clientInfo.put(username, clientSocket);
                new ClientHandler(clientSocket, username);
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static String getReceiver(String sender) {
        System.out.println(currentReceiver);
        return currentReceiver.get(sender);
    }

    public static boolean isReceiverOnline(String receiver) {
        return clientInfo.containsKey(receiver);
    }

    public static Socket getClientSocket(String username) {
        return clientInfo.get(username);
    }

    public static Set getClientsName() {
        return clientInfo.keySet();

    }

    public static void removeUser(String user) {
        clientInfo.remove(user);
    }

    public static HashSet retrieveContacts(String username) {
        HashSet<String> users = new HashSet<>();
        PreparedStatement ps;

        String sql = "SELECT id FROM users WHERE username = ?";
        int id = 0;
        boolean userFound = false;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
                userFound = true;
            } else {
                System.out.println("No user found");
            }

            if (userFound) {
                sql = "SELECT \n"
                        + "    IF(user1_id = ?, user2_id, user1_id) AS contact_id,\n"
                        + "    IF(user1_id = ?, \n"
                        + "        (SELECT username FROM users WHERE id = user2_id), \n"
                        + "        (SELECT username FROM users WHERE id = user1_id)) AS contact_name,\n"
                        + "    last_contacted\n"
                        + "FROM contacts\n"
                        + "WHERE user1_id = ? OR user2_id = ?";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setInt(2, id);
                ps.setInt(3, id);
                ps.setInt(4, id);
                rs = ps.executeQuery();

                while (rs.next()) {
                    users.add(rs.getString(2));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static String retrieveChatHistory(String user1, String user2) {
        String sender, receiver, contentType, content, status;
        StringBuilder message = new StringBuilder("");
        
        String sql = "SELECT id FROM users WHERE username = ?";
        int userId1 = 0, userId2 = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user1);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId1 = rs.getInt(1);
            }

            ps.setString(1, user2);
            rs = ps.executeQuery();

            if (rs.next()) {
                userId2 = rs.getInt(1);
            }

            sql = "SELECT \n"
                    + "* \n"
                    + "FROM messages\n"
                    + "WHERE (sender_id = ? AND receiver_id = ?) \n"
                    + "   OR (sender_id = ? AND receiver_id = ?)\n"
                    + "ORDER BY timestamp;";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId1);
            ps.setInt(2, userId2);
            ps.setInt(3, userId2);
            ps.setInt(4, userId1);

            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt(2) == userId1) {
                    sender = user1;
                    receiver = user2;
                } else {
                    sender = user2;
                    receiver = user1;
                }

                content = rs.getString(4);
                
                status = rs.getString(5);
                contentType = rs.getString(6);
                
                if(contentType.equals("file")){
                    sql = "SELECT * FROM files WHERE id = ?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(content));
                    
                    rs = ps.executeQuery();
                    while(rs.next()){
                        content = rs.getString(4) + "_%_" + rs.getString(6);
                    }
                }
                
                message.append(XMLHandler.createXML(sender, receiver, contentType, content)).append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message.toString();

    }

    
    public static void storeMessage(String sender, String receiver, String message, boolean status, String type) {
        String sql = "SELECT id FROM users WHERE username = ?";
        int senderId = 0, receiverId = 0;
        boolean senderFound = false, receiverFound = false;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, sender);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                senderId = rs.getInt(1);
                senderFound = true;
            }

            ps.setString(1, receiver);
            rs = ps.executeQuery();

            if (rs.next()) {
                receiverId = rs.getInt(1);
                receiverFound = true;
            }

            if (senderFound && receiverFound) {
                sql = "INSERT INTO messages (sender_id, receiver_id, message, status, type)\n"
                        + "VALUES (?, ?, ?, ?, ?)";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, senderId);
                ps.setInt(2, receiverId);
                ps.setString(3, message);
                if (status) {
                    ps.setString(4, "red");
                } else {
                    ps.setString(4, "sent");
                }
                ps.setString(5, type);
                ps.execute();

                sql = "INSERT INTO contacts (user1_id, user2_id, last_contacted)\n"
                        + "VALUES (LEAST(?, ?), GREATEST(?, ?), NOW())\n"
                        + "ON DUPLICATE KEY UPDATE\n"
                        + "    last_contacted = NOW();";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, senderId);
                ps.setInt(2, receiverId);
                ps.setInt(3, senderId);
                ps.setInt(4, receiverId);

                ps.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setActiveReceiver(String sender, String receiver) {

        currentReceiver.put(sender, receiver);
        System.out.println(currentReceiver);
    }

    public static HashSet findUsers(String user) {
        HashSet<String> users = new HashSet<>();
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(users);
        return users;
    }

    public static void insertFileDetails(String sender, String receiver, String fileName, String fileSize) {

        String sql = "SELECT id FROM users WHERE username = ?";
        int senderId = 0;
        int receiverId = 0;
        boolean userFound = false;
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, sender);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                senderId = rs.getInt(1);
            }

            ps = connection.prepareStatement(sql);
            ps.setString(1, receiver);

            rs = ps.executeQuery();
            if (rs.next()) {
                receiverId = rs.getInt(1);
            }

            if (senderId != 0 && receiverId != 0) {
                sql = "INSERT INTO files(sender_id, receiver_id, og_file_name, new_file_name, file_size) VALUES (?, ?, ?, ?, ?)";
               ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Add this flag here

                ps.setInt(1, senderId);
                ps.setInt(2, receiverId);
                ps.setString(3, fileName);
                ps.setString(4, Integer.toString(senderId) + "_" + fileName);
                ps.setString(5, fileSize);

                int rowsAffected = ps.executeUpdate();
                long autoIncrementedId = 0;
                if (rowsAffected > 0) {
                    // Retrieve the generated keys
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            // Get the auto-incremented ID
                            autoIncrementedId = generatedKeys.getLong(1);
                            System.out.println("Auto-incremented ID: " + autoIncrementedId);
                        }
                    }
                } else {
                    System.out.println("Insert failed, no ID generated.");
                    return;
                }
                
                Server.storeMessage(sender, receiver, Long.toString(autoIncrementedId), true, "file");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        String sql = "INSERT INTO files(sender_id, receiver_id, og_file_name, new_file_name, file_size) VALUES (?, ?, ?, ?)";

    }
}
