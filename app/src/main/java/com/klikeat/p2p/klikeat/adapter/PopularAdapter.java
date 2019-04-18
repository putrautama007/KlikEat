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
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.gson.Gson;
import com.klikeat.p2p.klikeat.DetailMakananActivity;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.Util;
import com.klikeat.p2p.klikeat.model.MakananModel;
import com.klikeat.p2p.klikeat.model.PopularModel;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MakananModel> popularModels;
    Util util;

    public PopularAdapter(Context context, ArrayList<MakananModel> popularModels) {
        this.context = context;
        this.popularModels = popularModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.list_popular, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        util = new Util(context);
        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(10,0);
        myViewHolder.tvFoodName.setText(popularModels.get(i).getNama_produk());
        myViewHolder.tvFoodPrice.setText(util.convertToIdr(Integer.parseInt(popularModels.get(i).getHarga())));
        Picasso.get().load(popularModels.get(i).getFoto()).fit().centerCrop().transform(roundedCornersTransformation).into(myViewHolder.ivPopularPicture);
//        Glide.with(context).load(popularModels.get(i).getFoto()).into(myViewHolder.ivPopularPicture);
        myViewHolder.ratingBar.setRating(Float.parseFloat(popularModels.get(i).rating));
        myViewHolder.tvRatingUlasan.setText("("+popularModels.get(i).jumlahUlasan+")");
        myViewHolder.cvPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMakananActivity.class);
                intent.putExtra("produkData",new Gson().toJson(popularModels.get(i)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName;
        TextView tvFoodPrice,tvRatingUlasan;
        ImageView ivPopularPicture;
        RatingBar ratingBar;
        CardView cvPopular;
        public MyViewHolder(View view){
            super(view);
            tvFoodName =  view.findViewById(R.id.tv_foodPopularName);
            tvFoodPrice =  view.findViewById(R.id.tv_foodPopularPrice);
            ivPopularPicture =  view.findViewById(R.id.iv_foodPopular);
            ratingBar = view.findViewById(R.id.rating_popular);
            tvRatingUlasan = view.findViewById(R.id.jumlah_ulasan_populer);
            cvPopular = view.findViewById(R.id.cv_foodPopular);

        }
    }
}
