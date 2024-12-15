package chatapplication;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class Server {

    private static HashMap<String, Socket> clientInfo = new HashMap<>();
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

    public static boolean isReceiverValid(String username) {
        return clientInfo.containsKey(username);
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

    public static HashSet retrieveContacts(String email) {
        HashSet<String> users = new HashSet<>();
        PreparedStatement ps;

        String sql = "SELECT id FROM users WHERE email = ?";
        int id = 0;
        boolean userFound = false;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, email);

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
        Timestamp timeStamp;
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
                    + "    id, \n"
                    + "    sender_id, \n"
                    + "    receiver_id, \n"
                    + "    message, \n"
                    + "    status, \n"
                    + "    timestamp\n"
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
            
            while(rs.next()){
                if(rs.getInt(2) == userId1){
                    sender = user1;
                    receiver = user2;
                }else{
                    sender = user2;
                    receiver = user1;
                }
                
                content = rs.getString(4);
                timeStamp = rs.getTimestamp(6);
                status = rs.getString(5);
                
                message.append(XMLHandler.createXML(sender, receiver, "message", content)).append("\n");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Messages : " + message);
        return message.toString();

    }

}
