package com.example.barnes.ummo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.example.barnes.ummo.db.Db;

/**
 * Created by barnes on 8/7/15.
 */
public class SingleFragmentActivity extends Activity
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
        db.insertServiceTypeQ(101, "Financial Services");
        db.insertServiceProviderQ(102, "FNB", 101);
        db.insertServiceProviderQ(103, "SwaziBank", 101);
        db.insertServiceNameQ(104, "Deposit", 101, 102);
        db.insertServiceNameQ(105, "Withdrawals", 101, 103);
        db.insertServiceNameQ(106, "Foreign Exchange", 101, 102);

        db.insertServiceTypeQ(107, "Government Services");
        db.insertServiceProviderQ(108, "Swaziland Revenue Authority", 107);
        db.insertServiceProviderQ(109, "Mbabane Govt Hospital", 107);
        db.insertServiceNameQ(200, "Tax Payment", 107, 108);
        db.insertServiceNameQ(201,"Trading licence",107,108);
        db.insertServiceNameQ(202,"Eye Check",107,109);
        db.insertServiceNameQ(203,"Counselling",107,109);

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
        db.close();

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