package com.klikeat.p2p.klikeat.model;

public class KeranjangBelanjaProdukModel {
    String fotoProduk,namaProduk,hargaProduk,jumlahPembelian;

    public KeranjangBelanjaProdukModel(String fotoProduk, String namaProduk,
                                       String hargaProduk, String jumlahPembelian) {
        this.fotoProduk = fotoProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.jumlahPembelian = jumlahPembelian;
    }

    public KeranjangBelanjaProdukModel() {
    }

    public String getFotoProduk() {
        return fotoProduk;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getJumlahPembelian() {
        return jumlahPembelian;
    }

    public void setJumlahPembelian(String jumlahPembelian) {
        this.jumlahPembelian = jumlahPembelian;
    }
}
