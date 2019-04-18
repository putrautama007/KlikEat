package com.klikeat.p2p.klikeat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.CommentModel;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    ArrayList<CommentModel> commentModels;

    public CommentAdapter(Context context, ArrayList<CommentModel> commentModels) {
        this.context = context;
        this.commentModels = commentModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_comment,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nameProfile.setText(commentModels.get(i).getUserName());
        viewHolder.comment.setText(commentModels.get(i).getUserComment());
        viewHolder.tglComment.setText(commentModels.get(i).getCommentDate());

    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProfile,comment,tglComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProfile = itemView.findViewById(R.id.tv_profile_comment);
            comment= itemView.findViewById(R.id.tv_comment);
            tglComment = itemView.findViewById(R.id.tv_tgl_comment);
        }
    }
}
