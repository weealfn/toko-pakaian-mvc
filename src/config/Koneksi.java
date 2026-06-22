package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                // Menghubungkan ke database db_toko_pakaian yang kita buat di phpMyAdmin
                String url = "jdbc:mysql://localhost:3306/db_toko_pakaian";
                String user = "root";
                String pass = ""; // default XAMPP biasanya kosong
                
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                koneksi = DriverManager.getConnection(url, user, pass);
                System.out.println("Koneksi ke Database Berhasil!");
            } catch (SQLException e) {
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }
}