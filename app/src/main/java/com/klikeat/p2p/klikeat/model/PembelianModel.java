package com.klikeat.p2p.klikeat.model;

public class PembelianModel {
    String fotoToko, namaToko,namaProduk,fotoProduk,jumlahProduk,hargaProduk,hargaPengiriman,catatan,
            subtotal;

    public PembelianModel(String fotoToko, String namaToko, String namaProduk, String fotoProduk,
                          String jumlahProduk, String hargaProduk, String hargaPengiriman,
                          String catatan, String subtotal) {
        this.fotoToko = fotoToko;
        this.namaToko = namaToko;
        this.namaProduk = namaProduk;
        this.fotoProduk = fotoProduk;
        this.jumlahProduk = jumlahProduk;
        this.hargaProduk = hargaProduk;
        this.hargaPengiriman = hargaPengiriman;
        this.catatan = catatan;
        this.subtotal = subtotal;
    }

    public PembelianModel() {
    }

    public String getFotoToko() {
        return fotoToko;
    }

    public void setFotoToko(String fotoToko) {
        this.fotoToko = fotoToko;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getFotoProduk() {
        return fotoProduk;
    }

    public void setFotoProduk(String fotoProduk) {
        this.fotoProduk = fotoProduk;
    }

    public String getJumlahProduk() {
        return jumlahProduk;
    }

    public void setJumlahProduk(String jumlahProduk) {
        this.jumlahProduk = jumlahProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getHargaPengiriman() {
        return hargaPengiriman;
    }

    public void setHargaPengiriman(String hargaPengiriman) {
        this.hargaPengiriman = hargaPengiriman;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
