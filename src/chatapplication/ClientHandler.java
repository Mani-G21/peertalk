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
                    String sender = XMLHandler.extractTag(incomingMessage, "from");
                    String receiver = XMLHandler.extractTag(incomingMessage, "to");
                    String content = XMLHandler.extractTag(incomingMessage, "content");
                    String function = XMLHandler.extractTag(incomingMessage, "contentType");

                    if (receiver.equals("server")) {
                        if (function.equals("contactsRetrieval")) {
                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                            receiverOut.println(Server.retrieveContacts(content));
                        } else if (function.equals("retrieveChatHistory")) {
                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                            Server.setActiveReceiver(sender, content);
                            System.out.println(sender + "is talking to " + content);
                            receiverOut.println(Server.retrieveChatHistory(sender, content));
                        }
                    } else {
                        if (function.equals("sendMessage")) {
                            String activeReceiver = Server.getReceiver(receiver);
                            if (Server.isReceiverOnline(receiver)){
                                if (receiver.equals(activeReceiver)) {
                                    Socket receiverSocket = Server.getClientSocket(receiver);
                                    PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                                    receiverOut.println(XMLHandler.createXML(sender, receiver, "message", content));
                                    Server.storeMessage(sender, receiver, content, true);
                                } else {
                                    Server.storeMessage(sender, receiver, content, false);
                                }
                            }else{
                                Server.storeMessage(sender, receiver, content, false);
                            }

                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Connection with client " + username + " lost.");
            Server.removeUser(username);
        }
    }

}
