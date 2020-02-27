package com.example.goodfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private PostModel[] postModels;
    private Context context;
    private String userId;

    public PostAdapter(PostModel[] postModels, Context context, String userId) {
        this.postModels = postModels;
        this.context = context;
        this.userId = userId;
    }


    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.image_post_view, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        PostModel postModel = postModels[position];

        holder.postText.setText(postModel.getPostTitle());
        if (postModel.getPostImageUrl() != null) {
            holder.postImage.setVisibility(View.VISIBLE);
            String imageUrl = postModel.getPostImageUrl();
            Glide.with(context).load(imageUrl).into(holder.postImage);

        }

        if (postModel.getUserId() == userId) {
            holder.edit.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //edit();
                }
            });
        }
        if (postModel.getUserId() == userId && postModel.getPostImageUrl() != null) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postModels.length;
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        ImageView edit;
        ImageView delete;
        TextView postText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            postText = itemView.findViewById(R.id.post_title);

        }
    }

}
