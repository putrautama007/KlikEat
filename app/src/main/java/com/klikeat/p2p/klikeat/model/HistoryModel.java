package com.klikeat.p2p.klikeat.model;

import java.util.ArrayList;

public class HistoryModel {
    String tglTransaksi, idTransaksi, totalPembayaran,fotoToko, namaToko, namaProduk,
            fotoProduk, jumlahProduk, hargaProduk, hargaPengiriman, catatan, subtotal, status,
    jasaPengiriaman,lamaPengiriman,produk_id;

    public HistoryModel(String tglTransaksi, String idTransaksi, String totalPembayaran,
                        String fotoToko, String namaToko, String namaProduk, String fotoProduk,
                        String jumlahProduk, String hargaProduk, String hargaPengiriman,
                        String catatan, String subtotal, String status, String jasaPengiriaman,
                        String lamaPengiriman, String produk_id) {
        this.tglTransaksi = tglTransaksi;
        this.idTransaksi = idTransaksi;
        this.totalPembayaran = totalPembayaran;
        this.fotoToko = fotoToko;
        this.namaToko = namaToko;
        this.namaProduk = namaProduk;
        this.fotoProduk = fotoProduk;
        this.jumlahProduk = jumlahProduk;
        this.hargaProduk = hargaProduk;
        this.hargaPengiriman = hargaPengiriman;
        this.catatan = catatan;
        this.subtotal = subtotal;
        this.status = status;
        this.jasaPengiriaman = jasaPengiriaman;
        this.lamaPengiriman = lamaPengiriman;
        this.produk_id = produk_id;
    }

    public HistoryModel() {
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

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(String tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getTotalPembayaran() {
        return totalPembayaran;
    }

    public void setTotalPembayaran(String totalPembayaran) {
        this.totalPembayaran = totalPembayaran;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
