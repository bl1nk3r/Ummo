package com.example.barnes.ummo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.fragment.Qfragment;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Qs extends AppCompatActivity {

    private ViewPager viewPager;
    private ModelPagerAdapter adapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    List<String> title_list = new ArrayList<>();
    List<Fragment> fragment_list = new ArrayList<>();
    Db db;
    public List<String> qTabsList = null;
    public List<String> qTabsNames = null;
    int numTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Ubuntu-C.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        db = new Db(this);
        db.open();
        qTabsNames = db.getAllQs();
        qTabsList = db.getAllQs();
        numTabs = qTabsList.size();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        PagerModelManager factory = new PagerModelManager();
        factory.addCommonFragment(getFragments(qTabsNames.size()), getTitles());
        adapter = new ModelPagerAdapter(getSupportFragmentManager(), factory);

        viewPager.setAdapter(adapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setShouldExpand(false);
        //pagerSlidingTabStrip.
    }

    private List<String> getTitles()
    {
        if (qTabsNames.size() == 2)
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
            return Lists.newArrayList(qTabsNames.get(0));
    }

    private List<Fragment> getFragments(int ntabs)
    {
        List<Fragment> list = new ArrayList<>();
        if(ntabs == 1)
        {
            Fragment listFragment = new Qfragment();
            list.add(listFragment);
        }
        else if (ntabs == 2)
        {
            Fragment listFragment = new Qfragment();
            Fragment listFragment2 = new Qfragment();
            list.add(listFragment);
            list.add(listFragment2);
        }
        else if (ntabs == 3)
        {
            Fragment listFragment = new Qfragment();
            Fragment listFragment2 = new Qfragment();
            Fragment listFragment3 = new Qfragment();
            list.add(listFragment);
            list.add(listFragment2);
            list.add(listFragment3);
        }
        else if (ntabs == 4)
        {
            Fragment listFragment = new Qfragment();
            Fragment listFragment2 = new Qfragment();
            Fragment listFragment3 = new Qfragment();
            Fragment listFragment4 = new Qfragment();
            list.add(listFragment);
            list.add(listFragment2);
            list.add(listFragment3);
            list.add(listFragment4);
        }
        else if (ntabs == 5)
        {
            Fragment listFragment = new Qfragment();
            Fragment listFragment2 = new Qfragment();
            Fragment listFragment3 = new Qfragment();
            Fragment listFragment4 = new Qfragment();
            Fragment listFragment5 = new Qfragment();
            list.add(listFragment);
            list.add(listFragment2);
            list.add(listFragment3);
            list.add(listFragment4);
            list.add(listFragment5);
        }
        else if (ntabs == 6)
        {
            Fragment listFragment = new Qfragment();
            Fragment listFragment2 = new Qfragment();
            Fragment listFragment3 = new Qfragment();
            Fragment listFragment4 = new Qfragment();
            Fragment listFragment5 = new Qfragment();
            Fragment listFragment6 = new Qfragment();
            list.add(listFragment);
            list.add(listFragment2);
            list.add(listFragment3);
            list.add(listFragment4);
            list.add(listFragment5);
            list.add(listFragment6);
        }
        else if (ntabs == 7)
        {
            Fragment listFragment = new Qfragment();
            Fragment listFragment2 = new Qfragment();
            Fragment listFragment3 = new Qfragment();
            Fragment listFragment4 = new Qfragment();
            Fragment listFragment5 = new Qfragment();
            Fragment listFragment6 = new Qfragment();
            Fragment listFragment7 = new Qfragment();
            list.add(listFragment);
            list.add(listFragment2);
            list.add(listFragment3);
            list.add(listFragment4);
            list.add(listFragment5);
            list.add(listFragment6);
            list.add(listFragment7);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qs, menu);
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