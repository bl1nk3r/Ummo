package com.example.barnes.ummo.ummoAPI;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.barnes.ummo.R;

/**
 * Created by barnes on 11/1/15.
 */
public class UmmoDaemon extends Service
{
    //    private QUser user;
    Activity calee;
    private final IBinder mBinder = new LocalBinder();

    int mId =0;
    public UmmoDaemon() {
    }

    public void makeNotification(){
        Log.e("eroor","Making Notification");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("This Is Your Ummoment")
                        .setContentText("Click to View")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_SOUND);
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this,calee.getClass());

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(calee.getClass());
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());

    }
    public void getUpadates(final QUser user){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.d("app","In Service");
                    //user.updateJoinedQs("");
                    try {
                        Thread.sleep(60000);
                    }

                    catch (InterruptedException e){
                        Log.e("Updates Thread",e.toString());
                    }
                }
            }
        });

        t.start();
    }

    public class LocalBinder extends Binder {
        public UmmoDaemon getService(){
            return UmmoDaemon.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    public void setCalee(Activity callingEctivity){
        calee=callingEctivity;
    }
}