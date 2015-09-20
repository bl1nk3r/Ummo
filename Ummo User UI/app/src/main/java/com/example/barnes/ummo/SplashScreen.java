package com.example.barnes.ummo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.example.barnes.ummo.fragment.SelectableTreeFragment;

import java.util.LinkedHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by barnes on 8/6/15.
 */
public class SplashScreen extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        final LinkedHashMap<String, Class<?>> listItems = new LinkedHashMap<>();
        listItems.put("Selectable Nodes", SelectableTreeFragment.class);

        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Class<?> clazz = listItems.values().toArray(new Class<?>[]{})[0];
                Intent i = new Intent(SplashScreen.this, SingleFragmentActivity.class);
                i.putExtra(SingleFragmentActivity.FRAGMENT_PARAM, clazz);
                SplashScreen.this.startActivity(i);
                SplashScreen.this.finish();
                overridePendingTransition(R.layout.fadein, R.layout.fadeout);
            }
        }, 4000);
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
