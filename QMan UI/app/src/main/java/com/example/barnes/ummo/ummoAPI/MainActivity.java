package com.example.barnes.ummo.ummoAPI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.barnes.ummo.R;

/**
 * Created by barnes on 11/1/15.
 */
public class MainActivity extends AppCompatActivity implements QUserListner,QMasterListener
{
    QUser qUser;


    @Override
    public void qReady(String string) {

    }

    @Override
    public void joinedQsError(String err) {

    }

    @Override
    public void gotJoinedQs(String string) {

    }

    @Override
    public void qError(String err) {

    }

    //These are the functions to be implemented
    @Override
    public void userRegistered(String string) {
        // parse the string a JSONObject or JSONArray
    }

    @Override
    public void qJoined(String string) {

    }

    @Override
    public void qLeft(String string) {

    }

    @Override
    public void updated(String string) {
        //The String We get Here is from the Daemon, Its The information on Joined Qs.
        Log.d("Updates",string);

    }

    @Override
    public void categoriesReady(String string) {

    }

    @Override
    public void allQsReady(String string) {
        Log.d("Suceess",string);
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

    //End Ovrriding Interface

    //Functions to be implemented for qmaster

    @Override
    public void qCreated(String string) {

    }

    @Override
    public void registered(String string) {
        Log.d("Success",string);
    }

    @Override
    public void qDestroyed(String string) {

    }

    @Override
    public void userDQd(String string) {

    }

    @Override
    public void userMoved(String string) {

    }

    @Override
    public void feedBackRecieved(String string) {

    }

    @Override
    public void myQRecieved(String string) {

    }

    @Override
    public void updatesRecieved(String string) {

    }

    @Override
    public void createQError(String string) {

    }

    @Override
    public void registrationError(String string) {
        Log.d("Registration Err",string);
    }

    @Override
    public void onQDestroyError(String sting) {

    }

    @Override
    public void onUserDQError(String string) {

    }

    @Override
    public void onUserMoveError(String string) {

    }

    @Override
    public void onFeedBackError(String string) {

    }

    @Override
    public void onUpdtaesError(String string) {

    }

    //End Functions to be implemented for QMaster

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ummo_splash);

        qUser=new QUser(this);
        if(qUser.isRegistered()) {
            qUser.getAvailableQs();
        }

        else {
            Log.d("User","Not Registered");
            qUser.register("Mbhamali Sihle","76558975");
        }

        //qUser.updateJoinedQs("");


        //This Example starts The Daemon
        Log.e("about", "to call fuction");
        qUser.startUpdatesDaemon();
        //End Of Example for Binding a service
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}