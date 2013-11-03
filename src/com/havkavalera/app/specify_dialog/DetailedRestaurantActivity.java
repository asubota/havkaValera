package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.android.volley.toolbox.NetworkImageView;
import com.havkavalera.app.R;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.adapters.MenuSelectAdapter;
import com.havkavalera.app.info_loaders.MenuGetter;
import com.havkavalera.app.model.MenuItem;
import com.havkavalera.app.model.Order;
import com.havkavalera.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DetailedRestaurantActivity extends Activity implements MenuGetter.MenuListener {

    public static final String RESTAURANT_KEY = "com.havkavalera.RESTAURANT_KEY";

    private Restaurant mRestaurant;

    private MenuSelectAdapter menuSelectAdapter;

    private ArrayList<MenuItem> mMenuItems = new ArrayList<MenuItem>();

    private ProgressDialog progressDialog;
    private Location location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_restaurant_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = ProgressDialog.show(this, "Please wait", "Loading restaurant menu", false, false);

        mRestaurant = getIntent().getParcelableExtra(RESTAURANT_KEY);

        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.restaurant_icon);
        TextView restaurantName = (TextView) findViewById(R.id.restaurant_name);
        TextView restaurantDescription = (TextView) findViewById(R.id.restaurant_description);
        TextView restaurantAddress = (TextView) findViewById(R.id.restaurant_address);

        networkImageView.setImageUrl(mRestaurant.mImageUrl, VolleySingleton.getInstance(this).getImageLoader());
        restaurantName.setText(mRestaurant.mName);
        restaurantDescription.setText(mRestaurant.getDescription());
        restaurantAddress.setText(mRestaurant.getAddress());

        MenuGetter menuGetter = new MenuGetter(this);
        menuGetter.setMenuListener(this);
        menuGetter.requestMenuForRestaurant(mRestaurant.mId);

        ListView listView = (ListView) findViewById(R.id.restaurant_menu);
        menuSelectAdapter = new MenuSelectAdapter(mMenuItems);
        listView.setAdapter(menuSelectAdapter);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        Button makeOrder = (Button) findViewById(R.id.make_order_all);
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedRestaurantActivity.this, OrderActivity.class);
                Order order = new Order(mRestaurant, mMenuItems);
                order.setCoords(location.getLatitude(), location.getLongitude());
                intent.putExtra(OrderActivity.ORDER_KEY, order);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuReceive(List<MenuItem> menuItems) {
        if (menuItems != null) {
            mMenuItems.clear();
            mMenuItems.addAll(menuItems);
            menuSelectAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Failed load restaurant menu", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
}
