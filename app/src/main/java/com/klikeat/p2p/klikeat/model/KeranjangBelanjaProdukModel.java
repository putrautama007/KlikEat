package com.klikeat.p2p.klikeat.model;

public class KeranjangBelanjaProdukModel {
    String produkId,fotoPenjual, namaToko,fotoProduk,namaProduk,hargaProduk,jumlahPembelian;
    Boolean setSelected;


    public KeranjangBelanjaProdukModel(String produkId, String fotoPenjual, String namaToko,
                                       String fotoProduk, String namaProduk, String hargaProduk, String jumlahPembelian) {
        this.produkId = produkId;
        this.fotoPenjual = fotoPenjual;
        this.namaToko = namaToko;
        this.fotoProduk = fotoProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.jumlahPembelian = jumlahPembelian;
    }

    public KeranjangBelanjaProdukModel(String produkId, String fotoPenjual, String namaToko,
                                       String fotoProduk, String namaProduk, String hargaProduk,
                                       String jumlahPembelian, Boolean setSelected) {
        this.produkId = produkId;
        this.fotoPenjual = fotoPenjual;
        this.namaToko = namaToko;
        this.fotoProduk = fotoProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.jumlahPembelian = jumlahPembelian;
        this.setSelected = setSelected;
    }

    public KeranjangBelanjaProdukModel() {
    }

    public Boolean getSetSelected() {
        return setSelected;
    }

    public void setSetSelected(Boolean setSelected) {
        this.setSelected = setSelected;
    }

    public String getProdukId() {
        return produkId;
    }

    public void setProdukId(String produkId) {
        this.produkId = produkId;
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

    public String getFotoPenjual() {
        return fotoPenjual;
    }

    public void setFotoPenjual(String fotoPenjual) {
        this.fotoPenjual = fotoPenjual;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }
}
