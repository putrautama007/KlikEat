package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.HistoryModel;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<HistoryModel> historyModels;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> historyModels) {
        this.context = context;
        this.historyModels = historyModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_history, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(40,0);
        viewHolder.tglTransaksi.setText(historyModels.get(i).getTglTransaksi());
        viewHolder.namaProduk.setText(historyModels.get(i).getNamaProduk());
        viewHolder.namaPenjual.setText(historyModels.get(i).getNamaToko());
        Picasso.get().load(historyModels.get(i).getFotoProduk()).fit()
                .centerCrop().transform(roundedCornersTransformation).into(viewHolder.ivProduk);
    }

    @Override
    public int getItemCount() {
        return historyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduk;
        TextView namaProduk,namaPenjual, tglTransaksi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduk = itemView.findViewById(R.id.iv_produk_history);
            namaPenjual = itemView.findViewById(R.id.nama_penjual_history);
            namaProduk = itemView.findViewById(R.id.nama_produk_history);
            tglTransaksi = itemView.findViewById(R.id.tgl_transaksi_history);
        }
    }
}
