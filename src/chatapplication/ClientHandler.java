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
        try (BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true)) {

            while (true) {
                String incomingMessage = clientIn.readLine();

                if (incomingMessage != null) {


                    String receiver = XMLHandler.extractTag(incomingMessage, "to");
                    String message = XMLHandler.extractTag(incomingMessage, "content");

                    if(receiver.equals("server")){
                        Socket receiverSocket = this.clientSocket;
                        PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);

                        receiverOut.println(Server.getClientsName());

                    }
                    if (receiver != null && message != null && Server.isReceiverValid(receiver)) {


                        Socket receiverSocket = Server.getClientSocket(receiver);
                        PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);

                        
                        String xmlResponse = XMLHandler.createXML(username, receiver, message);
                        receiverOut.println(xmlResponse);

                    } else if (receiver == null || message == null) {
                        clientOut.println(XMLHandler.createXML("server", username, "Error: Invalid XML format."));
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

