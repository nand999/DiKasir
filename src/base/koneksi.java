/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;
import java.sql.*;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author 1nternetslav3
 */
public class koneksi {
        private static java.sql.Connection koneksi;
    
    public static java.sql.Connection getKoneksi() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        if (koneksi == null){
           try {
               String url = "jdbc:mysql://localhost:3306/test_dikasir2";
               String user = "root";
               String password = "";
               
               koneksi = DriverManager.getConnection(url, user,  password);
           } catch (SQLException t){
               System.out.println("error membuat koneksi");
           }
        } return koneksi;
    }
}
