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

                            receiverOut.println(Server.retrieveChatHistory(sender, content));
                        } else if (function.equals("findUser")) {
                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                            receiverOut.println(Server.findUsers(content));
                        }
                    } else {
                        if (function.equals("sendMessage")) {
                            if (Server.isReceiverOnline(receiver)) {
                                Socket receiverSocket = Server.getClientSocket(receiver);
                                PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                                receiverOut.println(XMLHandler.createXML(sender, receiver, "message", content));
                                Server.storeMessage(sender, receiver, content, true);

                            } else {
                                Server.storeMessage(sender, receiver, content, false);
                            }
                        } else if (function.equals("sendFile")) {
                            handleFileTransfer(sender, receiver, content);
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Connection with client " + username + " lost.");
            Server.removeUser(username);
        }

    }

    private void handleFileTransfer(String sender, String receiver, String fileName) {
        try {
            int bytes = 0;

            DataInputStream dos = new DataInputStream(clientSocket.getInputStream());
            File f = new File(fileName);

            FileOutputStream fos = new FileOutputStream(f);

            long size = dos.readLong();
            long totalBytes = 0;
            byte b[] = new byte[(int) Math.min(size, 64 * 1024)];

            while (size > 0
                    && ((bytes = dos.read(b, 0, (int) Math.min(size, b.length))) != -1)) {
                fos.write(b, 0, bytes);
                size -= bytes;
                totalBytes += bytes;
            }
            System.out.println("Received " + totalBytes + "bytes");

            Socket receiverSocket = Server.getClientSocket(receiver);
            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
            receiverOut.println("<file><name>" + fileName + "</name></file>");
            DataOutputStream rdos = new DataOutputStream(receiverSocket.getOutputStream());

            bytes = 0;
            long totalBytesSent = 0;
            FileInputStream fis = new FileInputStream(f);
            while ((bytes = fis.read(b)) != -1) {
                rdos.write(b, 0, bytes);
                totalBytesSent += bytes;

            }
            System.out.println("sent " + totalBytesSent + "bytes");
            
            dos.close();
            fos.close();
            receiverOut.close();
            rdos.close();
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
