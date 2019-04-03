package com.klikeat.p2p.klikeat.model;

import java.util.ArrayList;

public class MakananModel {
    public String nama_produk,deskripsi,kategori,foto
            ,harga,lokasi_penjual,penjual,rating,jumlahUlasan,produk_id;

    ArrayList<UlasanModel> ulasan;

    public MakananModel(String nama_produk, String deskripsi,
                        String kategori, String foto, String harga,
                        String lokasi_penjual, String penjual,
                        String rating, String jumlahUlasan, String produk_id,
                        ArrayList<UlasanModel> ulasan) {
        this.nama_produk = nama_produk;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
        this.foto = foto;
        this.harga = harga;
        this.lokasi_penjual = lokasi_penjual;
        this.penjual = penjual;
        this.rating = rating;
        this.jumlahUlasan = jumlahUlasan;
        this.produk_id = produk_id;
        this.ulasan = ulasan;
    }

    public MakananModel() {
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getLokasi_penjual() {
        return lokasi_penjual;
    }

    public void setLokasi_penjual(String lokasi_penjual) {
        this.lokasi_penjual = lokasi_penjual;
    }

    public String getPenjual() {
        return penjual;
    }

    public void setPenjual(String penjual) {
        this.penjual = penjual;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getJumlahUlasan() {
        return jumlahUlasan;
    }

    public void setJumlahUlasan(String jumlahUlasan) {
        this.jumlahUlasan = jumlahUlasan;
    }

    public String getProduk_id() {
        return produk_id;
    }

    public void setProduk_id(String produk_id) {
        this.produk_id = produk_id;
    }

    public ArrayList<UlasanModel> getUlasan() {
        return ulasan;
    }

    public void setUlasan(ArrayList<UlasanModel> ulasan) {
        this.ulasan = ulasan;
    }
}
