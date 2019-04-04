package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.PembelianModel;

import java.util.ArrayList;

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.ViewHolder> {

    Context context;
    ArrayList<PembelianModel> pembelianModels;

    public PembelianAdapter(Context context, ArrayList<PembelianModel> pembelianModels) {
        this.context = context;
        this.pembelianModels = pembelianModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_pembelian,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(pembelianModels.get(i).getFotoToko()).into(viewHolder.ivPenjual);
        Glide.with(context).load(pembelianModels.get(i).getFotoToko()).into(viewHolder.ivProduk);
        viewHolder.namaPenjual.setText(pembelianModels.get(i).getNamaToko());
        viewHolder.namaProduk.setText(pembelianModels.get(i).getNamaProduk());
        viewHolder.jumlahBarang.setText(pembelianModels.get(i).getJumlahProduk()+" Barang");
        viewHolder.harga.setText(pembelianModels.get(i).getHargaProduk());
        viewHolder.subtotal.setText(pembelianModels.get(i).getSubtotal());
    }

    @Override
    public int getItemCount() {
        return pembelianModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaPenjual,namaProduk,jumlahBarang,harga,subtotal,jasaPengiriman,lamaWaktu,hargajasa;
        LinearLayout gantiJasaPengiriman;
        ImageView ivPenjual,ivProduk;
        EditText catatanPenjual;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPenjual = itemView.findViewById(R.id.nama_penjual_pembelian);
            namaProduk = itemView.findViewById(R.id.tv_nama_produk_pembelian);
            jumlahBarang = itemView.findViewById(R.id.tv_jumlah_produk_pembelian);
            harga = itemView.findViewById(R.id.tv_harga_produk_pembelian);
            subtotal = itemView.findViewById(R.id.subtotal);
            jasaPengiriman = itemView.findViewById(R.id.jasa_pengiriman);
            lamaWaktu = itemView.findViewById(R.id.durasi_pengiriman);
            gantiJasaPengiriman = itemView.findViewById(R.id.ll_harga_jasa);
            hargajasa = itemView.findViewById(R.id.harga_jasa);
            ivPenjual = itemView.findViewById(R.id.iv_profile_penjual_pembelian);
            ivProduk = itemView.findViewById(R.id.iv_produk_pembelian);
            catatanPenjual = itemView.findViewById(R.id.et_catatan);
        }
    }
}
