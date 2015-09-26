package com.example.sihle.networklib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements QUserListner{

    QUser qUser;

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

    }

    @Override
    public void categoriesReady(String string) {

    }

    @Override
    public void allQsReady(String string) {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qUser=new QUser(this);

        if (!qUser.isRegistered()){
            qUser.register("name","76492278");
        }

        else {
            qUser.getAvailableQs();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
