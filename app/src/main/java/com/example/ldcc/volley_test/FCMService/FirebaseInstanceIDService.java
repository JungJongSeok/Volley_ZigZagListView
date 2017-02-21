package com.example.ldcc.volley_test.FCMService;


import com.example.ldcc.volley_test.etc.Dlog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            AlertDialog mAlertDialog = new AlertDialog.Builder(this)
//                .setCancelable(false)
//                .setTitle("FCM token 값")
//                .setMessage(token)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                    }
//                })//setPositiveButton
//                .create();
//            mAlertDialog.show();
//        }
//    };

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Dlog.d(TAG, "Refreshed token: " + token);

        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        //request
        Request request = new Request.Builder()
                .url("http://10.231.242.54:3000/fcm/register.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


