package com.klikeat.p2p.klikeat.model;

import java.util.List;

public class KeranjangBelanjaTokoModel {
    String fotoPenjual,namaToko;
    List<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModel;

    public KeranjangBelanjaTokoModel(String fotoPenjual, String namaToko, List<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModel) {
        this.fotoPenjual = fotoPenjual;
        this.namaToko = namaToko;
        this.keranjangBelanjaProdukModel = keranjangBelanjaProdukModel;
    }

    public KeranjangBelanjaTokoModel() {
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

    public List<KeranjangBelanjaProdukModel> getKeranjangBelanjaProdukModel() {
        return keranjangBelanjaProdukModel;
    }

    public void setKeranjangBelanjaProdukModel(List<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModel) {
        this.keranjangBelanjaProdukModel = keranjangBelanjaProdukModel;
    }
}
