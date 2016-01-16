package com.example.barnes.ummo.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.barnes.ummo.ummoAPI.JoinedQ;

import java.util.List;

/**
 * Created by barnes on 10/12/15.
 */
public class QFragmentManager extends FragmentPagerAdapter
{
    int frag_count;
    List<JoinedQ> list_;
    int tposition;

    //So I had to change the Constructor too. or
    public QFragmentManager(FragmentManager fm, List<JoinedQ> list, int tabp)
    {
        super(fm);
        list_ = list;
        frag_count = list.size();
        tposition = tabp;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return list_.get(position).getqName();
    }

    @Override
    public Fragment getItem(int position)
    {
        return Qfragment.newInstance(position + 1, tposition, list_.get(position));
    }

    @Override
    public int getCount()
    {
        return frag_count;
    }
}