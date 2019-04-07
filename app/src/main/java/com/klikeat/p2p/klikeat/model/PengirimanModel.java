package com.klikeat.p2p.klikeat.model;

public class PengirimanModel {
    String namaJasa,hargaPengiriman,waktuPengiriman;

    public PengirimanModel(String namaJasa, String hargaPengiriman, String waktuPengiriman) {
        this.namaJasa = namaJasa;
        this.hargaPengiriman = hargaPengiriman;
        this.waktuPengiriman = waktuPengiriman;
    }

    public PengirimanModel() {
    }

    public String getNamaJasa() {
        return namaJasa;
    }

    public void setNamaJasa(String namaJasa) {
        this.namaJasa = namaJasa;
    }

    public String getHargaPengiriman() {
        return hargaPengiriman;
    }

    public void setHargaPengiriman(String hargaPengiriman) {
        this.hargaPengiriman = hargaPengiriman;
    }

    public String getWaktuPengiriman() {
        return waktuPengiriman;
    }

    public void setWaktuPengiriman(String waktuPengiriman) {
        this.waktuPengiriman = waktuPengiriman;
    }
}
