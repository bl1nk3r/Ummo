package com.release.sihle.umoclasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class Register extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean registered = sp.getBoolean(getString(R.string.PREF_USER_REGISTERED),false);
        if (registered){
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            Log.d("prefs","registered");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public void register(View view){
        try {
            EditText nameEdit = (EditText)findViewById(R.id.nameEdit);
            EditText phoneEdit = (EditText)findViewById(R.id.phoneEdit);

            String urlString = getString(R.string.SERVER_URL)+"/user/register";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("name",nameEdit.getText().toString());
            formPoster.add("cellnum",phoneEdit.getText().toString());
            formPoster.add("data", "data");
            Log.d("function", "exec");
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


                        String objString = response.toString();
                        try{
                            JSONObject obj = new JSONObject(objString);
                            String id = obj.getString("_id");
                            SharedPreferences sp = PreferenceManager
                                    .getDefaultSharedPreferences(Register.this);
                            sp.edit().putString(getString(R.string.PREF_USER_ID),id).apply();
                            sp.edit().putBoolean(getString(R.string.PREF_USER_REGISTERED), true).apply();


                            Log.d("Response",id);

                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);

                        }

                        catch (JSONException jse){
                            Log.d("Eroor in Response",jse.toString());
                        }

                        //Toast.makeText(calee,"Sent Information",Toast.LENGTH_LONG).show();

                    }

                    catch (IOException ioe){
                        Log.e("IO Exception",ioe.toString());
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
