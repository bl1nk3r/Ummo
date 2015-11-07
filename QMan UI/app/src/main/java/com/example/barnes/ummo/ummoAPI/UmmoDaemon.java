package com.example.barnes.ummo.ummoAPI;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by barnes on 10/4/15.
 */
public class UmmoDaemon extends Service
{
    //    private QUser user;
    Activity calee;
    private final IBinder mBinder = new LocalBinder();


    public UmmoDaemon() {
    }

    public void getUpadates(final QUser user){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Log.d("app", "In Service");
                    user.updateJoinedQs();
                    try {
                        Thread.sleep(30000);
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
