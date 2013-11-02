package com.havkavalera.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

    public final long mId;
    public final String mName;
    public final String mImageUrl;

    public Category(long id, String name, String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mImageUrl = imageUrl;
    }

    public Category(Parcel source) {
        this.mId = source.readLong();
        this.mName = source.readString();
        this.mImageUrl = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mImageUrl);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }

    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return !(mName != null ? !mName.equals(category.mName) : category.mName != null);
    }

    @Override
    public int hashCode() {
        return mName != null ? mName.hashCode() : 0;
    }
}
