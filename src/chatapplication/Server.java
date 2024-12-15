package chatapplication;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static HashMap<String, Socket> clientInfo = new HashMap<>();

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

    public static Set getClientsName(){
        return clientInfo.keySet();

    }

    public static void removeUser(String user) {
        clientInfo.remove(user);
    }
}