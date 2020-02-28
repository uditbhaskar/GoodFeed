package com.example.goodfeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.goodfeed.firebase.FirebaseInstance;
import com.example.goodfeed.firebase.User;
import com.example.goodfeed.firebase.UserPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FeedActivity extends AppCompatActivity implements FirebaseInstance.MyCallback {
    EditText useriD;
    FloatingActionButton showFeedButton;
    ProgressBar progressBar;
    FirebaseInstance firebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
         firebaseInstance = new FirebaseInstance(this);

        init();
        setListeners();
    }

    private void init() {
        useriD = findViewById(R.id.userId);
        showFeedButton = findViewById(R.id.showFeedButton);
        progressBar = findViewById(R.id.progress_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        useriD.setVisibility(View.VISIBLE);
    }

    private void setListeners() {
        showFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GloabalData.USER_ID = useriD.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                useriD.setVisibility(View.GONE);
                //firebaseInstance.addPost(GloabalData.USER_ID, String.valueOf(System.currentTimeMillis()), new UserPost("hi","dsfdg"));
                firebaseInstance.fetchData();
            }
        });
    }

    @Override
    public void onFetchCallback(List<User> userList) {
        Log.d("result", userList.toString());
        //Data Fetched
        GloabalData.USER_LIST = userList;
        startActivity(new Intent(FeedActivity.this, PostsActivity.class));
    }

    @Override
    public void onAddedCallback(boolean result) {
        //Post added
    }

    @Override
    public void onEditedCallback(boolean result) {
        //Post edited
    }

    @Override
    public void onDeletedCallback(boolean result) {
        //Post Deleted
    }
}
