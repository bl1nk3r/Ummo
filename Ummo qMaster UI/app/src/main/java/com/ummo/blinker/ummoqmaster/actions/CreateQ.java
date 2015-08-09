package com.ummo.blinker.ummoqmaster.actions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.ummo.blinker.ummoqmaster.R;

import java.util.ArrayList;

public class CreateQ extends AppCompatActivity{

    private Firebase qRef = new Firebase("https://ummo.firebaseio.com/ummoQ");
    private Firebase servRef = new Firebase("https://ummo.firebaseio.com/qServices/qServiceCategory");
    private EditText qNameText, qTagText, qFrameText, qServiceText;
    private Button saveQButton;

    private String qTag, qService, qFrame;
    private ArrayList mSelectedItems;

    public CreateQ() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_q);
        Firebase.setAndroidContext(this);
        //Firebase.getDefaultConfig().setPersistenceEnabled(true);            //Setting disk persistence for offline sync
    }

    @Override
    protected void onStart() {
        super.onStart();

        qNameText = (EditText) findViewById(R.id.qNameET);
        qNameText.setInputType(InputType.TYPE_CLASS_TEXT);

        qTagText = (EditText) findViewById(R.id.qTagET);
        qTagText.setInputType(InputType.TYPE_CLASS_TEXT);

        qFrameText = (EditText) findViewById(R.id.qFrameET);
        qFrameText.setInputType(InputType.TYPE_CLASS_TEXT);

        qServiceText = (EditText) findViewById(R.id.qServiceET);
        qServiceText.setInputType(InputType.TYPE_CLASS_TEXT);

        saveQButton = (Button) findViewById(R.id.saveQButton);

        saveQButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qTagText.getText() != null && qFrameText.getText() != null && qServiceText.getText() != null) {
                    qRef.child("qName").setValue(qNameText.getText().toString());
                    qRef.child("qTag").setValue(qTagText.getText().toString());
                    qRef.child("qService").setValue(qServiceText.getText().toString());
                    qRef.child("qFrame").setValue(qFrameText.getText().toString());
                    qRef.child("qActive").setValue("false");

                    servRef.child("miscServices").setValue(qServiceText.getText().toString());

                    /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();*/

                    Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                    //startActivity(intent);

                } else if (qNameText.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in form first...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_q, menu);
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

    /*protected Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.dialog_title).setItems(R.array.options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return  builder.create();
    }*/
}
