package com.example.goodfeed.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseInstance {
    private FirebaseDatabase instance;
    private List<User> userList;
    private MyCallback listener;

    public FirebaseInstance(MyCallback listener) {
        instance = FirebaseDatabase.getInstance();
        this.listener = listener;
    }

    public void fetchData() {
        userList = new ArrayList<>();

        instance.getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();

                    for (DataSnapshot postSnapshot : userSnapshot.getChildren()) {
                        String timeStamp = postSnapshot.getKey();
                        UserPost userPost = postSnapshot.getValue(UserPost.class);
                        userList.add(new User(userId, timeStamp, userPost));
                    }
                }
                listener.onFetchCallback(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void addPost(String userId, String timestamp, UserPost userPost) {
        instance.getReference("users")
                .child(userId)
                .child(timestamp)
                .setValue(userPost)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("result", "data successfully added");
                        listener.onAddedCallback(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("result", e.toString());
                        listener.onAddedCallback(false);
                    }
                });
    }

    public void deleteInfo(String userId, String timestamp) {
        instance.getReference("users")
                .child(userId)
                .child(timestamp)
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onDeletedCallback(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onDeletedCallback(false);
            }
        });
    }

    public void editInfo(String userId, String timestamp, UserPost userPost) {
        instance.getReference("users")
                .child(userId)
                .child(timestamp)
                .setValue(userPost).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onEditedCallback(true);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onDeletedCallback(false);
            }
        });
    }

    public interface MyCallback {
        void onFetchCallback(List<User> userList);

        void onAddedCallback(boolean result);

        void onEditedCallback(boolean result);

        void onDeletedCallback(boolean result);
    }
}
