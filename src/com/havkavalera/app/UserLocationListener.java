package com.havkavalera.app;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class UserLocationListener implements LocationListener {

    private double mLng = 0.0;
    private double mLat = 0.0;

    @Override
    public void onLocationChanged(Location location) {
        this.mLng = location.getLongitude();
        this.mLat = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
