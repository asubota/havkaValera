package com.example.havkavalera.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.havkavalera.ConnectionInfo;

public class Restaurant implements Parcelable {

    public final String mId;
    public final String mName;
    public final String mImageUrl;

    private String mDescription;

    private String mAddress;
    private double mLat;
    private double mLng;

    private String[] categories;

    public Restaurant(String id, String name, String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mImageUrl = ConnectionInfo.getHttpHostAddress() + imageUrl;
    }

    public Restaurant(Parcel source) {
        this.mId = source.readString();
        this.mName = source.readString();
        this.mImageUrl = source.readString();

        this.mDescription = source.readString();
        this.mAddress = source.readString();
        this.mLat = source.readDouble();
        this.mLng = source.readDouble();

        source.readStringArray(categories);
    }

    public void setLocation(String address, double lat, double lng) {
        this.mAddress = address;
        this.mLat = lat;
        this.mLng = lng;
    }

    public String getAddress() {
        return mAddress;
    }

    public double[] getCoordinates() {
        return new double[] {mLat, mLng};
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mImageUrl);
        dest.writeString(mDescription);
        dest.writeString(mAddress);
        dest.writeDouble(mLat);
        dest.writeDouble(mLng);
        dest.writeStringArray(categories);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        return !(mId != null ? !mId.equals(that.mId) : that.mId != null);
    }

    @Override
    public int hashCode() {
        return mId != null ? mId.hashCode() : 0;
    }
}
