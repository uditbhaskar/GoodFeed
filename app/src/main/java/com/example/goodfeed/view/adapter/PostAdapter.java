package com.example.goodfeed.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.goodfeed.GloabalData;
import com.example.goodfeed.view.ui.PostsActivity;
import com.example.goodfeed.R;
import com.example.goodfeed.view.ui.UpdateBottomSheetFragment;
import com.example.goodfeed.service.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<User> userList = new ArrayList<>();
    private Context context;

    public PostAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<User> userList) {
        this.userList = userList;
        Log.d("check", "setItems called");
        Collections.sort(userList, new Comparator<User>() {
            public int compare(User o1, User o2) {
                if (o1.getTimeStamp() == null || o2.getTimeStamp() == null)
                    return 0;
                return Long.valueOf(o1.getTimeStamp()).compareTo(Long.valueOf(o2.getTimeStamp()));
            }
        });
        Collections.reverse(userList);
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
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {
        String userId = userList.get(position).getUserId();
        final String timeStamp = userList.get(position).getTimeStamp();
        final String postTitle = userList.get(position).getUserPost().getText();
        final String postImage = userList.get(position).getUserPost().getImageUrl();

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBottomSheetFragment updateBottomSheetFragment = new UpdateBottomSheetFragment(context, userList.get(position));
                updateBottomSheetFragment.show(((PostsActivity) context).getSupportFragmentManager(), "edit");

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PostsActivity) context).deletePost(timeStamp);
            }
        });

        if (userId != null && !userId.isEmpty() && timeStamp != null && !timeStamp.isEmpty()) {
            if (userId.equals(GloabalData.USER_ID)) {
                holder.non_user_view_layout.setVisibility(View.VISIBLE);
                holder.tv_userId.setText(userId);
                holder.tv_postTitle.setText(postTitle);

                if (postImage != null && !postImage.isEmpty()) {
                    holder.img_postImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(postImage).placeholder(R.drawable.background).into(holder.img_postImage);
                    holder.btn_delete.setVisibility(View.GONE);
                } else {
                    holder.img_postImage.setVisibility(View.GONE);
                }

            } else {
                holder.non_user_view_layout.setVisibility(View.GONE);
                holder.tv_userId.setText(userId);
                holder.tv_postTitle.setText(postTitle);

                if (postImage != null && !postImage.isEmpty()) {
                    holder.img_postImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(postImage).placeholder(R.drawable.background).into(holder.img_postImage);
                } else {
                    holder.img_postImage.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        ImageView img_postImage;
        ImageView btn_edit;
        ImageView btn_delete;
        TextView tv_postTitle;
        TextView tv_userId;
        TextView tv_timeStamp;
        LinearLayout non_user_view_layout;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            img_postImage = itemView.findViewById(R.id.img_post_image);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            tv_postTitle = itemView.findViewById(R.id.tv_post_title);
            tv_userId = itemView.findViewById(R.id.tv_userId);
            tv_timeStamp = itemView.findViewById(R.id.tv_time);
            non_user_view_layout = itemView.findViewById(R.id.non_user_View);
        }
    }

}
