package com.example.barnes.ummo.gcm;

/**
 * Created by sihle on 12/3/15.
 */
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.barnes.ummo.R;
import com.example.barnes.ummo.SingleFragmentActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by sihle on 11/28/15.
 */
public class GcmMessageHandler extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        createNotification(from, message);

        Intent intent = new Intent("com.example.barnes.ummo.CATEGORIES");
        intent.putExtra("message",message);
        sendBroadcast(intent);

    }

    // Creates notification based on title and body received
    private void createNotification(String title, String body) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setContentText(body);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}