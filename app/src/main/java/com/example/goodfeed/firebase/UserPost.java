package com.example.goodfeed.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserPost {

    public String text;
    public String imageUrl;

    public UserPost() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserPost(String text, String imageUrl) {
        if(text==null){
            text = "";
        }
        if(imageUrl==null){
            imageUrl = "";
        }

        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
