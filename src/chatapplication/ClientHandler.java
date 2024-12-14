package chatapplication;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final String username;

    public ClientHandler(Socket clientSocket, String username) {
        this.clientSocket = clientSocket;
        this.username = username;
        start();
    }

    @Override
    public void run() {
        try (BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true)) {

            while (true) {
                String incomingMessage = clientIn.readLine();

                if (incomingMessage != null) {
                    String receiver = XMLHandler.extractTag(incomingMessage, "to");
                    String message = XMLHandler.extractTag(incomingMessage, "content");
                    String messageType = XMLHandler.extractTag(incomingMessage, "contentType");

                    if (receiver.equals("server")) {

                        if (messageType.equals("auth")) {
                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);

                            String authResult = Server.authentication(this.username, message);
                            if (authResult.equals("true")) {
                                receiverOut.println("true");
                            } else {
                                receiverOut.println("false");
                                // Optionally close the socket if authentication fails
                                clientSocket.close();
                                break;  // Exit the loop, which will stop further message processing
                            }
                        } else {

                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);

                            receiverOut.println(Server.getClientsName());
                        }

                    }
                     if (receiver != null && message != null && Server.isReceiverValid(receiver)) {

                         Socket receiverSocket = Server.getClientSocket(receiver);
                         PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                         String xmlResponse = XMLHandler.createXML(username, receiver, "message",message);
                         receiverOut.println(xmlResponse);
                     } else if (receiver == null || message == null) {
                         clientOut.println(XMLHandler.createXML("server", username,"error" ,"Error: Invalid XML format."));
                     } else {
                         // clientOut.println(XMLHandler.createXML("server", username, "User " + receiver + " not found."));
                     }
                }
            }
        } catch (IOException e) {
            System.out.println("Connection with client " + username + " lost.");
            Server.removeUser(username);
        }
    }

}
