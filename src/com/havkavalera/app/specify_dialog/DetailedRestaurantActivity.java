package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.havkavalera.app.R;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.Restaurant;
import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.utils.GeoPoint;

import java.util.Map;

public class DetailedRestaurantActivity extends Activity {

    public static final String RESTAURANT_KEY = "com.havkavalera.RESTAURANT_KEY";

    private Restaurant mRestaurant;
//    private MapView mMap;

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

//        putMapBaloon();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
