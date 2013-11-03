package com.havkavalera.app.info_loaders;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.havkavalera.app.ConnectionInfo;
import com.havkavalera.app.VolleySingleton;
import com.havkavalera.app.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserAuth {

    private RequestQueue mRequestQueue;
    private UserListener userListener;

    public UserAuth(Context context) {
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void sendUserRegisterRequest(String userID) {
        String url = ConnectionInfo.getHttpHostAddress() + "/user/light";
        Log.d("Request", url);
        sendRequest(url, userID);
    }

    private void sendRequest(String url, final String userID) {
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User user = null;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String id = jsonObject.getString("id");
                            user = new User(id);
                        } catch (JSONException ignored) {
                        }
                        // response
                        Log.d("Response", response);
                        if (userListener != null) {
                            userListener.userDataReceived(user);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        if (userListener != null) {
                            userListener.userDataReceived(null);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", userID);
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    public interface UserListener {
        public void userDataReceived(User user);
    }
}
