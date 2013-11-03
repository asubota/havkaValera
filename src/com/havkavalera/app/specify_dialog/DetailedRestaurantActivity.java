package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.NetworkImageView;
import com.havkavalera.app.R;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.adapters.MenuSelectAdapter;
import com.havkavalera.app.info_loaders.MenuGetter;
import com.havkavalera.app.model.MenuItem;
import com.havkavalera.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class DetailedRestaurantActivity extends Activity implements MenuGetter.MenuListener {

    public static final String RESTAURANT_KEY = "com.havkavalera.RESTAURANT_KEY";

    private Restaurant mRestaurant;
//    private MapView mMap;

    private MenuSelectAdapter menuSelectAdapter;

    private List<MenuItem> mMenuItems = new ArrayList<MenuItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_restaurant_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);

//        mMap = (MapView) findViewById(R.id.map);

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

//        putMapBaloon();

        ListView listView = (ListView) findViewById(R.id.restaurant_menu);
        menuSelectAdapter = new MenuSelectAdapter(mMenuItems);
        listView.setAdapter(menuSelectAdapter);

        Button makeOrder = (Button) findViewById(R.id.make_order_all);
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedRestaurantActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

    }

    private void putMapBaloon() {
 //        MapController mapController = mMap.getMapController();
//        OverlayManager overlayManager = mapController.getOverlayManager();
//        Overlay overlay = new Overlay(mapController);

//        Resources res = getResources();
//        double[] coords = mRestaurant.getCoordinates();
//        final OverlayItem rest = new OverlayItem(new GeoPoint(coords[0], coords[1]), res.getDrawable(R.drawable.map_location));

//        BalloonItem balloonYandex = new BalloonItem(this, rest.getGeoPoint());
//        balloonYandex.setText(mRestaurant.mName);

//        overlay.addOverlayItem(rest);
//        overlayManager.addOverlay(overlay);
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
    }
}
