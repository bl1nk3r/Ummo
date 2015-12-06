package com.example.barnes.ummoqmasterdashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by barnes on 11/29/15.
 */
public class QFragment extends Fragment
{
    private View parentView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        parentView = inflater.inflate(R.layout.q, container, false);
        listView   = (ListView) parentView.findViewById(R.id.listView);
        initView();
        return parentView;
    }

    private void initView()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                getData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Clicked item!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<String> getData()
    {
        ArrayList<String> qList = new ArrayList<String>();
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        qList.add("XSDEAD242");
        return qList;
    }
}