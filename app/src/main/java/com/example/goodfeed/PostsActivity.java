package com.example.goodfeed;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.goodfeed.firebase.FirebaseInstance;
import com.example.goodfeed.firebase.User;
import com.example.goodfeed.firebase.UserPost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class PostsActivity extends AppCompatActivity implements FirebaseInstance.MyCallback {

    RecyclerView recyclerView;
    FloatingActionButton addPostbutton;
    ProgressBar progressBar;
    FirebaseInstance firebaseInstance;
    PostAdapter postAdapter;
    ImageView empty_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        init();
        setUpRecyclerView();
        listeners();
    }

    void init() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyclerView);
        addPostbutton = findViewById(R.id.addPostButton);
        empty_view = findViewById(R.id.empty_view);

        firebaseInstance = new FirebaseInstance(this);
        fetchPost();
    }

    void listeners() {
        addPostbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });
    }

    private void fetchPost() {
        progressBar.setVisibility(View.VISIBLE);
        firebaseInstance.fetchData();
    }

    void addPost(UserPost userPost) {
        firebaseInstance.addPost(GloabalData.USER_ID, String.valueOf(System.currentTimeMillis()), userPost);
    }
    void updatePost(User data) {
        firebaseInstance.editInfo(GloabalData.USER_ID, data.getTimeStamp(), data.getUserPost());
    }

    void deletePost(String timeStamp) {
        firebaseInstance.deleteInfo(GloabalData.USER_ID, timeStamp);
    }

    private void openBottomSheet() {
        UpdateBottomSheetFragment updateBottomSheetFragment = new UpdateBottomSheetFragment(PostsActivity.this);
        updateBottomSheetFragment.show(getSupportFragmentManager(), "edit");
    }


    private void setUpRecyclerView() {
        postAdapter = new PostAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);
    }

    @Override
    public void onFetchCallback(List<User> userList) {
        Log.d("result", userList.toString());
        //Data Fetched
        GloabalData.USER_LIST = userList;
        if(userList.size()>0) {
            empty_view.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else {
            empty_view.setVisibility(View.VISIBLE);
        }

        progressBar.setVisibility(View.GONE);
        Log.d("check", "called");
        postAdapter.setItems(GloabalData.USER_LIST);
    }

    @Override
    public void onAddedCallback(boolean result) {
        if(result) {
            Toast.makeText(this,"Post has been added...", Toast.LENGTH_SHORT).show();
            fetchPost();
        } else {
            Toast.makeText(this,"Unable to add Post...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditedCallback(boolean result) {
        if(result) {
            Toast.makeText(this,"Post has been updated...", Toast.LENGTH_SHORT).show();
            fetchPost();
        } else {
            Toast.makeText(this,"Unable to update Post...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeletedCallback(boolean result) {
        if(result) {
            Toast.makeText(this,"Post has been successfully deleted...", Toast.LENGTH_SHORT).show();
            fetchPost();
        } else {
            Toast.makeText(this,"Sorry, unable to delete the post...", Toast.LENGTH_SHORT).show();
        }

    }
}
