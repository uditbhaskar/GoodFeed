package com.example.goodfeed.firebase;

public class User {
    private String userId;
    private String timeStamp;
    private UserPost userPost;

    public User() {

    }

    public User(String userId, String timeStamp, UserPost userPost) {
        this.userId = userId;
        this.timeStamp = timeStamp;
        this.userPost = userPost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public UserPost getUserPost() {
        return userPost;
    }

    public void setUserPost(UserPost userPost) {
        this.userPost = userPost;
    }
}
