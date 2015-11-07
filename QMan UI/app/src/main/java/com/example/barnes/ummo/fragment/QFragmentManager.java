package com.example.barnes.ummo.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by barnes on 10/12/15.
 */
public class QFragmentManager extends FragmentPagerAdapter
{
    int frag_count;
    List<String> list_;
    int tposition;

    public QFragmentManager(FragmentManager fm, List<String> list, int tabp)
    {
        super(fm);
        list_ = list;
        frag_count = list.size();
        tposition = tabp;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return list_.get(position);
    }

    @Override
    public Fragment getItem(int position)
    {
        return Qfragment.newInstance(position + 1, tposition);
    }

    @Override
    public int getCount()
    {
        return frag_count;
    }
}