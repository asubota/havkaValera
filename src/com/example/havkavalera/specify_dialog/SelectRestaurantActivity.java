package com.example.havkavalera.specify_dialog;

import android.app.Activity;
import android.os.Bundle;
import com.example.havkavalera.R;
import com.example.havkavalera.adapters.RestaurantSelectAdapter;
import com.example.havkavalera.mock.MockRestaurantsList;
import com.example.havkavalera.model.Restaurant;
import com.markupartist.android.widget.PullToRefreshListView;

import java.util.List;

public class SelectRestaurantActivity extends Activity {

    public static final int REQUEST_CODE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_layout);

        PullToRefreshListView listView = (PullToRefreshListView) findViewById(R.id.selected_list);
        List<Restaurant> restaurants = MockRestaurantsList.getRestaurants();
        listView.setAdapter(new RestaurantSelectAdapter(restaurants));
    }
}
