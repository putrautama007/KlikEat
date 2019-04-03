package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.UlasanModel;

import java.util.ArrayList;

public class UlasanProdukDetailAdapter extends RecyclerView.Adapter<UlasanProdukDetailAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UlasanModel> ulasanModels;

    public UlasanProdukDetailAdapter(Context context, ArrayList<UlasanModel> ulasanModels) {
        this.context = context;
        this.ulasanModels = ulasanModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_ulasan, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.namaPengulas.setText(ulasanModels.get(i).namaProfile);
        viewHolder.isiUlasan.setText(ulasanModels.get(i).isiUlasan);
        viewHolder.tglUlasan.setText(ulasanModels.get(i).tglUlasan);
        viewHolder.ratingBarUlasanProduk.setRating(Float.parseFloat(ulasanModels.get(i).ratingProduk));

    }

    @Override
    public int getItemCount() {
        return ulasanModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaPengulas, isiUlasan, tglUlasan;
        RatingBar ratingBarUlasanProduk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPengulas = itemView.findViewById(R.id.tv_profile_penilai);
            isiUlasan = itemView.findViewById(R.id.tv_review_produk);
            ratingBarUlasanProduk = itemView.findViewById(R.id.rating_produk_ulasan_item);
            tglUlasan = itemView.findViewById(R.id.tv_tgl_review);

        }
    }
}
