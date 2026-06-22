package model;

public class Produk {
    private String kode;
    private String nama;
    private String ukuran;
    private String warna;
    private int stok;

    // Constructor Kosong
    public Produk() {
    }

    // Constructor dengan Parameter lengkap
    public Produk(String kode, String nama, String ukuran, String warna, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.ukuran = ukuran;
        this.warna = warna;
        this.stok = stok;
    }

    // Method Getter dan Setter agar data bisa diambil dan diubah oleh package lain
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getUkuran() { return ukuran; }
    public void setUkuran(String ukuran) { this.ukuran = ukuran; }

    public String getWarna() { return warna; }
    public void setWarna(String warna) { this.warna = warna; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}