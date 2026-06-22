package controller;

import dao.ProdukDAO;
import model.Produk;
import view.MainForm;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProdukController {
    private final MainForm view;
    private final ProdukDAO dao;

    public ProdukController(MainForm view) {
        this.view = view;
        this.dao = new ProdukDAO();
    }

    // Fungsi membersihkan form input
    public void bersihkanForm() {
        view.getTxtKode().setText("");
        view.getTxtNama().setText("");
        view.getTxtUkuran().setText("");
        view.getTxtWarna().setText("");
        view.getTxtStok().setText("");
        view.getTxtKode().setEditable(true);
    }

    // Menampilkan data database ke JTable di View
    public void tampilkanData() {
        try {
            List<Produk> list = dao.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getTableProduk().getModel();
            model.setRowCount(0); // Reset tabel
            
            for (Produk p : list) {
                model.addRow(new Object[]{p.getKode(), p.getNama(), p.getUkuran(), p.getWarna(), p.getStok()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal memuat data: " + e.getMessage());
        }
    }

    // Fungsi Tambah Data dengan Validasi Penuh (Syarat Nilai 15%)
    public void simpanProduk() {
        String kode = view.getTxtKode().getText();
        String nama = view.getTxtNama().getText();
        String ukuran = view.getTxtUkuran().getText();
        String warna = view.getTxtWarna().getText();
        String strStok = view.getTxtStok().getText();

        // 1. Validasi tidak boleh kosong
        if (kode.isEmpty() || nama.isEmpty() || ukuran.isEmpty() || warna.isEmpty() || strStok.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua kolom input wajib diisi!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Validasi tipe data (Stok harus angka bulat)
        int stok;
        try {
            stok = Integer.parseInt(strStok);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Kolom Stok harus berupa angka/numerik!", "Validasi Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Produk p = new Produk(kode, nama, ukuran, warna, stok);
            dao.insert(p);
            JOptionPane.showMessageDialog(view, "Produk berhasil ditambahkan!");
            tampilkanData();
            bersihkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal menyimpan (Kode kemungkinan sudah ada): " + e.getMessage());
        }
    }

    // 1. FUNGSI UBAH DATA (UPDATE)
    public void ubahProduk() {
        String kode = view.getTxtKode().getText();
        String nama = view.getTxtNama().getText();
        String ukuran = view.getTxtUkuran().getText();
        String warna = view.getTxtWarna().getText();
        String strStok = view.getTxtStok().getText();

        if (kode.isEmpty() || nama.isEmpty() || ukuran.isEmpty() || warna.isEmpty() || strStok.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data pada tabel terlebih dahulu dan pastikan kolom tidak kosong!");
            return;
        }

        int stok;
        try {
            stok = Integer.parseInt(strStok);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Stok harus berupa angka!");
            return;
        }

        try {
            Produk p = new Produk(kode, nama, ukuran, warna, stok);
            dao.update(p);
            JOptionPane.showMessageDialog(view, "Data produk berhasil diubah!");
            tampilkanData();
            bersihkanForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal mengubah data: " + e.getMessage());
        }
    }

    // 2. FUNGSI HAPUS DATA (DELETE)
 public void hapusProduk() {
    String kode = view.getTxtKode().getText();
    
    // Validasi: Jika diketik manual maupun klik tabel, yang penting kolom kode ada isinya
    if (kode.isEmpty()) {
        JOptionPane.showMessageDialog(view, "Pilih data dari tabel atau ketik Kode Produk terlebih dahulu!");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(view, "Apakah Anda yakin ingin menghapus produk dengan kode [" + kode + "]?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (konfirmasi == JOptionPane.YES_OPTION) {
        try {
            dao.delete(kode);
            JOptionPane.showMessageDialog(view, "Produk berhasil dihapus!");
            tampilkanData(); // Menyegarkan isi tabel biar langsung hilang
            bersihkanForm(); // Mengosongkan kembali kolom inputan
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal menghapus data: " + e.getMessage());
        }
    }
}

    // 3. FUNGSI CARI DATA (SEARCH)
    public void cariProduk() {
        String keyword = view.getTxtCari().getText();
        try {
            List<Produk> list = dao.search(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getTableProduk().getModel();
            model.setRowCount(0); // Reset tabel
            
            for (Produk p : list) {
                model.addRow(new Object[]{p.getKode(), p.getNama(), p.getUkuran(), p.getWarna(), p.getStok()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal mencari data: " + e.getMessage());
        }
    }

    // 4. FUNGSI PILIH DATA TABEL
    public void pilihTabel() {
        int row = view.getTableProduk().getSelectedRow();
        if (row != -1) {
            view.getTxtKode().setText(view.getTableProduk().getValueAt(row, 0).toString());
            view.getTxtNama().setText(view.getTableProduk().getValueAt(row, 1).toString());
            view.getTxtUkuran().setText(view.getTableProduk().getValueAt(row, 2).toString());
            view.getTxtWarna().setText(view.getTableProduk().getValueAt(row, 3).toString());
            view.getTxtStok().setText(view.getTableProduk().getValueAt(row, 4).toString());
            view.getTxtKode().setEditable(false);
        }
    }

    // 5. FITUR CETAK LAPORAN
    public void cetakLaporan() {
        try {
            boolean print = view.getTableProduk().print(
                javax.swing.JTable.PrintMode.FIT_WIDTH, 
                new java.text.MessageFormat("LAPORAN DATA STOK TOKO PAKAIAN"), 
                new java.text.MessageFormat("Halaman {0}")
            );
            if (print) {
                JOptionPane.showMessageDialog(view, "Laporan berhasil dicetak / diekspor ke PDF!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal mencetak laporan: " + e.getMessage());
        }
    }
}