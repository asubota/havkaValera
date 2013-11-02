package com.example.havkavalera;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.havkavalera.model.Restaurant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsGetter {

    private final RequestQueue mRequestQueue;
    private RestaurantListener mRestaurantListener;

    public RestaurantsGetter(Context context) {
        this.mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void requestAllRestaurants() {
        String url = ConnectionInfo.getHttpHostAddress();
        url += "restaurant/";
        Log.d("Connect", "Connecting url: " + url);

        sendRequestArray(url);
    }

    public void requestRestaurantsByPosition(double lng, double lat, int radius) {
        String url = ConnectionInfo.getHttpHostAddress();
        url += "restaurant/" + lng + "/" + lat + "/" + radius;
        Log.d("Connect", "Connecting url: " + url);
        sendRequestArray(url);
    }

    private void sendRequestArray(String url) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) throws JSONException {
                Log.d("Response", response.toString());
                List<Restaurant> restaurants = new ArrayList<Restaurant>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jso = response.getJSONObject(i);
                    restaurants.add(parseRestaurant(jso));
                }

                if (mRestaurantListener != null) {
                    mRestaurantListener.restaurantsReceived(restaurants);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "Error: " + error);
                if (mRestaurantListener != null) {
                    mRestaurantListener.restaurantsReceived(null);
                }
            }
        }
        );

        mRequestQueue.add(jsObjRequest);
    }

    public void requestRestaurantById(String id) {
        String url = ConnectionInfo.getHttpHostAddress();
        url += "restaurant/" + id;
        sendRequest(url);
    }

    private void sendRequest(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                Log.d("Response", response.toString());
                List<Restaurant> restaurants = new ArrayList<Restaurant>();
                restaurants.add(parseRestaurant(response));

                if (mRestaurantListener != null) {
                    mRestaurantListener.restaurantsReceived(restaurants);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "Error: " + error.networkResponse.toString());
                if (mRestaurantListener != null) {
                    mRestaurantListener.restaurantsReceived(null);
                }
            }
        });

        mRequestQueue.add(jsObjRequest);
    }

    private Restaurant parseRestaurant(JSONObject jso) throws JSONException {
        String name = jso.getString("name");
        String title = jso.getString("title");
        double lat = jso.getDouble("lat");
        double lng = jso.getDouble("lng");
        JSONObject jsoLogo = jso.getJSONObject("logo");
        String logoUrl = jsoLogo.getString("src");
        String alt = jsoLogo.getString("alt");
        JSONObject jsoAddress = jso.getJSONObject("address");
        String street = jsoAddress.getString("street");
        JSONObject jsoInfo = jso.getJSONObject("info");
        String note = jsoInfo.getString("note");
//        String jsaCategory = jsoInfo.getString("category");
//        jsaCategory = jsaCategory.substring(1, jsaCategory.length() - 2);
//        String[] categories = jsaCategory.split(",");
//        for (int i = 0; i < categories.length; i++) {
//            categories[i] = categories[i].trim();
//        }
        String[] categories = new String[0];
        JSONObject jsoDelivery = jso.getJSONObject("delivery");
        int minCost = jsoDelivery.getInt("min_cost");
        int time = jsoDelivery.getInt("time");
        String cost = jsoDelivery.getString("cost");
        String currency = jsoDelivery.getString("currency");

        String id = jso.getString("_id");

        Restaurant restaurant = new Restaurant(id, title, logoUrl);
        restaurant.setDescription(note);
        restaurant.setLocation(street, lat, lng);
        restaurant.setCategories(categories);
        return restaurant;
    }

    public void setRestaurantListener(RestaurantListener restaurantListener) {
        this.mRestaurantListener = restaurantListener;
    }

    public interface RestaurantListener {

        public void restaurantsReceived(List<Restaurant> restaurants);
    }
}
