package com.ummo.blinker.ummoqmasternavsample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.ummo.blinker.ummoqmasternavsample.actions.CreateQ;
import com.ummo.blinker.ummoqmasternavsample.actions.ActivateQ;
import com.ummo.blinker.ummoqmasternavsample.actions.ReviewQ;


/**
 * A simple {@link Fragment} subclass.
 */

public class AccountFragment extends Fragment implements View.OnClickListener {

    private Firebase mRef;

    public AccountFragment() {
        // Required empty public constructor
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);
        Button createQButton = (Button) view.findViewById(R.id.btCreateQ);
        createQButton.setOnClickListener(this);

        Button initQButton = (Button) view.findViewById(R.id.btInitQ);
        initQButton.setOnClickListener(this);

        Button reviewQButton = (Button) view.findViewById(R.id.btReviewQ);
        reviewQButton.setOnClickListener(this);

        mRef = new Firebase(getString(R.string.UmmoFirebaseURL));

        return view;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btCreateQ:
                Intent i = new Intent(v.getContext(), CreateQ.class);
                startActivity(i);
                break;

            case R.id.btInitQ:
                Intent j = new Intent(v.getContext(), ActivateQ.class);
                startActivity(j);
                break;

            case R.id.btReviewQ:
                Intent k = new Intent(v.getContext(), ReviewQ.class);
                startActivity(k);
                break;
        }
    }
}
