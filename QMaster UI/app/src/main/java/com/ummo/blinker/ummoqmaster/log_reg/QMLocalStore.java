package com.ummo.blinker.ummoqmaster.log_reg;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by blinker on 7/31/15 for Ummo.
 */
public class QMLocalStore {

    public  static final String SP_FNAME = "qMDetails";
    SharedPreferences qMLocalDatabase;

    public QMLocalStore(Context context) {
        qMLocalDatabase = context.getSharedPreferences(SP_FNAME, 0);
    }

    public void  storeQMData (QM QM) {
        SharedPreferences.Editor spEditor = qMLocalDatabase.edit();
        spEditor.putString("fname", QM.fname);
        spEditor.putString("uname", QM.uname);
        spEditor.putString("service", QM.service);
        spEditor.putString("location", QM.location);
        spEditor.putString("password", QM.password);
        spEditor.commit();
    }

    public QM getLoggedInQM () {
        String fname = qMLocalDatabase.getString("fname", "");
        String uname = qMLocalDatabase.getString("uname", "");
        String service = qMLocalDatabase.getString("service", "");
        String location = qMLocalDatabase.getString("location", "");
        String password = qMLocalDatabase.getString("password", "");

        QM storedQM = new QM(fname, uname, service, location, password);

        return storedQM;
    }

    public void setQMLoggedIn(boolean loggedIn) {
        SharedPreferences.Editor spEditor = qMLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getQMLoggedIn () {
        return qMLocalDatabase.getBoolean("loggedIn", false);
    }

    public void clearQMData() {
        SharedPreferences.Editor spEditor = qMLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
