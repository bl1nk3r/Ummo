package com.ummo.blinker.ummoqmasternavsample;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */

public class MyQFragment extends Fragment {

    private Firebase qRef;
    private TextView myQTV;
    private TextView qActiveTV;

    public MyQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_q, container, false);

        qRef = new Firebase("https://ummo.firebaseio.com/");
        myQTV = (TextView) v.findViewById(R.id.myQViewTV);
        qActiveTV = (TextView) v.findViewById(R.id.qActiveTV);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        qRef.child("ummoQ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String qActive = (String) dataSnapshot.child("qActive").getValue();
                qActiveTV.setText(qActive);
                String myQ = (String) dataSnapshot.child("qName").getValue();
                myQTV.setText(myQ);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
