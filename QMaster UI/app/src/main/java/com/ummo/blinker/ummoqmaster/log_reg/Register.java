package com.ummo.blinker.ummoqmaster.log_reg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ummo.blinker.ummoqmaster.R;

public class Register extends AppCompatActivity {
    private Firebase qMRef = new Firebase("https://ummo.firebaseio.com/qMaster");
    private EditText etFName, etUName, etService, etLocation, etPassword, etRPassword;
    private Button btRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);

        etFName = (EditText) findViewById(R.id.etFullname);
        etUName = (EditText) findViewById(R.id.etUsername);
        etService = (EditText) findViewById(R.id.etService);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRPassword = (EditText) findViewById(R.id.etRPassword);
        btRegister = (Button) findViewById(R.id.btRegister);

    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qMRef.child("fname").setValue(etFName.getText().toString());
                qMRef.child("uname").setValue(etUName.getText().toString());
                qMRef.child("qService").setValue(etService.getText().toString());
                qMRef.child("location").setValue(etLocation.getText().toString());
                qMRef.child("password").setValue(etRPassword.getText().toString());

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

                qMRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Context context = getApplicationContext();
                        CharSequence chars = etRPassword.getText().toString();
                        Toast toast = Toast.makeText(context, chars, Toast.LENGTH_LONG);
                        toast.show();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }
        });
    }
}
