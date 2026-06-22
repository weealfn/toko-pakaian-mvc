package dao;

import config.Koneksi;
import model.Produk;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {
    private Connection connection;

    public ProdukDAO() {
        connection = Koneksi.getKoneksi();
    }

    // 1. FUNGSI TAMBAH DATA (CREATE) - Menggunakan PreparedStatement
    public void insert(Produk p) throws SQLException {
        String sql = "INSERT INTO produk (kode, nama, ukuran, warna, stok) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getKode());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getUkuran());
            ps.setString(4, p.getWarna());
            ps.setInt(5, p.getStok());
            ps.executeUpdate();
        }
    }

    // 2. FUNGSI TAMPILKAN SEMUA DATA (READ)
    public List<Produk> getAll() throws SQLException {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk";
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Produk p = new Produk(
                    rs.getString("kode"),
                    rs.getString("nama"),
                    rs.getString("ukuran"),
                    rs.getString("warna"),
                    rs.getInt("stok")
                );
                list.add(p);
            }
        }
        return list;
    }

    // 3. FUNGSI UBAH DATA (UPDATE) - Menggunakan PreparedStatement
    public void update(Produk p) throws SQLException {
    // Pastikan di ujungnya tertulis WHERE kode = ?
    String sql = "UPDATE produk SET nama = ?, ukuran = ?, warna = ?, stok = ? WHERE kode = ?";
    
    Connection conn = config.Koneksi.getKoneksi();
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, p.getNama());
        ps.setString(2, p.getUkuran());
        ps.setString(3, p.getWarna());
        ps.setInt(4, p.getStok());
        ps.setString(5, p.getKode()); // Mengisi ? terakhir pada WHERE kode
        ps.executeUpdate();
    }
}

    // 4. FUNGSI HAPUS DATA (DELETE) - Menggunakan PreparedStatement
    public void delete(String kode) throws SQLException {
    // Pastikan bertuliskan WHERE kode = ?
    String sql = "DELETE FROM produk WHERE kode = ?"; 
    
    Connection conn = config.Koneksi.getKoneksi();
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, kode);
        ps.executeUpdate();
    }
}

    // 5. FITUR CARI DATA (Poin Tambahan Pencarian)
    public List<Produk> search(String keyword) throws SQLException {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk WHERE nama LIKE ? OR kode LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Produk p = new Produk(
                        rs.getString("kode"),
                        rs.getString("nama"),
                        rs.getString("ukuran"),
                        rs.getString("warna"),
                        rs.getInt("stok")
                    );
                    list.add(p);
                }
            }
        }
        return list;
    }
}