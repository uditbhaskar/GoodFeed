package com.example.goodfeed.service.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.goodfeed.service.model.User;
import com.example.goodfeed.service.model.UserPost;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseInstanceRepository {
    FirebaseDatabase instance = FirebaseDatabase.getInstance();
    List<User> userList = new ArrayList<>();

    public MutableLiveData<List<User>> fetchData() {
        final MutableLiveData<List<User>> userData = new MutableLiveData<>();
        userList.clear();

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
                userData.setValue(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return userData;
    }

    public MutableLiveData<Boolean> addPost(String userId, String timestamp, UserPost userPost) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();

        instance.getReference("users")
                .child(userId)
                .child(timestamp)
                .setValue(userPost)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("result", "data successfully added");
                        result.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("result", e.toString());
                        result.setValue(false);
                    }
                });
        return result;
    }

    public MutableLiveData<Boolean> deleteInfo(String userId, String timestamp) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();

        instance.getReference("users")
                .child(userId)
                .child(timestamp)
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                result.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                result.setValue(false);
            }
        });
        return result;
    }

    public MutableLiveData<Boolean> editInfo(String userId, String timestamp, UserPost data) {
        final MutableLiveData<Boolean> result = new MutableLiveData<>();

        instance.getReference("users")
                .child(userId)
                .child(timestamp)
                .setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                result.setValue(true);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                result.setValue(false);
            }
        });
        return result;
    }
}
