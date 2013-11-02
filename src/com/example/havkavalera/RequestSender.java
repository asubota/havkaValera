package com.example.havkavalera;

import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class RequestSender {

    private final RequestQueue mRequestQueue;

    public RequestSender(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    public void sendRequest(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
//                txtDisplay.setText("Response => " + response.toString());
//                findViewById(R.id.progressBar1).setVisibility(View.GONE);
//                Toast.makeText(MainActivity.this, "everything ok", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "everything failed " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
//                findViewById(R.id.progressBar1).setVisibility(View.GONE);
            }
        });

        mRequestQueue.add(jsObjRequest);
    }
}
