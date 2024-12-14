/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author manib
 */
public class MySQLConnect {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            String connectionString = "jdbc:mysql://localhost:3307/peertalk";
            try {
                connection = DriverManager.getConnection(connectionString, "root", "sqlDBMS21");
            } catch (SQLException ex) {
                String msg = "Could not connect to the db " + ex.getMessage();
                JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return connection;
    }

}
