package com.example.VolleyExample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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

    private TextView txtDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txtDisplay = (TextView) findViewById(R.id.txt_display);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.googleapis.com/books/v1/volumes?q=Harry&maxResults=1&projection=lite";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                txtDisplay.setText("Response => " + response.toString());
                findViewById(R.id.progressBar1).setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "everything ok", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "everything failed " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                findViewById(R.id.progressBar1).setVisibility(View.GONE);
            }
        });

        queue.add(jsObjRequest);

    }
}
