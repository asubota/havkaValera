package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.havkavalera.app.ConnectionInfo;
import com.havkavalera.app.R;
import com.havkavalera.app.adapters.OrderedMenuAdapter;
import com.havkavalera.app.info_loaders.OrderSend;
import com.havkavalera.app.info_loaders.UserAuth;
import com.havkavalera.app.model.Order;
import com.havkavalera.app.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OrderActivity extends Activity implements UserAuth.UserListener {

    public static final String ORDER_KEY = "com.havka.ORDER_KEY";
    private static final String PREF = "com.havka.PREFERENCES";
    private static final String PHONE_NUMBER = "com.havka.PHONE_NUMBER";

    private String user_ID;
    private OrderedMenuAdapter orderedMenuAdapter;
    private Order order;
    private User user;
    private ProgressDialog progressDialog;
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_order_layout);

        progressDialog = ProgressDialog.show(this, "Authentithicating user...", "Please wait...", false, false);

        final UserAuth userAuth = new UserAuth(this);
        userAuth.setUserListener(this);
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

        order = getIntent().getParcelableExtra(ORDER_KEY);

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        SharedPreferences pref = getSharedPreferences(PREF, Context.MODE_WORLD_READABLE);
        String readNumber = pref.getString(PHONE_NUMBER, "");
        phoneNumber.setText(readNumber);
        if (!TextUtils.isEmpty(readNumber)) {
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(phoneNumber.getWindowToken(), 0);
        }

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
        OrderSend orderSend = new OrderSend(this);

        String url = ConnectionInfo.getHttpHostAddress() + "/order";

        if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            Toast.makeText(this, "Please, enter your mobile phone.", Toast.LENGTH_SHORT).show();
        } else if (order.getOrderedMenu().size() <= 0) {
            Toast.makeText(this, "Please, choose some orders.", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences pref = getSharedPreferences(PREF, Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(PHONE_NUMBER, phoneNumber.getText().toString());
            editor.commit();

            if (user != null) {
                orderSend.sendRequest(url, user.mId, order);
            }

            Toast.makeText(this, "Your order sent to server.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void userDataReceived(User user) {
        this.user = user;
        progressDialog.dismiss();
    }
}
