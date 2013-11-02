package com.example.havkavalera.specify_dialog;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.havkavalera.R;
import com.example.havkavalera.RestaurantsGetter;
import com.example.havkavalera.UserLocationListener;
import com.example.havkavalera.adapters.RestaurantSelectAdapter;
import com.example.havkavalera.mock.MockRestaurantsList;
import com.example.havkavalera.model.Restaurant;

import java.util.List;

public class SelectRestaurantActivity extends Activity implements RestaurantsGetter.RestaurantListener {

    public static final int REQUEST_CODE = 1002;

    private List<Restaurant> mRestaurants;
    private RestaurantSelectAdapter rsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_layout);

        ListView listView = (ListView) findViewById(R.id.selected_list);
        mRestaurants = MockRestaurantsList.getRestaurants();
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

        RestaurantsGetter restaurantsGetter = new RestaurantsGetter(this);
        restaurantsGetter.setRestaurantListener(this);
        Location location = ((LocationManager) getSystemService(LOCATION_SERVICE)).
                getLastKnownLocation(LocationManager.GPS_PROVIDER);
        restaurantsGetter.requestRestaurantsByPosition(location.getLongitude(),
                location.getLatitude(),1000);
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
    }
}
