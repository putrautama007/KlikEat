package com.klikeat.p2p.klikeat.model;

public class UlasanModel {
    public String namaProfile,isiUlasan,ratingProduk,idUlasan,tglUlasan;

    public UlasanModel(String namaProfile, String isiUlasan,
                       String ratingProduk, String idUlasan, String tglUlasan) {
        this.namaProfile = namaProfile;
        this.isiUlasan = isiUlasan;
        this.ratingProduk = ratingProduk;
        this.idUlasan = idUlasan;
        this.tglUlasan = tglUlasan;
    }

    public UlasanModel() {
    }
}
