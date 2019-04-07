package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.TransferActivity;
import com.klikeat.p2p.klikeat.Util;
import com.klikeat.p2p.klikeat.model.MetodePembayaranModel;

import java.util.ArrayList;

public class MetodePembayaranAdapter extends RecyclerView.Adapter<MetodePembayaranAdapter.ViewHolder> {

    Context context;
    ArrayList<MetodePembayaranModel> metodePembayaranModels;
    Util util;
    Gson gson = new Gson();

    public MetodePembayaranAdapter(Context context, ArrayList<MetodePembayaranModel> metodePembayaranModels) {
        this.context = context;
        this.metodePembayaranModels = metodePembayaranModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_metode_transfer,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        util = new Util(context);
        Glide.with(context).load(metodePembayaranModels.get(i).getFotoBank()).into(viewHolder.ivBank);
        viewHolder.namaBank.setText(metodePembayaranModels.get(i).getNamaBank());
        viewHolder.clBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.saveMetodeTransfer(gson.toJson(metodePembayaranModels.get(i)));
                Intent intent = new Intent(context, TransferActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return metodePembayaranModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBank;
        TextView namaBank;
        ConstraintLayout clBank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBank = itemView.findViewById(R.id.iv_bank);
            namaBank = itemView.findViewById(R.id.tv_nama_bank);
            clBank = itemView.findViewById(R.id.cl_metode_pembayaran);
        }
    }
}
