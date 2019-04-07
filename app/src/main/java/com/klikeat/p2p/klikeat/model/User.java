package com.klikeat.p2p.klikeat.model;

public class User {
    String nama,email,password,notlp,tglLahir,alamat,jenisKelamin,poin,foto;

    public User(String nama, String email, String password, String notlp,
                String tglLahir, String alamat, String jenisKelamin, String poin, String foto) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.notlp = notlp;
        this.tglLahir = tglLahir;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
        this.poin = poin;
        this.foto = foto;
    }
}
