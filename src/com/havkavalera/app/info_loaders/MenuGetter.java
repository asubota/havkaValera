package com.havkavalera.app.info_loaders;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.havkavalera.app.ConnectionInfo;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.MenuItem;
import com.havkavalera.app.model.Restaurant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuGetter {

    private RequestQueue mRequestQueue;
    private MenuListener mMenuListener;

    public MenuGetter(Context context) {
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void requestMenuForRestaurant(String restaurantKey) {
        String url = ConnectionInfo.getHttpHostAddress()  + "/restaurant/" + restaurantKey + "/menu/";
        Log.d("Connection", "Connection url: " + url);
        sendRequestArray(url);

    }


    private void sendRequestArray(String url) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) throws JSONException {
                Log.d("Response", response.toString());
                List<MenuItem> restaurants = new ArrayList<MenuItem>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jso = response.getJSONObject(i);
                    restaurants.add(parseMenu(jso));
                }

                if (mMenuListener != null) {
                    mMenuListener.onMenuReceive(restaurants);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response", "Error: " + error);
                if (mMenuListener != null) {
                    mMenuListener.onMenuReceive(null);
                }
            }
        });

        mRequestQueue.add(jsObjRequest);
    }

    private MenuItem parseMenu(JSONObject jso) throws JSONException {
        String id = jso.getString("key");
        String name = jso.getString("name");
        String image = jso.getString("image");
        String description = jso.getString("description");
        float price = (float) jso.getDouble("price");
        String category = jso.getString("category");
        String currency = jso.getString("currency");

        MenuItem menuItem = new MenuItem(id);
        menuItem.setName(name);
        menuItem.setImageUrl(image);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setCategory(category);
        menuItem.setCurrency(currency);

        return menuItem;
    }

    public void setMenuListener(MenuListener mMenuListener) {
        this.mMenuListener = mMenuListener;
    }

    public interface MenuListener {

        public void onMenuReceive(List<MenuItem> menuItems);
    }
}
