package com.example.goodfeed.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.goodfeed.service.model.User;
import com.example.goodfeed.service.model.UserPost;
import com.example.goodfeed.service.repository.FirebaseInstanceRepository;

import java.util.List;

public class FirebaseViewModel extends ViewModel {
    private FirebaseInstanceRepository firebaseInstanceRepository;
    public LiveData<List<User>> userListLiveData;
    public LiveData<Boolean> addPostResult;
    public LiveData<Boolean> deleteInfoResult;
    public LiveData<Boolean> editInfoResult;

    public FirebaseViewModel() {
        firebaseInstanceRepository = new FirebaseInstanceRepository();
    }

    public void fetchList() {
        userListLiveData = firebaseInstanceRepository.fetchData();
    }

    public void addPost(String userId, String timestamp, UserPost user) {
        addPostResult = firebaseInstanceRepository.addPost(userId, timestamp, user);
    }

    public void deleteInfo(String userId, String timestamp) {
        deleteInfoResult = firebaseInstanceRepository.deleteInfo(userId, timestamp);
    }

    public void editInfo(String userId, String timestamp, UserPost user) {
        editInfoResult = firebaseInstanceRepository.editInfo(userId, timestamp, user);
    }
}
