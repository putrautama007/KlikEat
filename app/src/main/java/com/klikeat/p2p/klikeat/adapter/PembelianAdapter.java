package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.Util;
import com.klikeat.p2p.klikeat.model.PembelianModel;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.ViewHolder> {

    DatabaseReference mUserDatabase;
    FirebaseDatabase  mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    Context context;
    ArrayList<PembelianModel> pembelianModels;
    Util util;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(40,0);
        RoundedCornersTransformation roundedCornersTransformation1 = new RoundedCornersTransformation(100,0);
        util = new Util(context);
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        Picasso.get().load(pembelianModels.get(i).getFotoProduk()).fit()
                .centerCrop().transform(roundedCornersTransformation).into(viewHolder.ivProduk);
        Picasso.get().load(pembelianModels.get(i).getFotoToko()).fit()
                .centerCrop().transform(roundedCornersTransformation1).into(viewHolder.ivPenjual);
        viewHolder.catatanPenjual.setText(pembelianModels.get(i).getCatatan());
        viewHolder.hargajasa.setText(util.convertToIdr(Integer.parseInt(pembelianModels.get(i).getHargaPengiriman())));
        viewHolder.namaPenjual.setText(pembelianModels.get(i).getNamaToko());
        viewHolder.namaProduk.setText(pembelianModels.get(i).getNamaProduk());
        viewHolder.jumlahBarang.setText(pembelianModels.get(i).getJumlahProduk()+" Barang");
        viewHolder.harga.setText(util.convertToIdr(Integer.parseInt(pembelianModels.get(i).getHargaProduk())));
        viewHolder.subtotal.setText(util.convertToIdr(Integer.parseInt(pembelianModels.get(i).getHargaProduk())
                *Integer.parseInt(pembelianModels.get(i).getJumlahProduk()) +
                Integer.parseInt(pembelianModels.get(i).getHargaPengiriman())));
        viewHolder.catatanPenjual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserDatabase.child(userId).child("pembelian").child(pembelianModels.get(i).getProdukId())
                        .child("catatan").setValue(viewHolder.catatanPenjual.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
