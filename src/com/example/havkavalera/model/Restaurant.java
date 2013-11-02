package com.example.havkavalera.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {

    public final long mId;
    public final String mName;
    public final String mImageUrl;

    private String mDescription;

    private String mAddress;
    private double mLat;
    private double mLng;

    public Restaurant(long id, String name, String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mImageUrl = imageUrl;
    }

    public Restaurant(Parcel source) {
        this.mId = source.readLong();
        this.mName = source.readString();
        this.mImageUrl = source.readString();

        this.mDescription = source.readString();
        this.mAddress = source.readString();
        this.mLat = source.readDouble();
        this.mLng = source.readDouble();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mImageUrl);
        dest.writeString(mDescription);
        dest.writeString(mAddress);
        dest.writeDouble(mLat);
        dest.writeDouble(mLng);
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
