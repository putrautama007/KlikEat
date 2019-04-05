package com.klikeat.p2p.klikeat.model;

public class MetodePembayaranModel {
    String namaBank,fotoBank,nomorRekeningBank,namaRekening;

    public MetodePembayaranModel(String namaBank, String fotoBank,
                                 String nomorRekeningBank, String namaRekening) {
        this.namaBank = namaBank;
        this.fotoBank = fotoBank;
        this.nomorRekeningBank = nomorRekeningBank;
        this.namaRekening = namaRekening;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getFotoBank() {
        return fotoBank;
    }

    public void setFotoBank(String fotoBank) {
        this.fotoBank = fotoBank;
    }

    public String getNomorRekeningBank() {
        return nomorRekeningBank;
    }

    public void setNomorRekeningBank(String nomorRekeningBank) {
        this.nomorRekeningBank = nomorRekeningBank;
    }

    public String getNamaRekening() {
        return namaRekening;
    }

    public void setNamaRekening(String namaRekening) {
        this.namaRekening = namaRekening;
    }
}
