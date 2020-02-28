package com.example.goodfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.goodfeed.firebase.User;

import java.util.List;

public class PostsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        init();
        setUpRecyclerView(GloabalData.USER_ID, GloabalData.USER_LIST);
    }
    void init() {
        recyclerView = findViewById(R.id.recyclerView);
    }
    private void setUpRecyclerView(String userId, List<User> userList) {
        PostAdapter postAdapter = new PostAdapter(this, userId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);
        postAdapter.setItems(userList);
    }
}
