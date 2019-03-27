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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.PopularModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.MyViewHolder> {
    private Context context;
    private List<PopularModel> popularModels;

    public PopularAdapter(Context context, List<PopularModel> popularModels) {
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvFoodName.setText(popularModels.get(i).getFoodName());
        myViewHolder.tvFoodPrice.setText(popularModels.get(i).getFoodPrice());
        Glide.with(context)
                .load(popularModels.get(i).getPicture())
                .into(myViewHolder.ivPopularPicture);
    }

    @Override
    public int getItemCount() {
        return popularModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName;
        TextView tvFoodPrice;
        ImageView ivPopularPicture;
        public MyViewHolder(View view){
            super(view);
            tvFoodName = (TextView) view.findViewById(R.id.tv_foodPopularName);
            tvFoodPrice = (TextView) view.findViewById(R.id.tv_foodPopularPrice);
            ivPopularPicture = (ImageView) view.findViewById(R.id.iv_foodPopular);
        }
    }
}
