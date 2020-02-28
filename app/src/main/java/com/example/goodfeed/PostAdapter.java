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
import com.example.goodfeed.firebase.User;
import com.example.goodfeed.firebase.UserPost;

import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<User> userList;
    private Context context;
    private String userId;

    PostAdapter(Context context, String userId) {
        this.context = context;
        this.userId = userId;
    }

    void setItems(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
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
        String Id = userList.get(position).getUserId();
        String timeStamp = userList.get(position).getTimeStamp();

        UserPost data = userList.get(position).getUserPost();
        holder.postText.setText(data.text);

        holder.userId.setText(userId);
        holder.timeStamp.setText(timeStamp);

        if (data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
            holder.postImage.setVisibility(View.VISIBLE);
            String imageUrl = data.getImageUrl();
            Glide.with(context).load(imageUrl).into(holder.postImage);
        }

        if (userId.equals(Id)) {
            holder.userIdView.setVisibility(View.VISIBLE);
            holder.userIdView.setText(userId);

            holder.edit.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //edit();
                }
            });
        }
        if (userId.equals(Id) && data.getImageUrl() != null && !data.getImageUrl().isEmpty()) {
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
        return userList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        ImageView edit;
        ImageView delete;
        TextView postText;
        TextView userIdView;
        TextView userId;
        TextView timeStamp;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_image);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            postText = itemView.findViewById(R.id.post_title);
            userIdView=itemView.findViewById(R.id.userIdView);
            userId = itemView.findViewById(R.id.tv_userId);
            timeStamp = itemView.findViewById(R.id.tv_time);
        }
    }

}
