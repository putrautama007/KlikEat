package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.Util;
import com.klikeat.p2p.klikeat.model.PembelianModel;
import com.klikeat.p2p.klikeat.model.PengirimanModel;
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
    ArrayList<PengirimanModel> pengirimanModels;
    Util util;

//    public PembelianAdapter(Context context, ArrayList<PembelianModel> pembelianModels) {
//        this.context = context;
//        this.pembelianModels = pembelianModels;
//    }

    public PembelianAdapter(Context context, ArrayList<PembelianModel> pembelianModels, ArrayList<PengirimanModel> pengirimanModels) {
        this.context = context;
        this.pembelianModels = pembelianModels;
        this.pengirimanModels = pengirimanModels;
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
        viewHolder.jumlahBarang.setText(pembelianModels.get(i).getJumlahPembelian()+" Barang");
        viewHolder.harga.setText(util.convertToIdr(Integer.parseInt(pembelianModels.get(i).getHargaProduk())));
        viewHolder.subtotal.setText(util.convertToIdr(Integer.parseInt(pembelianModels.get(i).getHargaProduk())
                *Integer.parseInt(pembelianModels.get(i).getJumlahPembelian()) +
                Integer.parseInt(pembelianModels.get(i).getHargaPengiriman())));
        viewHolder.catatanPenjual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                        .child("catatan").setValue(viewHolder.catatanPenjual.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        viewHolder.checkJasaPengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConstraintLayout clJasa1,clJasa2;
                TextView jasaPengiriman1,jasaPengiriman2,hargaJasa1,hargaJasa2,lamaJasa1,lamaJasa2;
                View view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_kategori,null);
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(view);
                jasaPengiriman1 = view.findViewById(R.id.jasa_pengiriman_1);
                jasaPengiriman2 = view.findViewById(R.id.jasa_pengiriman_2);
                hargaJasa1 = view.findViewById(R.id.harga_jasa_1);
                hargaJasa2 = view.findViewById(R.id.harga_jasa_2);
                lamaJasa1 = view.findViewById(R.id.durasi_pengiriman_1);
                lamaJasa2 = view.findViewById(R.id.durasi_pengiriman_2);
                clJasa1 = view.findViewById(R.id.constraintLayout6);
                clJasa2 = view.findViewById(R.id.constraintLayout7);

                jasaPengiriman1.setText(pengirimanModels.get(0).getNamaJasa());
                jasaPengiriman2.setText(pengirimanModels.get(1).getNamaJasa());
                hargaJasa1.setText(util.convertToIdr(Integer.parseInt(pengirimanModels.get(0).getHargaPengiriman())));
                hargaJasa2.setText(util.convertToIdr(Integer.parseInt(pengirimanModels.get(1).getHargaPengiriman())));
                lamaJasa1.setText(pengirimanModels.get(0).getWaktuPengiriman());
                lamaJasa2.setText(pengirimanModels.get(1).getWaktuPengiriman());
                clJasa1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int totalItem;
                        viewHolder.hargajasa.setText(util.convertToIdr(Integer.parseInt(pengirimanModels.get(0).getHargaPengiriman())));
                        viewHolder.jasaPengiriman.setText(pengirimanModels.get(0).getNamaJasa());
                        viewHolder.lamaWaktu.setText(pengirimanModels.get(0).getWaktuPengiriman());
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("jasaPengiriaman").setValue(viewHolder.jasaPengiriman.getText().toString());
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("lamaPengiriman").setValue(viewHolder.lamaWaktu.getText().toString());
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("hargaPengiriman").setValue(pengirimanModels.get(0).getHargaPengiriman());
                        totalItem = (Integer.parseInt(pembelianModels.get(i).getHargaProduk())
                                *Integer.parseInt(pembelianModels.get(i).getJumlahPembelian()))
                                + Integer.parseInt(pengirimanModels.get(0).getHargaPengiriman());
                        viewHolder.subtotal.setText(util.convertToIdr(totalItem));
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("subtotal").setValue(String.valueOf(totalItem));
                        bottomSheetDialog.dismiss();
                    }
                });
                clJasa2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int totalItem;
                        viewHolder.hargajasa.setText(util.convertToIdr(Integer.parseInt(pengirimanModels.get(1).getHargaPengiriman())));
                        viewHolder.jasaPengiriman.setText(pengirimanModels.get(1).getNamaJasa());
                        viewHolder.lamaWaktu.setText(pengirimanModels.get(1).getWaktuPengiriman());
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("jasaPengiriaman").setValue(viewHolder.jasaPengiriman.getText().toString());
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("lamaPengiriman").setValue(viewHolder.lamaWaktu.getText().toString());
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("hargaPengiriman").setValue(pengirimanModels.get(1).getHargaPengiriman());
                        totalItem = (Integer.parseInt(pembelianModels.get(i).getHargaProduk())
                                *Integer.parseInt(pembelianModels.get(i).getJumlahPembelian()))
                                + Integer.parseInt(pengirimanModels.get(1).getHargaPengiriman());
                        viewHolder.subtotal.setText(util.convertToIdr(totalItem));
                        mUserDatabase.child(userId).child("BarangDiBeli").child(pembelianModels.get(i).getProdukId())
                                .child("subtotal").setValue(String.valueOf(totalItem));
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.show();
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
        ImageButton checkJasaPengiriman;

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
            checkJasaPengiriman = itemView.findViewById(R.id.check_jasa_pengiriman);
        }
    }
}
