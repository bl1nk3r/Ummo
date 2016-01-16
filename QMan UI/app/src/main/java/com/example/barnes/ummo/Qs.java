package com.example.barnes.ummo;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.fragment.QFragmentManager;
import com.example.barnes.ummo.ummoAPI.JoinedQ;
import com.github.florent37.hollyviewpager.HollyViewPager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Qs extends AppCompatActivity
{
    @Bind(R.id.hollyViewPager)
    HollyViewPager hollyViewPager;

    private ViewPager viewPager;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    Db db;
    public List<String> qTabsList = null;
    public List<String> qTabsNames = null;
    public List<String> qPos = null;
    int numTabs;
    String qsJSON;
    //I replaced the String with the JoinedQ Class, to encapsulate more information.
    List<JoinedQ> qnames = new ArrayList<JoinedQ>();
    int tabpos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q);
        String cellnumb =  PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.PREF_USER_CELLNUMBER),"NULL");
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        qsJSON=getIntent().getStringExtra("joinedQs");
        Log.d("QSTRING",qsJSON);
        try{
            String qAlphaNum ="";
            String pos = "";
            JSONArray qArrays = new JSONArray(qsJSON);
            for(int i=0;i<qArrays.length();i++){
                String Qname = qArrays.getJSONObject(i).getJSONObject("managedQ").getString("qName");
                if(cellnumb!="NULL"){
                    JoinedQ joinedQ = new JoinedQ(qArrays.getJSONObject(i),cellnumb);
                    qnames.add(joinedQ);
                    pos=qArrays.getJSONObject(i).getJSONObject("managedQ").getJSONObject("qErs").getJSONObject(cellnumb).getString("position");
                    qAlphaNum = qArrays.getJSONObject(i).getJSONObject("managedQ").getJSONObject("qErs").getJSONObject(cellnumb).getString("numCode");
                }
                //TextView tv = (TextView)findViewById(R.id.man_one_text);
                //        tv.setText(qAlphaNum);
            }
        }
        catch (JSONException jse)
        {
            Log.e("JoinedQS",jse.toString());
        }
        db = new Db(this);
        db.open();
        qTabsNames = db.getAllQs();
        qTabsList = db.getAllQs();
        qPos = db.getQPosition();
        db.close();
        numTabs = qTabsList.size();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        Intent intent = getIntent();
        if (intent != null)
        {
            String tabPos = intent.getStringExtra("tabPos");
            if ((tabPos!=null))
            {
                int int_tabPos = Integer.parseInt(tabPos);
                tabpos = int_tabPos;
                viewPager.setAdapter(new QFragmentManager(getSupportFragmentManager(), qnames, tabpos));
                pagerSlidingTabStrip.setViewPager(viewPager);
                if (tabpos != -1)
                {
                    viewPager.setCurrentItem(tabpos);
                }
            }
            else
            {
                tabpos = -1;
                viewPager.setAdapter(new QFragmentManager(getSupportFragmentManager(), qnames, tabpos));
                pagerSlidingTabStrip.setViewPager(viewPager);
            }
        }
    }

    private List<String> getTitles()
    {
       /* if (qTabsNames.size() == 1)
            return Lists.newArrayList(qTabsNames.get(0));
        else if (qTabsNames.size() == 2)
            return Lists.newArrayList(qTabsNames.get(0), qTabsNames.get(1));
        else if (qTabsNames.size() == 3)
            return Lists.newArrayList(qTabsNames.get(0),qTabsNames.get(1),qTabsNames.get(2));
        else if (qTabsNames.size() == 4)
            return Lists.newArrayList(qTabsNames.get(0),qTabsNames.get(1),qTabsNames.get(2),qTabsNames.get(3));
        else if (qTabsNames.size() == 5)
            return Lists.newArrayList(qTabsNames.get(0),qTabsNames.get(1),qTabsNames.get(2),qTabsNames.get(3),qTabsNames.get(4));
        else if (qTabsNames.size() == 6)
            return Lists.newArrayList(qTabsNames.get(0),qTabsNames.get(1),qTabsNames.get(2),qTabsNames.get(3),qTabsNames.get(4),qTabsNames.get(5));
        else if (qTabsNames.size() == 7)
            return Lists.newArrayList(qTabsNames.get(0),qTabsNames.get(1),qTabsNames.get(2),qTabsNames.get(3),qTabsNames.get(4),qTabsNames.get(5),qTabsNames.get(6));
        else
            return Lists.newArrayList(qTabsNames.get(0));*/
        return new ArrayList<String>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}