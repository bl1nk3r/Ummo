package com.example.barnes.ummo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.barnes.ummo.fragment.SelectableTreeFragment;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.example.barnes.ummo.ummoAPI.QUserListner;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by barnes on 8/6/15.
 */
public class SplashScreen extends Activity implements QUserListner
{
    private ProgressDialog p_Dialog;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    private String name = "Ummo Splash";
    final LinkedHashMap<String, Class<?>> listItems = new LinkedHashMap<>();
    long Delay = 10000;

    @Override
    public void joinedQsError(String err) {

    }

    @Override
    public void gotJoinedQs(String string) {

    }

    @Override
    public void qReady(String string) {

    }

    @Override
    public void qError(String err) {

    }

    @Override
    public void userRegistered(String string) {
        // parse the string a JSONObject or JSONArray
        //Registration  is successful , Start Main Activity
    }

    @Override
    public void qJoined(String string) {



    }

    @Override
    public void qLeft(String string) {

    }

    @Override
    public void updated(String string) {

    }

    @Override
    public void categoriesReady(String string) {

    }

    @Override
    public void allQsReady(String string) {
        Log.d("data",string);
        listItems.put("Selectable Nodes", SelectableTreeFragment.class);
        Class<?> clazz = listItems.values().toArray(new Class<?>[]{})[0];
        Intent i = new Intent(SplashScreen.this, SingleFragmentActivity.class);
        i.putExtra(SingleFragmentActivity.FRAGMENT_PARAM, clazz);
        SplashScreen.this.startActivity(i);
        SplashScreen.this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
    }

    @Override
    public void userRegistrationError(String err) {
        //There was an Error During Registration
    }

    @Override
    public void qJoinedError(String err) {

    }

    @Override
    public void qLeftError(String err) {

    }

    @Override
    public void updateError(String err) {

    }

    @Override
    public void categoriesError(String err) {

    }

    @Override
    public void allQError(String err) {
        Log.e("Error Getting Qs",err);
    }

    //End Ovrriding Interface
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-70767186-1");
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(false);
        Log.i("GA says -----", "Setting screen name: " + name);
        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        Timer RunSplash = new Timer();
        if (isInternetOn() == true)
        {
            TimerTask ShowSplash = new TimerTask() {
                @Override
                public void run()
                {
                    //Close SplashScreenActivity.class
                    finish();
                    //QUser user = new QUser(SplashScreen.this);
                    //user.getAvailableQs();
                    //Start MainActivity.class
                    listItems.put("Selectable Nodes", SelectableTreeFragment.class);
                    Class<?> clazz = listItems.values().toArray(new Class<?>[]{})[0];
                    Intent i = new Intent(SplashScreen.this, SingleFragmentActivity.class);
                    i.putExtra(SingleFragmentActivity.FRAGMENT_PARAM, clazz);
                    SplashScreen.this.startActivity(i);
                    SplashScreen.this.finish();
                    overridePendingTransition(R.layout.fadein, R.layout.fadeout);
                }
            };
            //Start the timer
            RunSplash.schedule(ShowSplash, Delay);
        }
    }

    public final boolean isInternetOn()
    {
        //get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED  ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED )
        {
            //if connected with internet
            //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        }
        else if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {
            Toast.makeText(this, " Not internet Connection ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    class LoadQues extends AsyncTask<String, String, String>
    {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            p_Dialog = new ProgressDialog(SplashScreen.this);
            p_Dialog.setMessage("Loading Ques ...");
            p_Dialog.setIndeterminate(false);
            p_Dialog.setCancelable(false);
            p_Dialog.show();
        }
        /**
         * getting papers JSON
         * */
        protected String doInBackground(String... args)
        {
            Log.d("app","Does in background");
            QUser user = new QUser(SplashScreen.this);
            user.getAvailableQs();
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url)
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}