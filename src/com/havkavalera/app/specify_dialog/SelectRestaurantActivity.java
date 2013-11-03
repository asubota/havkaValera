package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.havkavalera.app.R;
import com.havkavalera.app.info_loaders.RestaurantsGetter;
import com.havkavalera.app.adapters.RestaurantSelectAdapter;
import com.havkavalera.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SelectRestaurantActivity extends Activity implements RestaurantsGetter.RestaurantListener {

    public static final int REQUEST_CODE = 1002;

    public static final String SEARCH_DISTANCE_KEY = "com.havkavalera.app.SEARCH_DISTANCE_KEY";

    private List<Restaurant> mRestaurants = new ArrayList<Restaurant>();
    private RestaurantSelectAdapter rsa;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_layout);

        progressDialog = ProgressDialog.show(this, "Please wait", "Loading restaurants list", false, false);

        ListView listView = (ListView) findViewById(R.id.selected_list);
        rsa = new RestaurantSelectAdapter(mRestaurants);
        listView.setAdapter(rsa);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = mRestaurants.get(position);

                Intent intent = new Intent(SelectRestaurantActivity.this, DetailedRestaurantActivity.class);
                intent.putExtra(DetailedRestaurantActivity.RESTAURANT_KEY, restaurant);
                startActivity(intent);
            }
        });

        int searchDistance = getIntent().getIntExtra(SEARCH_DISTANCE_KEY, 1000);

        final RestaurantsGetter restaurantsGetter = new RestaurantsGetter(this);
        restaurantsGetter.setRestaurantListener(this);
        setRestaurantRequest(restaurantsGetter, searchDistance);


    }

    private void setRestaurantRequest(RestaurantsGetter restaurantsGetter, int radius) {
        Location location = ((LocationManager) getSystemService(LOCATION_SERVICE)).
                getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            restaurantsGetter.requestRestaurantsByPosition(location.getLongitude(),
                    location.getLatitude(), radius);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void restaurantsReceived(List<Restaurant> restaurants) {
        if (restaurants != null) {
            mRestaurants.clear();
            mRestaurants.addAll(restaurants);
            rsa.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Failed to load restaurants", Toast.LENGTH_SHORT).show();
        }
        progressDialog.dismiss();
    }
}
