package com.release.sihle.umoclasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<UmmoQ> ummoQs= new ArrayList<UmmoQ>();

    public List<UmmoQ> getUmmoSet(){
        return ummoQs;
    }
    public void getQs(){
        try {
            String urlString = getString(R.string.SERVER_URL)+"/user/avalqs";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();


                        String objString = response.toString();
                        Log.d("Respose",objString);
                        try{
                            ummoQs.clear();
                            JSONArray jsonArray = new JSONArray(objString);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                UmmoQ ummoQ= new UmmoQ();
                                ummoQ.setId(obj.getString("_id"));
                                ummoQ.setName(obj.getString("qName"));
                                ummoQs.add(ummoQ);
                                //ummoQ.setLength(obj.getInt());
                            }

                            mAdapter.notifyDataSetChanged();


                        }

                        catch (JSONException jse){
                            Log.d("Eroor in Response",jse.toString());
                        }

                        //Toast.makeText(calee,"Sent Information",Toast.LENGTH_LONG).show();

                    }

                    catch (IOException ioe){
                        Log.e("IO Exception",ioe.toString());
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }
    }
    public void joinQ(View view){
        //mRecyclerView.
       // Log.d("index",new Integer(position).toString());
        UmmoQ ummoQ = ummoQs.get(0);
        try {
            String urlString = getString(R.string.SERVER_URL)+"/user/joinq";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("qid",ummoQ.getId());
            formPoster.add("uid",PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.PREF_USER_ID), ""));
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();


                        String objString = response.toString();
                        Log.d("Respose",objString);
                        //Toast.makeText(calee,"Sent Information",Toast.LENGTH_LONG).show();

                    }

                    catch (IOException ioe){
                        Log.e("IO Exception",ioe.toString());
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception",me.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getQs();
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //List<UmmoQ> myDataset =getUmmoSet();
        // specify an adapter (see also next example)
        ummoQs.add(new UmmoQ());
        mAdapter = new MyAdapter(ummoQs);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

class UmmoClass
{

    public void register(){

    }

    public void joinQ(String qid){

    }

    public void leaveq(String qid){

    }

    public void getJoinedQs(){

    }

    public void getQupdates(String qid){

    }


}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<UmmoQ> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mdataView;
        public Button but;
        public ViewHolder(View v) {
            super(v);
            mTextView =(TextView) v.findViewById(R.id.info_text);
            mdataView =(TextView) v.findViewById(R.id.data_view);
            but = (Button)v.findViewById(R.id.join_b);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<UmmoQ> myDataset) {
        mDataset = myDataset;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

