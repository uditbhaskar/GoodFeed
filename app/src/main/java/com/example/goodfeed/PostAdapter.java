package com.example.goodfeed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        public PostHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
