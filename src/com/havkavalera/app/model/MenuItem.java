package com.havkavalera.app.model;

import com.havkavalera.app.ConnectionInfo;

public class MenuItem {
    public final String mKey;

    private String mName;
    private String mImageUrl;
    private String mDescription;
    private int mPrice;
    private String category;

    private int mOrdered;

    public MenuItem(String key) {
        this.mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = ConnectionInfo.getHttpHostAddress() + mImageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOrdered(int ordered) {
        mOrdered = ordered;
    }

    public int getOrdered() {
        return mOrdered;
    }
}
