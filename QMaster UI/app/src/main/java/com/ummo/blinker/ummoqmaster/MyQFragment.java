package com.ummo.blinker.ummoqmaster;


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
import com.ummo.blinker.ummoqmaster.log_reg.QM;
import com.ummo.blinker.ummoqmaster.log_reg.QMLocalStore;


/**
 * A simple {@link Fragment} subclass.
 */

public class MyQFragment extends Fragment {

    private Firebase qRef;
    private TextView myQTV;
    private TextView myDet;
    private TextView qActiveTV;
    private QMLocalStore qMLocalStore;
    private Firebase qMRef;

    public MyQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_q, container, false);
        qMLocalStore = new QMLocalStore(v.getContext());

        qRef = new Firebase("https://ummo.firebaseio.com/");
        qMRef = new Firebase("https://ummo.firebaseio.com/");
        myQTV = (TextView) v.findViewById(R.id.myQViewTV);
        myDet = (TextView) v.findViewById(R.id.myQTV);
        qActiveTV = (TextView) v.findViewById(R.id.qActiveTV);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        qRef.child("ummoQ").addValueEventListener(new ValueEventListener() {
            public String myQ;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String qActive = (String) dataSnapshot.child("qActive").getValue();
                myQ = (String) dataSnapshot.child("qName").getValue();
                if (qActive.equals("true")) {
                    qActiveTV.setText(qActive);
                    myQTV.setText(myQ);
                } else if (qActive.equals("false")) {
                    qActiveTV.setText(qActive);
                    myQTV.setText(myQ);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
