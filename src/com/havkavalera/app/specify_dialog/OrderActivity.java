package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.havkavalera.app.R;
import com.havkavalera.app.adapters.OrderedMenuAdapter;
import com.havkavalera.app.info_loaders.UserAuth;
import com.havkavalera.app.model.Order;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OrderActivity extends Activity {

    public static final String ORDER_KEY = "com.havka.ORDER_KEY";

    private String user_ID;
    private OrderedMenuAdapter orderedMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_order_layout);

        final UserAuth userAuth = new UserAuth(this);
        Session.openActiveSession(this, true, new Session.StatusCallback() {
            @Override
            public void call(final Session session, SessionState state, Exception exception) {
                if (session != null && session.isOpened()) {
                    // If the session is open, make an API call to get user data
                    // and define a new callback to handle the response
                    Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            // If the response is successful
                            if (user != null) {
                                user_ID = user.getId();
                                Log.d("FB", "User id: " + user_ID);
                                userAuth.sendUserRegisterRequest(user_ID);
                            }
                        }
                    });
                    Request.executeBatchAsync(request);
                }
            }
        });

        Order order = getIntent().getParcelableExtra(ORDER_KEY);

        ListView listView = (ListView) findViewById(R.id.ordered_menu_items);
        orderedMenuAdapter = new OrderedMenuAdapter(order.getOrderedMenu());
        listView.setAdapter(orderedMenuAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    public void sendOrderData(View view) {

    }
}
