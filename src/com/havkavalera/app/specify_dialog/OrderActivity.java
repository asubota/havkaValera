package com.havkavalera.app.specify_dialog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.havkavalera.app.info_loaders.UserAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OrderActivity extends Activity {

    private String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppKeyCode();
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
    }

    private void getAppKeyCode() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(),  PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        } catch (NoSuchAlgorithmException ignored) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }

}
