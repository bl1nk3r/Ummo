package com.ummo.blinker.ummoqmaster.actions;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ummo.blinker.ummoqmaster.R;

public class ActivateQ extends AppCompatActivity {

    private Firebase qRef;


    public ActivateQ() {
    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_q);
        Firebase.setAndroidContext(this);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activateQRL);

        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.q0) {
                    //Toast.makeText(getApplicationContext(), getString(R.string.q0Text)+": is active!", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.q1) {
                    //Toast.makeText(getApplicationContext(), getString(R.string.q1Text)+": is active!", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.q2) {
                    //Toast.makeText(getApplicationContext(), getString(R.string.q2Text)+": is active!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), getString(R.string.q3Text)+": is active!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        q0 = (RadioButton) findViewById(R.id.q0);
        q1 = (RadioButton) findViewById(R.id.q1);
        q2 = (RadioButton) findViewById(R.id.q2);
        q3 = (RadioButton) findViewById(R.id.q3);

        activateQButton = (Button) findViewById(R.id.activateQButton);
        activateQButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();

                if (checkedId == q0.getId()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.q0Text)+": is now active!", Toast.LENGTH_SHORT).show();
                } else if (checkedId == q1.getId()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.q1Text)+": is now active!", Toast.LENGTH_SHORT).show();
                } else if (checkedId == q2.getId()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.q2Text)+": is now active!", Toast.LENGTH_SHORT).show();
                } else if (checkedId == q3.getId()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.q3Text)+": is now active!", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        final Button activateQButton = (Button) findViewById(R.id.activateQBT);

        qRef = new Firebase("https://ummo.firebaseio.com/ummoQ");

        qRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String condition = (String) dataSnapshot.child("qName").getValue();
                activateQButton.setText(condition);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        qRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map<String, Object> newQueue = (Map<String, Object>) dataSnapshot.getValue();

                //Toast.makeText(getApplicationContext(), "QTag is: "+newQueue.get("qName"), Toast.LENGTH_SHORT).show();
                //firebaseTV.setText((CharSequence) newQueue);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String qName = (String) dataSnapshot.child("qName").getValue();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String qName = (String) dataSnapshot.child("qName").getValue();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                String qName = (String) dataSnapshot.child("qName").getValue();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        activateQButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                qRef.child("qActive").setValue("true");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_init_q, menu);
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
