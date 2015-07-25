package com.example.barnes.ummoq.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barnes.ummoq.R;

/**
 * Created by barnes on 7/25/15.
 */
public class Qfragment extends Fragment
{
    private android.widget.ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.q, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView()
    {
        scrollView = (android.widget.ScrollView) getView().findViewById(R.id.scroll_view);
    }
}
