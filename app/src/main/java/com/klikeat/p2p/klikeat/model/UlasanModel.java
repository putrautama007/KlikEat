package com.klikeat.p2p.klikeat.model;

public class UlasanModel {
    public String namaProfile,isiUlasan,ratingProduk,idUlasan,tglUlasan,produk_id,userId;

    public UlasanModel(String namaProfile, String isiUlasan, String ratingProduk, String idUlasan,
                       String tglUlasan, String produk_id, String userId) {
        this.namaProfile = namaProfile;
        this.isiUlasan = isiUlasan;
        this.ratingProduk = ratingProduk;
        this.idUlasan = idUlasan;
        this.tglUlasan = tglUlasan;
        this.produk_id = produk_id;
        this.userId = userId;
    }

    public UlasanModel() {
    }
}
