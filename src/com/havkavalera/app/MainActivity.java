package com.havkavalera.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.havkavalera.app.specify_dialog.SelectCategoryActivity;
import com.havkavalera.app.specify_dialog.SelectRestaurantActivity;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private int mSearchDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        LocationListener mlocListener = new UserLocationListener();
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

        final TextView distanceView = (TextView) findViewById(R.id.distance);
        distanceView.setText(String.valueOf(1000));

        SeekBar distanceMeter = (SeekBar) findViewById(R.id.distance_meter);
        distanceMeter.setMax(10000);
        distanceMeter.setProgress(1000);
        distanceMeter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceView.setText(String.valueOf(progress));
                mSearchDistance = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void onSelectRestaurantClick(View view) {
        Intent intent = new Intent(this, SelectRestaurantActivity.class);
        intent.putExtra(SelectRestaurantActivity.SEARCH_DISTANCE_KEY, mSearchDistance);
        startActivityForResult(intent, SelectRestaurantActivity.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            handleOkActivityResult(resultCode);
        }
    }

    public void handleOkActivityResult(int requestCode) {
        switch (requestCode) {
            case SelectCategoryActivity.REQUEST_CODE:
                break;
            case SelectRestaurantActivity.REQUEST_CODE:
                break;
        }
    }
}
