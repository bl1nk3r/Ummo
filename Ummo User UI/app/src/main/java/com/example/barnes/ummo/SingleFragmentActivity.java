package com.example.barnes.ummo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.barnes.ummo.db.Db;

/**
 * Created by barnes on 8/7/15.
 */
public class SingleFragmentActivity extends AppCompatActivity
{
    Db db;
    public final static String FRAGMENT_PARAM = "fragment";

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_single_fragment);

        db = new Db(this);
        db.open();
        /*db.insertServiceTypeQ(100, "Financial Services");
        db.insertServiceProviderQ(202, "SwaziBank", 100);
        db.insertServiceProviderQ(200, "FNB", 100);
        db.insertServiceNameQ(300, "Deposit", 100, 200);
        db.insertServiceNameQ(302, "Withdrawals", 100, 200);
        //db.insertServiceNameQ(302, "Foreign Exchange ", 100, 202);*/

        //db.insertServiceTypeQ(101, "Government Services");
        //db.insertServiceProviderQ(201, "SRA", 101);
        //db.insertServiceNameQ(304, "Tax Payment", 101, 201);
        //db.insertServiceNameQ(305,"Trading licence",101,201);

        db.insertServiceTypeQ(102, "MTN Services");
        db.insertServiceProviderQ(401, "Ezulwini MTN", 102);
        db.insertServiceNameQ(500, "SIM Swap", 102, 401);
        db.insertServiceNameQ(501, "Air Time", 102, 401);
        db.insertServiceNameQ(502, "Internet Services", 102, 401);

        Bundle b = getIntent().getExtras();
        Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);
        if (bundle == null)
        {
            Fragment f = Fragment.instantiate(this, fragmentClass.getName());
            f.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
        }
    }
}
