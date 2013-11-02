package com.example.havkavalera.specify_dialog;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.havkavalera.R;
import com.example.havkavalera.model.Restaurant;
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
    private MapView mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_restaurant_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mMap = (MapView) findViewById(R.id.map);

        mRestaurant = getIntent().getParcelableExtra(RESTAURANT_KEY);

        MapController mapController = mMap.getMapController();
        OverlayManager overlayManager = mapController.getOverlayManager();
        Overlay overlay = new Overlay(mapController);

        Resources res = getResources();
        double[] coords = mRestaurant.getCoordinates();
        final OverlayItem rest = new OverlayItem(new GeoPoint(coords[0], coords[1]), res.getDrawable(R.drawable.map_location));

        BalloonItem balloonYandex = new BalloonItem(this, rest.getGeoPoint());
        balloonYandex.setText(mRestaurant.mName);

        overlay.addOverlayItem(rest);
        overlayManager.addOverlay(overlay);
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
