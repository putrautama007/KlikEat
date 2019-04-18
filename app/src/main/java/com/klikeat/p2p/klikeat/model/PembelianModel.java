package com.klikeat.p2p.klikeat.model;

public class PembelianModel {
    String fotoToko, namaToko,namaProduk,fotoProduk, jumlahPembelian,hargaProduk,hargaPengiriman,catatan,
            subtotal,status,produkId,jasaPengiriaman,lamaPengiriman,produk_id;

    public PembelianModel(String fotoToko, String namaToko, String namaProduk, String fotoProduk,
                          String jumlahPembelian, String hargaProduk, String hargaPengiriman,
                          String catatan, String subtotal, String status, String produkId,
                          String jasaPengiriaman, String lamaPengiriman, String produk_id) {
        this.fotoToko = fotoToko;
        this.namaToko = namaToko;
        this.namaProduk = namaProduk;
        this.fotoProduk = fotoProduk;
        this.jumlahPembelian = jumlahPembelian;
        this.hargaProduk = hargaProduk;
        this.hargaPengiriman = hargaPengiriman;
        this.catatan = catatan;
        this.subtotal = subtotal;
        this.status = status;
        this.produkId = produkId;
        this.jasaPengiriaman = jasaPengiriaman;
        this.lamaPengiriman = lamaPengiriman;
        this.produk_id = produk_id;
    }

    public PembelianModel() {
    }

    public String getProduk_id() {
        return produk_id;
    }

    public void setProduk_id(String produk_id) {
        this.produk_id = produk_id;
    }

    public String getJasaPengiriaman() {
        return jasaPengiriaman;
    }

    public void setJasaPengiriaman(String jasaPengiriaman) {
        this.jasaPengiriaman = jasaPengiriaman;
    }

    public String getLamaPengiriman() {
        return lamaPengiriman;
    }

    public void setLamaPengiriman(String lamaPengiriman) {
        this.lamaPengiriman = lamaPengiriman;
    }

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getJumlahPembelian() {
        return jumlahPembelian;
    }

    public void setJumlahPembelian(String jumlahPembelian) {
        this.jumlahPembelian = jumlahPembelian;
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
