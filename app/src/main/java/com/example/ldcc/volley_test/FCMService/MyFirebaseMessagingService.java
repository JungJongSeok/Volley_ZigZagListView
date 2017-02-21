package com.example.ldcc.volley_test.FCMService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.example.ldcc.volley_test.MainActivity;
import com.example.ldcc.volley_test.R;
import com.example.ldcc.volley_test.etc.Consts;
import com.example.ldcc.volley_test.etc.Dlog;
import com.example.ldcc.volley_test.etc.Sharedpreferences;
import com.example.ldcc.volley_test.model.ObjectCPU;
import com.example.ldcc.volley_test.model.ObjectFCM;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    private static final String DATE_TYPE = "yyyy/MM/dd HH:mm:ss";

    private ObjectFCM mObjectFCM;

    //
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Dlog.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Dlog.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Dlog.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        Gson gson = new Gson();
        mObjectFCM = gson.fromJson(remoteMessage.getData().get("message"), ObjectFCM.class);
        //추가한것
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TYPE);
        String formatDate = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        mObjectFCM.getContent().setDate(formatDate);
        sendNotification(mObjectFCM.getTitle());
        insertArrayList(mObjectFCM.getContent());

    }
    private void insertArrayList(ObjectCPU objectCPU){
        ArrayList<ObjectCPU> mArrayList = new ArrayList<>();
        if(Sharedpreferences.getArrayListCPUPreferences(this, Consts.CPU_ARRAYLIST) != null) {
            mArrayList.addAll(Sharedpreferences.getArrayListCPUPreferences(this, "CPUArray"));
        }
        mArrayList.add(0, objectCPU);
        Sharedpreferences.saveArrayListCPUPreferences(this, Consts.CPU_ARRAYLIST, mArrayList);
        Dlog.d(TAG,mArrayList.get(mArrayList.size()-1).getDate().toString() + " count : "+mArrayList.size());
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        long[] pattern = {500, 500, 500, 500, 500};

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.sos_icon)
                .setContentTitle("SmartIndoor CPU warning")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 1, 1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire();

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

