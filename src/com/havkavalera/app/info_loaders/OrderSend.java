package com.havkavalera.app.info_loaders;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.MenuItem;
import com.havkavalera.app.model.Order;
import com.havkavalera.app.model.Restaurant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSend {

    private OrderListener orderListener;
    private RequestQueue mRequestQueue;
    private Context context;

    public OrderSend(Context context) {
        this.context = context;
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void sendRequest(String url, final String userID, final Order order) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        if (orderListener != null) {
                            orderListener.orderSubmitted();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        if (orderListener != null) {
                            orderListener.orderSubmitted();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                Restaurant restaurant = order.getRestaurant();
                params.put("restaurant", restaurant.mId);
                params.put("lat", String.valueOf(order.getCoords()[0]));
                params.put("lng", String.valueOf(order.getCoords()[1]));

                params.put("phone", order.getPhoneNumber());
                params.put("description", "");
                params.put("amount", String.valueOf(order.getTotal()));
                params.put("currency", "UAH");
                params.put("user_id", userID);
                List<MenuItem> menus = order.getOrderedMenu();
                JSONArray jsonArray = new JSONArray();
                try {
                    for (MenuItem menu : menus) {
                        JSONObject jso = new JSONObject();
                        jso.put("menu_id", menu.mKey);
                        jso.put("count", menu.getOrdered());
                        jsonArray.put(jso);
                    }
                } catch (JSONException ignored) {
                }
                params.put("order", jsonArray.toString());
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

        /*{
        restaurant   : "",
                lat          : 0,
            lng          : 0,
            phone        : "",
            description  : "",
            amount       : 0,
            currency     : "UAH",
            order        : [
        {
            menu_id : 0,
                    count   : 0
        }
        ]
    } */

    public void setOrderListener(OrderListener orderListener) {
        this.orderListener = orderListener;
    }

    public interface OrderListener {
        public void orderSubmitted();
    }

}
