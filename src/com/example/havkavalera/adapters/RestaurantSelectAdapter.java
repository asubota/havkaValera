package com.example.havkavalera.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.example.havkavalera.R;
import com.example.havkavalera.VolleySingleton;
import com.example.havkavalera.model.Restaurant;

import java.util.List;

public class RestaurantSelectAdapter extends BaseAdapter {

    private List<Restaurant> mRestaurants;

    public RestaurantSelectAdapter(List<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    @Override
    public int getCount() {
        return mRestaurants.size();
    }

    @Override
    public Restaurant getItem(int position) {
        return mRestaurants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRestaurants.get(position).mId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.select_restaurant_item, parent, false);

        NetworkImageView restaurantImageView = (NetworkImageView) view.findViewById(R.id.restaurant_icon);
        TextView restaurantName = (TextView) view.findViewById(R.id.restaurant_name);

        Restaurant restaurant = getItem(position);
        restaurantImageView.setImageUrl(restaurant.mImageUrl, VolleySingleton.getInstance(context).getImageLoader());
        restaurantName.setText(restaurant.mName);

        return view;
    }
}
