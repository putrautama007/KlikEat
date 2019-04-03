package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.KeranjangBelanjaProdukModel;

import java.util.List;

public class KerangjangProdukAdapter extends RecyclerView.Adapter<KerangjangProdukAdapter.ViewHolder> {
    int jumlah = 0;
    Context context;
    List<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModelList;

    public KerangjangProdukAdapter(Context context, List<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModelList) {
        this.context = context;
        this.keranjangBelanjaProdukModelList = keranjangBelanjaProdukModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_keranjang_perproduk,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Glide.with(context).load(keranjangBelanjaProdukModelList.get(i).getFotoProduk()).into(viewHolder.ivProduk);
        viewHolder.namaProduk.setText(keranjangBelanjaProdukModelList.get(i).getNamaProduk());
        viewHolder.hargaProduk.setText(keranjangBelanjaProdukModelList.get(i).getNamaProduk());
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlah>0) {
                    jumlah--;
                    viewHolder.jumlahProduk.setText(jumlah);
                }
            }
        });
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah++;
                viewHolder.jumlahProduk.setText(jumlah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return keranjangBelanjaProdukModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox checkBoxProduk;
        ImageView ivProduk;
        TextView namaProduk,hargaProduk,jumlahProduk;
        Button btnMinus,btnPlus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxProduk = itemView.findViewById(R.id.checkbox_keranjang);
            ivProduk = itemView.findViewById(R.id.iv_produk_keranjang);
            namaProduk = itemView.findViewById(R.id.tv_nama_produk_keranjang);
            hargaProduk = itemView.findViewById(R.id.tv_harga_produk_keranjang);
            jumlahProduk = itemView.findViewById(R.id.tv_jumlah_produk);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);

        }
    }
}
