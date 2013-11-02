package com.example.havkavalera.model;

public class Restaurant {

    public final long mId;
    public final String mName;
    public final String mImageUrl;

    private String description;

    private String mAddress;
    private float mLat;
    private float mLng;

    public Restaurant(long id, String name, String imageUrl) {
        this.mId = id;
        this.mName = name;
        this.mImageUrl = imageUrl;
    }

    public void setLocation(String address, float lat, float lng) {
        this.mAddress = address;
        this.mLat = lat;
        this.mLng = lng;
    }

    public String getAddress() {
        return mAddress;
    }

    public float[] getCoordinates() {
        return new float[] {mLat, mLng};
    }
}
