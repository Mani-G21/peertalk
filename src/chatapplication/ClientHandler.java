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
                            receiverOut.println(Server.retrieveContacts(sender));
                        } else if (function.equals("retrieveChatHistory")) {
                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);

                            receiverOut.println(Server.retrieveChatHistory(sender, content));
                        } else if (function.equals("findUser")) {
                            Socket receiverSocket = this.clientSocket;
                            PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                            receiverOut.println(Server.findUsers(content));
                        } else if (function.equals("receiveFile")) {
                            sendFile(receiver, content);
                        }
                    } else {
                        if (function.equals("sendMessage")) {
                            if (Server.isReceiverOnline(receiver)) {
                                Socket receiverSocket = Server.getClientSocket(receiver);
                                PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                                receiverOut.println(XMLHandler.createXML(sender, receiver, "message", content));
                                Server.storeMessage(sender, receiver, content, true, "text");

                            } else {
                                Server.storeMessage(sender, receiver, content, false, "text");
                            }
                        } else if (function.equals("sendFile")) {
                            receiveFile(sender, receiver, content);
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.out.println("Connection with client " + username + " lost.");
            Server.removeUser(username);
        }
    }

    private void receiveFile(String sender, String receiver, String fileName) {
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
            try{
                PrintWriter receiverOut = new PrintWriter(receiverSocket.getOutputStream(), true);
                receiverOut.println("<file><name>" + fileName + "</name><size>" + totalBytes + "</size></file>");
            
            }catch(NullPointerException e){
                System.out.println("User is offline");
            }finally{
                 fos.close();
                Server.insertFileDetails(sender, receiver, fileName, Long.toString(totalBytes));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendFile(String receiver, String fileName) {
        try {

            File f = new File(fileName);
            PrintWriter receiverOut = new PrintWriter(this.clientSocket.getOutputStream(), true);
            receiverOut.println("<fileData><name>" + fileName + "</name></fileData>");

            // Send file to the receiver
            DataOutputStream rdos = new DataOutputStream(this.clientSocket.getOutputStream());
            long size = f.length();
            rdos.writeLong(size);

            FileInputStream fis = new FileInputStream(f);

            byte[] buffer = new byte[64 * 1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                rdos.write(buffer, 0, bytesRead);
            }

            fis.close();
            rdos.flush(); // Ensure all data is sent
            
            System.out.println("File sent to receiver.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
