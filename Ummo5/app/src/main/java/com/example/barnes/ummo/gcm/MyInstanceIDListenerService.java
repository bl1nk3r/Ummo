package com.example.barnes.ummo.gcm;

/**
 * Created by sihle on 12/3/15.
 */
import android.content.Intent;

import com.example.barnes.ummo.gcm.RegistrationIntentService;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by sihle on 11/28/15.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}