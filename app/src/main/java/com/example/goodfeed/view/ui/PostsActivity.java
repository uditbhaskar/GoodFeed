package com.example.goodfeed.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodfeed.GloabalData;
import com.example.goodfeed.R;
import com.example.goodfeed.service.model.User;
import com.example.goodfeed.service.model.UserPost;
import com.example.goodfeed.service.repository.FirebaseInstanceRepository;
import com.example.goodfeed.view.adapter.PostAdapter;
import com.example.goodfeed.viewmodel.FirebaseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PostsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addPostButton;
    ProgressBar progressBar;
    FirebaseInstanceRepository firebaseInstanceRepository;
    PostAdapter postAdapter;
    ImageView empty_view;
    LinearLayoutManager linearLayoutManager;
    FirebaseViewModel firebaseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        init();
        setUpRecyclerView();
        setupViewModel();
        listeners();
    }

    public void init() {
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyclerView);
        addPostButton = findViewById(R.id.addPostButton);
        empty_view = findViewById(R.id.empty_view);
    }

    public void setupViewModel() {
        firebaseViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication()))
                .get(FirebaseViewModel.class);
        //First Time fetching posts...
        fetchPost();
    }

    public void listeners() {
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });
    }

    public void fetchPost() {
        progressBar.setVisibility(View.VISIBLE);
        firebaseViewModel.fetchList();
        firebaseViewModel.userListLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> data) {
                Log.d("result", data.toString());
                //Data Fetched
                GloabalData.USER_LIST = data;
                if (data.size() > 0) {
                    empty_view.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    empty_view.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);
                Log.d("check", "called");
                postAdapter.setItems(GloabalData.USER_LIST);
            }
        });
    }

    public void addPost(UserPost userPost) {
        firebaseViewModel.addPost(GloabalData.USER_ID, String.valueOf(System.currentTimeMillis()), userPost);
        firebaseViewModel.addPostResult.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(PostsActivity.this, "Post has been added...", Toast.LENGTH_SHORT).show();
                    fetchPost();
                } else {
                    Toast.makeText(PostsActivity.this, "Unable to add Post...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updatePost(User data) {
        firebaseViewModel.editInfo(GloabalData.USER_ID, String.valueOf(System.currentTimeMillis()), data.getUserPost());
        firebaseViewModel.editInfoResult.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(PostsActivity.this, "Post has been updated...", Toast.LENGTH_SHORT).show();
                    fetchPost();
                } else {
                    Toast.makeText(PostsActivity.this, "Unable to update Post...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deletePost(String timeStamp) {
        firebaseViewModel.deleteInfo(GloabalData.USER_ID, timeStamp);
        firebaseViewModel.deleteInfoResult.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(PostsActivity.this, "Post has been successfully deleted...", Toast.LENGTH_SHORT).show();
                    fetchPost();
                } else {
                    Toast.makeText(PostsActivity.this, "Sorry, unable to delete the post...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openBottomSheet() {
        UpdateBottomSheetFragment updateBottomSheetFragment = new UpdateBottomSheetFragment(PostsActivity.this);
        updateBottomSheetFragment.show(getSupportFragmentManager(), "edit");
    }


    public void setUpRecyclerView() {
        postAdapter = new PostAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);

    }

}
