package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.klikeat.p2p.klikeat.DetailMakananActivity;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.MakananModel;

import java.util.List;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

     Context context;
     List<MakananModel> makananModels;

    public MakananAdapter(Context context, List<MakananModel> makananModels) {
        this.context = context;
        this.makananModels = makananModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_food,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.namaProduk.setText(makananModels.get(i).nama_produk);
        viewHolder.namaPenjual.setText(makananModels.get(i).penjual);
        viewHolder.ratingMakanan.setRating(Float.parseFloat(makananModels.get(i).rating));
        viewHolder.hargaMakanan.setText("Rp" + makananModels.get(i).harga);
        viewHolder.jumlahUlasan.setText("("+makananModels.get(i).jumlahUlasan+")");
        viewHolder.cvProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMakananActivity.class);
                intent.putExtra("produkData",new Gson().toJson(makananModels.get(i)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return makananModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaProduk,namaPenjual,hargaMakanan,jumlahUlasan;
        RatingBar ratingMakanan;
        CardView cvProduk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProduk = itemView.findViewById(R.id.tv_foodName);
            namaPenjual = itemView.findViewById(R.id.tv_penjual);
            hargaMakanan = itemView.findViewById(R.id.tv_harga);
            jumlahUlasan = itemView.findViewById(R.id.tv_jumlah_ulasan);
            ratingMakanan = itemView.findViewById(R.id.rating_makanan);
            cvProduk = itemView.findViewById(R.id.cv_produk);
        }
    }
}
