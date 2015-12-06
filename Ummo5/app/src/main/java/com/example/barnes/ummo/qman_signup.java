package com.example.barnes.ummo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barnes.ummo.fragment.SelectableTreeFragment;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.example.barnes.ummo.ummoAPI.QUserListner;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * Created by blinker on 12/3/15.
 */
public class qman_signup extends Activity implements View.OnClickListener,QUserListner{

    private QUser user;
    public static GoogleAnalytics analytics;
    public static Tracker tracker;
    private String name = "Ummo Test SignUp";

    //Overides for QUSER


    @Override
    public void userRegistered(String string) {
        final LinkedHashMap<String, Class<?>> listItems = new LinkedHashMap<>();
        Toast.makeText(this, "Thank you, " + user.getName() + " for signing up ",Toast.LENGTH_LONG).show();
        listItems.put("Selectable Nodes", SelectableTreeFragment.class);
        Class<?> clazz = listItems.values().toArray(new Class<?>[]{})[0];
        Intent i = new Intent(this, SingleFragmentActivity.class);
        i.putExtra(SingleFragmentActivity.FRAGMENT_PARAM, clazz);
        this.startActivity(i);
        this.finish();
        overridePendingTransition(R.layout.fadein, R.layout.fadeout);
        tracker.send(new HitBuilders.EventBuilder().setCategory("Action").setAction("User successfully registered...").build());
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
    public void qReady(String string) {

    }

    @Override
    public void gotJoinedQs(String string) {

    }


    @Override
    public void categoriesReady(String string) {
      /*  categoriesJSON = string;
        Bundle b = getIntent().getExtras();
        //Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);

        Fragment f = Fragment.instantiate(this, fragmentClass.getName());
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
          user.makeNotification();*/
    }

    @Override
    public void allQsReady(String string) {
       // qsJSON = string;
        Log.d("qs",string);
    }

    //Error Handlers

    @Override
    public void joinedQsError(String err) {

    }

    @Override
    public void userRegistrationError(String err) {

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

    }

    @Override
    public void qError(String err) {

    }

    //End Overide for Quser

    Button signupButton;
    EditText ummoAlias, ummoCell, ummoTestID;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.qman_signup_button){
            String ummoAliasStr = ummoAlias.getText().toString();
            String ummoCellVal = ummoCell.getText().toString();
            int ummoTestIDVal = Integer.parseInt(ummoTestID.getText().toString());

           // ummoTester signUpData = new ummoTester(ummoAliasStr, new Integer(ummoCellVal), ummoTestIDVal);
            user = new QUser(this);
            user.register(ummoAliasStr, ummoCellVal);
            /*Intent intent = new Intent(this, Sin.class);
            startActivity(intent);*/

            tracker.send(new HitBuilders.EventBuilder().setCategory("Action").setAction("Clicked 'SignUp'").build());
        }
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qman_signup);


        signupButton = (Button) findViewById(R.id.qman_signup_button);
        ummoAlias = (EditText) findViewById(R.id.qman_alias);
        ummoCell = (EditText) findViewById(R.id.qman_cell);
        ummoTestID = (EditText) findViewById(R.id.qman_testid);

        signupButton.setOnClickListener(this);

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-70767186-1");
        tracker.enableAutoActivityTracking(true);
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(false);
        Log.i("GA says -----", "Setting screen name: " + name);
        tracker.setScreenName("Image~" + name);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
