package com.example.havkavalera.model;

public class Category {

    public final long mId;
    public final String mName;
    public final String mImageUrl;

    public Category(long id, String name, String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mImageUrl = imageUrl;
    }
}
