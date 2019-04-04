package com.klikeat.p2p.klikeat.model;

public class FavoriteModel {
    public String fotoPrduk,namaProduk,namaPenjual,produkId,statusFavorite;

    public FavoriteModel(String fotoPrduk, String namaProduk,
                         String namaPenjual, String produkId, String statusFavorite) {
        this.fotoPrduk = fotoPrduk;
        this.namaProduk = namaProduk;
        this.namaPenjual = namaPenjual;
        this.produkId = produkId;
        this.statusFavorite = statusFavorite;
    }

    public FavoriteModel() {
    }

    public String getProdukId() {
        return produkId;
    }
}
