package com.example.barnes.ummoq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.barnes.ummoq.fragments.Qfragment;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

public class Qs extends ActionBarActivity
{
    private ViewPager viewPager;
    private ModelPagerAdapter adapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.q);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        PagerModelManager factory = new PagerModelManager();
        factory.addCommonFragment(getFragments(), getTitles());
        adapter = new ModelPagerAdapter(getSupportFragmentManager(), factory);

        viewPager.setAdapter(adapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    private List<String> getTitles()
    {
        return Lists.newArrayList("#FNB1");
    }

    private List<Fragment> getFragments()
    {
        List<Fragment> list = new ArrayList<>();
        Fragment listFragment = new Qfragment();
        list.add(listFragment);
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
