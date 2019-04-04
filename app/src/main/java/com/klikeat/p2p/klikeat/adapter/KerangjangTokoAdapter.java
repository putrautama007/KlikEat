package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.KeranjangBelanjaTokoModel;

import java.util.List;

public class KerangjangTokoAdapter extends RecyclerView.Adapter<KerangjangTokoAdapter.ViewHolder> {
    Context context;
    List<KeranjangBelanjaTokoModel> keranjangBelanjaTokoModelList;
    KerangjangProdukAdapter kerangjangProdukAdapter;

    public KerangjangTokoAdapter(Context context, List<KeranjangBelanjaTokoModel> keranjangBelanjaTokoModelList) {
        this.context = context;
        this.keranjangBelanjaTokoModelList = keranjangBelanjaTokoModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_keranjang_pertoko,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.namaToko.setText(keranjangBelanjaTokoModelList.get(i).getNamaToko());
        Glide.with(context).load(keranjangBelanjaTokoModelList.get(i).getFotoPenjual()).into(viewHolder.fotoToko);
        viewHolder.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        viewHolder.rvProdukKeranjang.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        kerangjangProdukAdapter = new KerangjangProdukAdapter(context, keranjangBelanjaTokoModelList.get(i).getKeranjangBelanjaProdukModel());
//        viewHolder.rvProdukKeranjang.setAdapter(kerangjangProdukAdapter);
//        kerangjangProdukAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return keranjangBelanjaTokoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox checkBoxToko;
        TextView namaToko;
        ImageView fotoToko;
        ImageButton deleteAll;
        RecyclerView  rvProdukKeranjang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxToko = itemView.findViewById(R.id.checkbox_keranjang_toko);
            namaToko = itemView.findViewById(R.id.nama_penjual);
            fotoToko = itemView.findViewById(R.id.iv_profile_penjual_keranjang);
            deleteAll = itemView.findViewById(R.id.btn_delete_all_produk);
            rvProdukKeranjang = itemView.findViewById(R.id.rv_produk_keranjang);
        }
    }
}
