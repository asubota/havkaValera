package com.havkavalera.app.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.havkavalera.app.ConnectionInfo;

public class MenuItem implements Parcelable {
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

    public MenuItem(Parcel source) {
        mKey = source.readString();
        mName = source.readString();
        mImageUrl = source.readString();
        mDescription = source.readString();
        mPrice = source.readInt();
        category = source.readString();
        mOrdered = source.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeString(mName);
        dest.writeString(mImageUrl);
        dest.writeString(mDescription);
        dest.writeInt(mPrice);
        dest.writeString(category);
        dest.writeInt(mOrdered);
    }

    public static final Parcelable.Creator<MenuItem> CREATOR = new Parcelable.Creator<MenuItem>() {
        public MenuItem createFromParcel(Parcel source) {
            return new MenuItem(source);
        }

        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

}
