package com.example.sihle.networklib;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sihle on 9/19/15.
 */
public class QUser {
    private Activity callingActivity;
    private String uCellNumber;
    private boolean registered=false;
    private String uName;

    public boolean isRegistered(){
        return registered;
    }
    public void setCallingActivity(Activity activity) {
        callingActivity = activity;
    }
    public void register(final String name, final String cellNumber){
        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL)+"/user/register";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("cellnum",cellNumber);
            formPoster.add("alias",name);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final String uname = name;
                        final String cellphone = cellNumber;
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        String objString = response.toString();
                        ((QUserListner)callingActivity).userRegistered(objString);
                        //This would mean the registration was compleate
                            //
                            //JSONObject obj = new JSONObject(objString);
                            //Set the Shared Preferences for User Name and CellNumber
                            SharedPreferences sp = PreferenceManager
                                    .getDefaultSharedPreferences(callingActivity);
                            sp.edit().putString(callingActivity.getString(R.string.PREF_USER_NAME),uname).apply();
                            sp.edit().putString(callingActivity.getString(R.string.PREF_USER_CELLNUMBER),cellphone).apply();
                            sp.edit().putBoolean(callingActivity.getString(R.string.PREF_USER_REGISTERED), true).apply();

                            //Log.d("Response",id);



                        //Toast.makeText(calee,"Sent Information",Toast.LENGTH_LONG).show();

                    }

                    catch (final IOException ioe){

                        Log.e("IO Exception",ioe.toString());
                        Log.e("IO Exception", ioe.toString());
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner)callingActivity).userRegistrationError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }


    }

    public QUser(Activity activity){
        setCallingActivity(activity);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(callingActivity);
        registered = sp.getBoolean(callingActivity.getString(R.string.PREF_USER_REGISTERED), false);
        if (registered){
            uName=sp.getString(callingActivity.getString(R.string.PREF_USER_NAME),"NO NAME");
            uCellNumber=sp.getString(callingActivity.getString(R.string.PREF_USER_CELLNUMBER),"NO NUMBER");
         //   Intent intent = new Intent(Register.this, MainActivity.class);
          //  startActivity(intent);
          //  finish();
            Log.d("prefs","registered");
        }

    }

    public void getAvailableQs(){
        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL)+"/user/availQs";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            //formPoster.add("uid",uCellNumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).allQsReady(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner)callingActivity).allQError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }

    }

    public void setName(){

    }

    public void setCellNumber(){

    }

    public String getName(){
        return uName;
    }

    public String getCellNumb(){
        return uCellNumber;
    }

    public void getCategories(){

    }

    public void getProvides(){

    }

    public void updateJoinedQs(){
        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL)+"/user/joinedQs";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid",uCellNumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).updated(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner)callingActivity).updateError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }

    }

    public void joinQ(String qCellnumber){

        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL)+"/user/joinQ";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid",uCellNumber);
            formPoster.add("qid",qCellnumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).qJoined(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner)callingActivity).qJoinedError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }


    }

    public void leaveQ(String qCellnumber){

        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL)+"/user/leaveQ";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid",uCellNumber);
            formPoster.add("qid",qCellnumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).qLeft(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner)callingActivity).qLeftError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }



    }

}
