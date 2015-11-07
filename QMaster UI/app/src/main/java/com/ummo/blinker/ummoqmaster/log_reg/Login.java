package com.ummo.blinker.ummoqmaster.log_reg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ummo.blinker.ummoqmaster.MainActivity;
import com.ummo.blinker.ummoqmaster.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private TextView regLink;
    private Firebase qMRef;

    private QMLocalStore qMLocalStore;
    private CharSequence password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        regLink = (TextView) findViewById(R.id.tvRegister);

        regLink.setOnClickListener(this);

        qMLocalStore = new QMLocalStore(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        qMRef = new Firebase("https://ummo.firebase.com/qMaster/");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = getApplicationContext();
                final int duration = Toast.LENGTH_LONG;

                qMRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        password = (String) dataSnapshot.child("password").getValue();
                        Toast.makeText(context, password, duration).show();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                CharSequence logPass = etPassword.getText().toString();


                if (logPass.equals("admin")) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    Toast toast = Toast.makeText(context, "Welcome to Ummo QMaster!!!", duration);
                    toast.show();
                }

                else{
                    Toast toast = Toast.makeText(context, "Please try again!!!", duration);
                    toast.show();
                }


            }
        });
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvRegister:

                startActivity(new Intent(this, Register.class));

                break;

        }

    }


    private void logQMIn(QM returnedQM) {
        qMLocalStore.storeQMData(returnedQM);
        qMLocalStore.setQMLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();

        //Logs 'installs' and 'app activate' app events
        //AppEventsLogger.activateApp(this);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();

        //Logs 'app deactivate' app event
        //AppEventsLogger.deactivateApp(this);
    }

    private void showErrorMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Incorrect user details...");
        dialogBuilder.setPositiveButton("Ok", null);
        dialogBuilder.show();
    }
}
