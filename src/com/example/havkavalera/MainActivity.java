package com.example.havkavalera;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://www.googleapis.com/books/v1/volumes?q=Harry&maxResults=1&projection=lite";
    }

    public void onSelectCategoryClick(View view) {

    }

    public void onSelectRestaurantClick(View view) {
    }
}
