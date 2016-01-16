package com.example.barnes.ummo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.barnes.ummo.fragment.BaseActivity;
import com.example.barnes.ummo.fragment.ScrollViewFragment;
import com.example.barnes.ummo.ummoAPI.JoinedQ;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.github.florent37.hollyviewpager.HollyViewPager;
import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

/**
 * Created by barnes on 12/30/15.
 */
public class Main_Activity extends BaseActivity
{
    int pageCount = 2;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.hollyViewPager)
    HollyViewPager hollyViewPager;
    String qsJSON;
    List<JoinedQ> qnames = new ArrayList<JoinedQ>();
    UmmoBrouadcatReciever broadcastReceiver;
    String qname;
    String qposition;
    String qalphaNum;

    @Override
    protected void onPause()
    {
        exitToBottomAnimation();
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("com.example.barnes.ummo.CATEGORIES"));
        //enterFromBottomAnimation();
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        String cellnumb =  PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.PREF_USER_CELLNUMBER),"NULL");
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setBackgroundColor(getResources().getColor(R.color.ummo));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hollyViewPager.getViewPager().setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {
                return ((page + 4) % 10) / 10f;
            }
        });

        Bundle extras = getIntent().getExtras();
        Intent intent = getIntent();

       /* if(extras == null)
        {
            hollyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
            {
                @Override
                public Fragment getItem(int position)
                {
                    //if(position%2==0)
                    //    return new RecyclerViewFragment();
                    //else
                    qnames = new QUser(Main_Activity.this).getLocalJoinedQList();
                    enterFromBottomAnimation();
                    return ScrollViewFragment2.newInstance((String) getPageTitle(position));
                }

                @Override
                public int getCount() {
                    return pageCount;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return "TITLE" + position;
                }
            });
        }*/
        //else
        //{
        qnames = new QUser(this).getLocalJoinedQList();

        if (qnames.isEmpty())
        {
            TextView emptyQList = (TextView) findViewById(R.id.empty_qlist);
            emptyQList.setVisibility(View.VISIBLE);
        }
        else
        {
            Log.e("QNAME)",qnames.get(0).getqName());
        }

            //Log.e("QNAME)",qnames.get(0).getqName());
            /*qsJSON = getIntent().getStringExtra("joinedQs");
            Log.d("QSTRING", qsJSON);
            try
            {
                String qAlphaNum ="";
                String pos = "";
                JSONArray qArrays = new JSONArray(qsJSON);
                for(int i=0;i<qArrays.length();i++)
                {
                    String q_name = qArrays.getJSONObject(i).getJSONObject("managedQ").getString("qName");
                    qname = q_name;
                    if(cellnumb!="NULL")
                    {
                        JoinedQ joinedQ = new JoinedQ(qArrays.getJSONObject(i),cellnumb);
                        qnames.add(joinedQ);
                        pos = qArrays.getJSONObject(i).getJSONObject("managedQ").getJSONObject("qErs").getJSONObject(cellnumb).getString("position");
                        qposition = getIntent().getStringExtra("qpos");
                        qAlphaNum = qArrays.getJSONObject(i).getJSONObject("managedQ").getJSONObject("qErs").getJSONObject(cellnumb).getString("numCode");
                        qalphaNum = qAlphaNum;
                    }
                    //TextView tv = (TextView)findViewById(R.id.man_one_text);
                    //tv.setText(qAlphaNum);
                }
            }
            catch (JSONException jse)
            {
                Log.e("JoinedQS",jse.toString());
            }*/

            hollyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
            {
                @Override
                public Fragment getItem(int position)
                {
                    //if(position%2==0)
                    //    return new RecyclerViewFragment();
                    //else
                    enterFromBottomAnimation();
                    return ScrollViewFragment.newInstance(qnames.get(position),qname, "4", "qalphaNum");
                }

                @Override
                public int getCount() {
                    return qnames.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return qnames.get(position).getqName();
                }
            });
       // }
    }

    // handler for received Intents for the "my-event" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            Log.e("receiver", "Got message: " + message);
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("com.example.barnes.ummo.CATEGORIES"));
    }
}

class UmmoBrouadcatReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String mess =intent.getStringExtra("message");
        Log.d("MESSAGE",mess);
    }
}