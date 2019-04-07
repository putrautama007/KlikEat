package com.klikeat.p2p.klikeat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    SharedPreferences preferences ;
    Editor editor;
    int PRIVATE_MODE = 0;
    Context context;
    String PREF_NAME ="KlikEat";
    String PEMBELIAN_DATA ="pembelian";
    String PRODUK_ID ="produkId";
    String METODE_TRANSFER ="metode";

    public Util(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void savePembelian(String pembelian){
        editor.putString(PEMBELIAN_DATA,pembelian);
        editor.commit();
    }
    public void saveMetodeTransfer(String metodeTranfer){
        editor.putString(METODE_TRANSFER,metodeTranfer);
        editor.commit();
    }

    public void clearPembelian(){
        editor.clear();
        editor.commit();
    }

    public String convertToIdr(int harga){
        Locale localeID = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(harga);
    }
}
