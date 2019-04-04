package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klikeat.p2p.klikeat.DetailMakananActivity;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.FavoriteModel;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    Context context;
    ArrayList<FavoriteModel> favoriteModels;

    public FavoriteAdapter(Context context, ArrayList<FavoriteModel> favoriteModels) {
        this.context = context;
        this.favoriteModels = favoriteModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_favourite,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).load(favoriteModels.get(i).fotoPrduk).into(viewHolder.ivProduk);
        viewHolder.tvNamaProduk.setText(favoriteModels.get(i).namaProduk);
        viewHolder.tvNamaToko.setText(favoriteModels.get(i).namaPenjual);
        viewHolder.cvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMakananActivity.class);
                intent.putExtra("produkId", favoriteModels.get(i).produkId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduk;
        TextView tvNamaProduk,tvNamaToko;
        CardView cvFavorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduk = itemView.findViewById(R.id.iv_produk_favorite);
            tvNamaProduk = itemView.findViewById(R.id.tv_produk_favorite);
            tvNamaToko = itemView.findViewById(R.id.tv_store_name_favorite);
            cvFavorite = itemView.findViewById(R.id.cv_favorite);
        }
    }
}
