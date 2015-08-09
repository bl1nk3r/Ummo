package com.example.barnes.ummo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.barnes.ummo.R;

/**
 * Created by barnes on 7/26/15.
 */
public class Qfragment extends Fragment
{
    private android.widget.ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.q_list_items,container, false);
        Button man_Q_4 = (Button)view.findViewById(R.id.man_four);
        man_Q_4.setText("#3");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
}
