package com.example.barnes.ummo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.example.barnes.ummo.db.Db;
import com.example.barnes.ummo.ummoAPI.QUser;
import com.example.barnes.ummo.ummoAPI.QUserListner;

/**
 * Created by barnes on 8/7/15.
 */
public class SingleFragmentActivity extends Activity implements QUserListner
{
    Db db;

    private QUser user;
    private String categoriesJSON;
    private String qsJSON;
    private String serviceProvidersJSON;

    public String getCategoriesJSON(){
        return categoriesJSON;
    }

    public String getQsJSON(){
        return qsJSON;
    }

    public String getServiceProvidersJSON(){
        return serviceProvidersJSON;
    }


    public final static String FRAGMENT_PARAM = "fragment";

    //Overriding for QUserListener Begins Here
    //sucess Handlers
    @Override
    public void userRegistered(String string) {

    }

    @Override
    public void qJoined(String string) {

    }

    @Override
    public void qLeft(String string) {

    }

    @Override
    public void updated(String string) {

    }

    @Override
    public void categoriesReady(String string) {
        categoriesJSON = string;
        Bundle b = getIntent().getExtras();
        Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);

        Fragment f = Fragment.instantiate(this, fragmentClass.getName());
        f.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
        //  user.makeNotification();
    }

    @Override
    public void allQsReady(String string) {
        qsJSON = string;
        Log.d("qs",string);
    }

    //Error Handlers
    @Override
    public void userRegistrationError(String err) {

    }

    @Override
    public void qJoinedError(String err) {

    }

    @Override
    public void qLeftError(String err) {

    }

    @Override
    public void updateError(String err) {

    }

    @Override
    public void categoriesError(String err) {

    }

    @Override
    public void allQError(String err) {

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

        Log.e("about", "to call fuction");
        user.startUpdatesDaemon();
    }
}