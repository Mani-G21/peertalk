/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package chatapplication;

import static chatapplication.ChatScreen.recentChatMainPanel;
import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ProgressBar;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author manib
 */
public class ChatScreen extends javax.swing.JFrame {

    /**
     * Creates new form ChatScreen
     */
    public ChatScreen(String userName, String email) {
        ChatScreen.userName = userName;
        ChatScreen.email = email;
        ChatScreen.contactList = new HashSet<>();
        try {
            socket = new Socket("127.0.0.1", 8761);
            serverOut = new PrintWriter(socket.getOutputStream(), true);
            serverOut.println(userName);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection error: " + e.getMessage());
        }
        initComponents();
        cancelToggleBtn.setVisible(false);
        initializeClients();

        currentChatBodyPanel.setVisible(false);
        currentChatHeader.setVisible(false);
        currentChatLabel.setVisible(false);
        currentChatTxt.setVisible(false);
        sendButton.setVisible(false);
        fileSelectButton.setVisible(false);
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
    }

    private void initializeClients() {
        serverOut.println(XMLHandler.createXML(userName, "server", "contactsRetrieval", email));
        Thread serverListenerThread = new Thread(new ServerListener(socket, chatPanel, recentChatMainPanel, recentChatScrollPane));
        serverListenerThread.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        homeButton = new javax.swing.JLabel();
        logoutButton = new javax.swing.JLabel();
        recentChatScrollPane = new javax.swing.JScrollPane();
        recentChatMainPanel = new javax.swing.JPanel();
        recentChatPanel = new javax.swing.JPanel();
        recentChatLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        currentChatHeader = new javax.swing.JPanel();
        currentChatLabel = new javax.swing.JLabel();
        currentChatBodyPanel = new javax.swing.JScrollPane();
        chatPanel = new javax.swing.JPanel();
        currentChatTxt = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        fileSelectButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        newChatTxt = new javax.swing.JTextField();
        newChatSeparator = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        chatsLabel = new javax.swing.JLabel();
        cancelToggleBtn = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatapplication/homeButtonLogo.jpg"))); // NOI18N
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeButtonMouseClicked(evt);
            }
        });

        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatapplication/logoutButton.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logoutButton)
                    .addComponent(homeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(homeButton)
                .addGap(18, 18, 18)
                .addComponent(logoutButton)
                .addGap(16, 16, 16))
        );

        recentChatScrollPane.setBackground(new java.awt.Color(102, 102, 102));
        recentChatScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        recentChatScrollPane.setHorizontalScrollBar(null);

        recentChatMainPanel.setBackground(new java.awt.Color(255, 255, 255));

        recentChatPanel.setBackground(new java.awt.Color(255, 255, 255));
        recentChatPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        recentChatLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        recentChatLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recentChatLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatapplication/chatLogoPerson.jpg"))); // NOI18N
        recentChatLabel.setText("Manish Ghindwani");
        recentChatLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        recentChatLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recentChatLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout recentChatPanelLayout = new javax.swing.GroupLayout(recentChatPanel);
        recentChatPanel.setLayout(recentChatPanelLayout);
        recentChatPanelLayout.setHorizontalGroup(
            recentChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(recentChatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        recentChatPanelLayout.setVerticalGroup(
            recentChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentChatPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(recentChatLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout recentChatMainPanelLayout = new javax.swing.GroupLayout(recentChatMainPanel);
        recentChatMainPanel.setLayout(recentChatMainPanelLayout);
        recentChatMainPanelLayout.setHorizontalGroup(
            recentChatMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(recentChatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        recentChatMainPanelLayout.setVerticalGroup(
            recentChatMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentChatMainPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(recentChatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(676, Short.MAX_VALUE))
        );

        recentChatScrollPane.setViewportView(recentChatMainPanel);

        currentChatHeader.setBackground(new java.awt.Color(255, 255, 255));

        currentChatLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        currentChatLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentChatLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatapplication/chatLogoPerson.jpg"))); // NOI18N
        currentChatLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout currentChatHeaderLayout = new javax.swing.GroupLayout(currentChatHeader);
        currentChatHeader.setLayout(currentChatHeaderLayout);
        currentChatHeaderLayout.setHorizontalGroup(
            currentChatHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(currentChatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 982, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        currentChatHeaderLayout.setVerticalGroup(
            currentChatHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(currentChatHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentChatLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        currentChatBodyPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1243, Short.MAX_VALUE)
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 794, Short.MAX_VALUE)
        );

        currentChatBodyPanel.setViewportView(chatPanel);

        currentChatTxt.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        currentChatTxt.setToolTipText("");
        currentChatTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentChatTxtActionPerformed(evt);
            }
        });

        sendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatapplication/sendLogo.png"))); // NOI18N
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendButtonMouseClicked(evt);
            }
        });
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        fileSelectButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/chatapplication/fileFolder.png"))); // NOI18N
        fileSelectButton.setText("jButton1");
        fileSelectButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fileSelectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSelectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(currentChatHeader, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(currentChatBodyPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(currentChatTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(fileSelectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(sendButton)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(currentChatHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(currentChatBodyPanel)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(currentChatTxt)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fileSelectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("New Chat");

        newChatTxt.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        newChatTxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        newChatTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newChatTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newChatSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(newChatTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newChatTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(newChatSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        chatsLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        chatsLabel.setText("Chats");

        cancelToggleBtn.setBackground(new java.awt.Color(51, 102, 255));
        cancelToggleBtn.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cancelToggleBtn.setForeground(new java.awt.Color(255, 255, 255));
        cancelToggleBtn.setText("  Cancel");
        cancelToggleBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelToggleBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelToggleBtn.setOpaque(true);
        cancelToggleBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelToggleBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(chatsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(cancelToggleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(chatsLabel)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelToggleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recentChatScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recentChatScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newChatTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newChatTxtActionPerformed
        String newChatPerson = newChatTxt.getText();
        chatsLabel.setText("Found Users");
        cancelToggleBtn.setVisible(true);
        if (!newChatPerson.equals("")) {
            serverOut.println(XMLHandler.createXML(userName, "server", "findUser", newChatPerson));
            System.out.println("I am called");
        }
    }//GEN-LAST:event_newChatTxtActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendButtonActionPerformed

    private void sendButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendButtonMouseClicked
        String message = currentChatTxt.getText();

        if (!message.equalsIgnoreCase("")) {
            JLabel messageLabel = new JLabel("<html><b>You:</b><br>&nbsp;&nbsp;&nbsp;" + message + "</html>");

            messageLabel.setOpaque(true);
            messageLabel.setForeground(Color.BLACK);
            messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            chatPanel.add(messageLabel);
            chatPanel.revalidate();
            chatPanel.repaint();
            currentChatTxt.setText("");
            currentChatTxt.requestFocus();

            String receiver = currentChatLabel.getText();
            serverOut.println(XMLHandler.createXML(userName, receiver, "sendMessage", message));

        }
    }//GEN-LAST:event_sendButtonMouseClicked

    private void currentChatTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentChatTxtActionPerformed
        String message = currentChatTxt.getText();

        if (!message.equalsIgnoreCase("")) {
            JLabel messageLabel = new JLabel("<html><b>You:</b><br>&nbsp;&nbsp;&nbsp;" + message + "</html>");

            messageLabel.setOpaque(true);
            messageLabel.setForeground(Color.BLACK);
            messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
            chatPanel.add(messageLabel);
            chatPanel.revalidate();
            chatPanel.repaint();
            currentChatTxt.setText("");
            currentChatTxt.requestFocus();

            String receiver = currentChatLabel.getText();
            serverOut.println(XMLHandler.createXML(userName, receiver, "sendMessage", message));

        }
    }//GEN-LAST:event_currentChatTxtActionPerformed

    private void recentChatLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recentChatLabelMouseClicked
        handleUI(evt);

    }//GEN-LAST:event_recentChatLabelMouseClicked

    private void homeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseClicked
        currentChatBodyPanel.setVisible(false);
        currentChatHeader.setVisible(false);
        currentChatLabel.setVisible(false);
        currentChatTxt.setVisible(false);
        sendButton.setVisible(false);
        chatPanel.setVisible(false);
        fileSelectButton.setVisible(false);
        chatPanel.removeAll();

        chatPanel.revalidate();
        chatPanel.repaint();
    }//GEN-LAST:event_homeButtonMouseClicked

    private void cancelToggleBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelToggleBtnMouseClicked
        initializeClients();
        newChatTxt.setText("");
        homeButtonMouseClicked(evt);
        chatsLabel.setText("Chats");
        cancelToggleBtn.setVisible(false);
    }//GEN-LAST:event_cancelToggleBtnMouseClicked

    private void fileSelectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSelectButtonActionPerformed
        FileDialog fd = new FileDialog(this, "Select a file", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getDirectory() != null || fd.getFile() != null) {

            sendFile(fd.getDirectory() + fd.getFile(), fd.getFile(), userName, currentChatLabel.getText());
        }

       
    }//GEN-LAST:event_fileSelectButtonActionPerformed

    public static void handleUI(java.awt.event.MouseEvent evt) {
        chatPanel.removeAll();
        chatPanel.revalidate();
        chatPanel.repaint();
        JLabel selectedLabel = (JLabel) evt.getComponent();
        currentChatLabel.setText(selectedLabel.getText());
        currentChatBodyPanel.setVisible(true);
        chatPanel.setVisible(true);
        currentChatHeader.setVisible(true);
        currentChatLabel.setVisible(true);
        currentChatTxt.setVisible(true);
        sendButton.setVisible(true);
        fileSelectButton.setVisible(true);

        String receiver = selectedLabel.getText();
        serverOut.println(XMLHandler.createXML(userName, "server", "retrieveChatHistory", receiver));
    }

    private static boolean sendFile(String filePath, String fileName, String sender, String receiver) {
        File file = new File(filePath);

        int bufferSize = (int) Math.min(file.length(), 64 * 1024);
        byte[] buffer = new byte[bufferSize];
        serverOut.println(XMLHandler.createXML(userName, currentChatLabel.getText(), "sendFile", fileName));

        try {
            FileInputStream fis = new FileInputStream(file);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeLong(file.length());

            int bytesRead;
            int totalBytes = 0;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            System.out.println("sent " + totalBytes + "bytes");
            System.out.println("\nFile transfer complete!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    private static Socket socket;
    static String userName;
    private static String email;
    private static PrintWriter serverOut;
    static HashSet<String> contactList;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cancelToggleBtn;
    public static javax.swing.JPanel chatPanel;
    private javax.swing.JLabel chatsLabel;
    public static javax.swing.JScrollPane currentChatBodyPanel;
    public static javax.swing.JPanel currentChatHeader;
    public static javax.swing.JLabel currentChatLabel;
    public static javax.swing.JTextField currentChatTxt;
    private static javax.swing.JButton fileSelectButton;
    private javax.swing.JLabel homeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel logoutButton;
    private javax.swing.JSeparator newChatSeparator;
    private javax.swing.JTextField newChatTxt;
    private javax.swing.JLabel recentChatLabel;
    public static javax.swing.JPanel recentChatMainPanel;
    private javax.swing.JPanel recentChatPanel;
    private javax.swing.JScrollPane recentChatScrollPane;
    public static javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables

}

class ServerListener implements Runnable {

    private final Socket socket;
    private final JPanel chatPanel;
    private final JPanel recentChatMainPanel;
    private final JScrollPane recentChatScrollPane;

    public ServerListener(Socket socket, JPanel chatPanel, JPanel recentChatMainPanel, JScrollPane recentChatScrollPane) {
        this.socket = socket;
        this.chatPanel = chatPanel;
        this.recentChatMainPanel = recentChatMainPanel;
        this.recentChatScrollPane = recentChatScrollPane;
    }

    @Override
    public void run() {
        try (BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String incomingMessage;

            while ((incomingMessage = serverIn.readLine()) != null) {

                if (incomingMessage.startsWith("[")) {
                    recentChatMainPanel.removeAll();
                    System.out.println("Received user list: " + incomingMessage);
                    updateUserList(incomingMessage);

                } else if (incomingMessage.startsWith("<message>")) {

                    displayChatMessage(incomingMessage);
                } else if (incomingMessage.startsWith("<file>")) {
                
                    receiveFile(incomingMessage, dis);
                }
            }
        } catch (IOException e) {
            System.out.println("Lost connection to server.");
        }
    }

    private void updateUserList(String userListMessage) {

        String userString = "(\\w+)";
        Pattern userPattern = Pattern.compile(userString);
        Matcher userMatcher = userPattern.matcher(userListMessage);

        while (userMatcher.find()) {
            String userName = userMatcher.group(1);
            System.out.println("Adding user to recent chats: " + userName);
            ChatScreen.contactList.add(userName);
            addRecentChat(userName, "/chatapplication/chatLogoPerson.jpg");

        }

        recentChatMainPanel.revalidate();
        recentChatMainPanel.repaint();
    }

    private void displayChatMessage(String message) {
        String messageContent = XMLHandler.extractTag(message, "content");
        String sender;

        if (ChatScreen.userName.equals(XMLHandler.extractTag(message, "from"))) {
            sender = "You";
        } else {
            sender = XMLHandler.extractTag(message, "from");
        }

        if (!sender.equalsIgnoreCase("You") && ChatScreen.contactList.add(sender)) {
            addRecentChat(sender, "/chatapplication/chatLogoPerson.jpg");
        }

        if (ChatScreen.currentChatLabel.getText().equals(sender) || sender.equalsIgnoreCase("You")) {
            if (messageContent != null) {
                JLabel messageLabel = new JLabel("<html><b>" + sender + "</b><br>&nbsp;&nbsp;&nbsp;" + messageContent + "</html>");
                messageLabel.setOpaque(true);
                messageLabel.setForeground(Color.BLACK);
                messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));

                chatPanel.add(messageLabel);
                chatPanel.revalidate();
                chatPanel.repaint();
            } else {
                System.out.println("Error: Could not parse message content.");
            }
        }

    }

    private void addRecentChat(String userName, String iconPath) {
        // Set BoxLayout on recentChatMainPanel for vertical stacking
        if (!(recentChatMainPanel.getLayout() instanceof BoxLayout)) {
            recentChatMainPanel.setLayout(new BoxLayout(recentChatMainPanel, BoxLayout.Y_AXIS));
        }

        // Create a new panel for the recent chat
        JPanel recentChatPanel = new JPanel();
        recentChatPanel.setBackground(new java.awt.Color(255, 255, 255));
        recentChatPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        recentChatPanel.setMaximumSize(new Dimension(397, 100)); // Set consistent size for the panel
        recentChatPanel.setLayout(new BorderLayout()); // Use BorderLayout to make the label fill the entire panel

        // Create and configure the label
        JLabel recentChatLabel = new JLabel();
        recentChatLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // Set font
        recentChatLabel.setHorizontalAlignment(SwingConstants.CENTER);
        recentChatLabel.setText(userName);
        recentChatLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (iconPath != null) {
            recentChatLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        }

        // Add mouse listener to handle click events on the label
        recentChatLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChatScreen.handleUI(evt);
            }
        });

        // Add the label to the center of recentChatPanel to make it fill the entire panel
        recentChatPanel.add(recentChatLabel, BorderLayout.CENTER);

        // Add a vertical space (strut) between panels
        recentChatMainPanel.add(Box.createVerticalStrut(10));

        // Add the recentChatPanel to recentChatMainPanel
        recentChatMainPanel.add(recentChatPanel);

        // Refresh the recentChatMainPanel and scroll pane to reflect the new components
        recentChatMainPanel.revalidate();
        recentChatMainPanel.repaint();

        System.out.println("Added recent chat panel for user: " + userName);
    }

    private void receiveFile(String incomingMessage, DataInputStream dis) {
        String fileName = XMLHandler.extractTag(incomingMessage, "name");
        System.out.println(fileName);
        int bytes = 0;
        try {

            File f = new File("received_" + fileName);

            if (!f.exists()) {
                f.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(f);

            long size = dis.readLong();
            System.out.println(size);
            long totalBytes = 0;
            byte b[] = new byte[(int) Math.min(size, 64 * 1024)];
            
            
            while (size > 0
                    && ((bytes = dis.read(b, 0, (int) Math.min(size, b.length))) != -1)) {
                fos.write(b, 0, bytes);
                size -= bytes;
                totalBytes += bytes;
                System.out.println(size);
               
            }
            System.out.println("Received " + totalBytes + "bytes");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
