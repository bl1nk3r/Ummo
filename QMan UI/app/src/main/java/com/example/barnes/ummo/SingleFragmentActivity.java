package com.example.barnes.ummo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.fragment.SelectableTreeFragment;
import com.example.barnes.ummo.holder.SelectableItemHolder;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.example.barnes.ummo.ummoAPI.QUserListner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by barnes on 8/7/15.
 */
public class SingleFragmentActivity extends Activity implements QUserListner
{
    Db db;
    private QUser user;
    private vQ selectedQ;
    private String categoriesJSON;
    private String qsJSON;
    private String qJSON;
    private String joinedQsJSON;
    private String serviceProvidersJSON;
    //Some setters and getters for qs
    public void setSelectedQ(vQ vq)
    {
        selectedQ=vq;
    }
    public String getJoinedQsJSON(){
        return joinedQsJSON;
    }
    public void joinSelectedQ(){
        user.joinQ(selectedQ.getCellNumb());
    }
    public void setQinfoDialog(SelectableItemHolder info){
        selectedQ.setInfo(info);
    }
    public vQ getSelectedQ(){
        return selectedQ;
    }
    public void setSelectedQ(String cell)
    {
        selectedQ = new vQ();
        selectedQ.setCellNumb(cell);
    }
    public void setSelectedQ(JSONObject obj)
    {
        selectedQ = new vQ();
        selectedQ.setFromJSON(obj);
    }
    //end setters and getters for qs
    //Overriding for QUserListener Begins Here
    //sucess Handlers
    @Override
    public void gotJoinedQs(String string) {
        Log.d("JoinedQs",string);
    }
    @Override
    public void qReady(String string)
    {
        qJSON=string;
        Log.d("QDATA",string);
        try
        {
            JSONObject object = new JSONObject(qJSON);
            selectedQ.getInfo().dialog(qJSON);
        }
        catch (JSONException jse)
        {
            Log.d("Error",jse.toString());
        }
    }

    @Override
    public void qError(String err)
    {
    }

    public String getCategoriesJSON(){
        return categoriesJSON;
    }

    public String getQsJSON(){
        return qsJSON;
    }

    public String getServiceProvidersJSON(){
        return serviceProvidersJSON;
    }

    public QUser getUser(){
        return user;
    }

    public final static String FRAGMENT_PARAM = "fragment";

    @Override
    public void userRegistered(String string)
    {
    }

    @Override
    public void qJoined(String string)
    {
    }

    @Override
    public void qLeft(String string)
    {
    }

    @Override
    public void updated(String string)
    {
        Log.d("joinedQs",string);
        try
        {
            //JSONObject object = new JSONObject(string);
            JSONArray JSONqs = new JSONArray(string);
            joinedQsJSON = JSONqs.toString();
            if(JSONqs.length()>0)
            {
                Intent i = new Intent();
                i.putExtra("joinedQs",joinedQsJSON);
                //i.putExtra("qpos",string2);
                i.setClass(this, Main_Activity.class);
                this.startActivity(i);
            }
        }
        catch (JSONException jse)
        {
            Log.e("JoinedQs",jse.toString());
        }
    }

    @Override
    public void categoriesReady(String string)
    {
        categoriesJSON = string;
      /*  Bundle b = getIntent().getExtras();
        Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);
        Fragment f = (Fragment)(new SelectableTreeFragment());//Fragment.instantiate(this, SelectableTreeFragment.class);
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
        //  user.makeNotification();
        //Intent i = new Intent(SingleFragmentActivity.this, SelectableTreeFragment.class);
        //i.putExtra("message", b);
        //startActivity(i);*/
    }
    @Override
    public void allQsReady(String string)
    {
        qsJSON = string;
        Log.d("qs",string);
    }
    //Error Handlers
    @Override
    public void joinedQsError(String err)
    {
    }
    @Override
    public void userRegistrationError(String err)
    {
    }
    @Override
    public void qJoinedError(String err)
    {
    }
    @Override
    public void qLeftError(String err)
    {
    }
    @Override
    public void updateError(String err)
    {
    }
    @Override
    public void categoriesError(String err)
    {
    }
    @Override
    public void allQError(String err)
    {
    }
    //End Overriding for QUserListener
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_single_fragment);
        db = new Db(this);
        db.open();
        db.insertServiceTypeQ(101, "Financial Services");
        db.insertServiceProviderQ(102, "SwaziBank", 101);
        db.insertServiceProviderQ(103, "FNB", 101);
        db.insertServiceNameQ(104, "Deposit", 101, 102);
        db.insertServiceNameQ(105, "Withdrawals", 101, 103);
        //db.insertServiceNameQ(106, "Foreign Exchange", 101, 102);
        /*db.insertServiceTypeQ(101, "Government Services");
        db.insertServiceProviderQ(201, "SRA", 101);
        db.insertServiceNameQ(304, "Tax Payment", 101, 201);
        db.insertServiceNameQ(305,"Trading licence",101,201);
        /*db.insertServiceTypeQ(102, "MTN Services");
        db.insertServiceProviderQ(401, "Ezulwini MTN", 102);
        db.insertServiceNameQ(500, "SIM Swap", 102, 401);
        db.insertServiceNameQ(501, "Air Time", 102, 401);
        db.insertServiceNameQ(502, "Internet Services", 102, 401);
        //db.insertServiceTypeQ(100, "MTN Services");
        //db.insertServiceProviderQ(2, "Ezulwini MTN", 1);
        //db.insertServiceNameQ(3, "SIM Swap", 1, 2);
        db.close();
        //db.insertServiceNameQ(501, "Air Time", 102, 401);
        //db.insertServiceNameQ(502, "Internet Services", 102, 401);*/
        user = new QUser(this);
        user.getCategories();
        // Bundle b = getIntent().getExtras();
        //Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);
        //Fragment f = Fragment.instantiate(this, fragmentClass.getName());
        //f.setArguments(b);
        //getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
        //Log.e("about", "to call function");
        //user.startUpdatesDaemon();
        Bundle b = getIntent().getExtras();
        Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);
        /*Fragment f = Fragment.instantiate(this, fragmentClass.getName());
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();*/
        Intent i = new Intent(SingleFragmentActivity.this, SelectableTreeFragment.class);
        i.putExtra("message", b);
        startActivity(i);
    }
}

class vQ
{
    private String cellNumb;
    private SelectableItemHolder info;
    private String qLocation;
    private int qlength;
    private int ttdqMin;
    private int ttdqSec;
    public void setInfo(SelectableItemHolder info1){
        info=info1;
    }
    public SelectableItemHolder getInfo(){
        return info;
    }
    public vQ()
    {
    }
    public void calculateStats()
    {
    }
    public String getCellNumb(){
        return cellNumb;
    }
    public void setFromJSON(JSONObject vq)
    {
        Log.e("cellNumber",cellNumb);
        info.dialog(vq.toString());
    }
    public void setCellNumb(String cellNumb1){
        cellNumb = cellNumb1;
    }
}