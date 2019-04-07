package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.Util;
import com.klikeat.p2p.klikeat.model.KeranjangBelanjaProdukModel;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class KerangjangProdukAdapter extends RecyclerView.Adapter<KerangjangProdukAdapter.ViewHolder> {
    int jumlah = 0;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    Util util;
    Context context;
    ArrayList<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModelList;

    public KerangjangProdukAdapter(Context context, ArrayList<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModelList) {
        this.context = context;
        this.keranjangBelanjaProdukModelList = keranjangBelanjaProdukModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_keranjang,viewGroup,false));
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
        viewHolder.namaToko.setText(keranjangBelanjaProdukModelList.get(i).getNamaToko());
        Picasso.get().load(keranjangBelanjaProdukModelList.get(i).getFotoProduk()).fit()
                .centerCrop().transform(roundedCornersTransformation).into(viewHolder.ivProduk);
        Picasso.get().load(keranjangBelanjaProdukModelList.get(i).getFotoPenjual()).fit()
                .centerCrop().transform(roundedCornersTransformation1).into(viewHolder.ivToko);
        viewHolder.namaProduk.setText(keranjangBelanjaProdukModelList.get(i).getNamaProduk());
        viewHolder.hargaProduk.setText(util.convertToIdr(Integer.parseInt(keranjangBelanjaProdukModelList.get(i).getHargaProduk())));
        viewHolder.jumlahProduk.setText(keranjangBelanjaProdukModelList.get(i).getJumlahPembelian());
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlah>0) {
                    jumlah--;
                    mUserDatabase.child(userId).child("pembelian")
                            .child(keranjangBelanjaProdukModelList.get(i).getProdukId())
                            .child("jumlahProduk").setValue(String.valueOf(jumlah));

                    mUserDatabase.child(userId).child("keranjang")
                            .child(keranjangBelanjaProdukModelList.get(i).getProdukId())
                            .child("jumlahPembelian").setValue(String.valueOf(jumlah));

                    viewHolder.jumlahProduk.setText(""+jumlah);
                }
            }
        });
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah++;
                mUserDatabase.child(userId).child("pembelian")
                        .child(keranjangBelanjaProdukModelList.get(i).getProdukId())
                        .child("jumlahProduk").setValue(String.valueOf(jumlah));

                mUserDatabase.child(userId).child("keranjang")
                        .child(keranjangBelanjaProdukModelList.get(i).getProdukId())
                        .child("jumlahPembelian").setValue(String.valueOf(jumlah));

                viewHolder.jumlahProduk.setText(""+jumlah);
            }
        });
        viewHolder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query deleteKeranjangItem = mUserDatabase.child(userId).child("keranjang")
                        .child(keranjangBelanjaProdukModelList.get(i).getProdukId());
                deleteKeranjangItem.getRef().removeValue();
                removeAt(i);
            }
        });
    }

    public void removeAt(int position) {
        keranjangBelanjaProdukModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, keranjangBelanjaProdukModelList.size());
    }

    @Override
    public int getItemCount() {
        return keranjangBelanjaProdukModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatCheckBox checkBoxProduk,checkBoxToko;
        ImageView ivProduk,ivToko;
        TextView namaProduk,hargaProduk,jumlahProduk,namaToko;
        ImageButton btnMinus,btnPlus,btnDeleteItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxProduk = itemView.findViewById(R.id.checkbox_keranjang);
            ivProduk = itemView.findViewById(R.id.iv_produk_keranjang);
            namaProduk = itemView.findViewById(R.id.tv_nama_produk_keranjang);
            hargaProduk = itemView.findViewById(R.id.tv_harga_produk_keranjang);
            jumlahProduk = itemView.findViewById(R.id.tv_jumlah_produk);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            checkBoxToko = itemView.findViewById(R.id.checkbox_keranjang_toko);
            ivToko = itemView.findViewById(R.id.iv_profile_penjual_keranjang);
            namaToko = itemView.findViewById(R.id.nama_penjual);
            btnDeleteItem = itemView.findViewById(R.id.btn_delete_all_produk);
        }
    }
}
