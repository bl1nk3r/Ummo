package com.example.barnes.ummo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by barnes on 8/6/15.
 */
public class DbHelper extends SQLiteOpenHelper
{
    private static final String CREATE_TABLE_SERVICE_TYPE = "CREATE TABLE " +
            Constants.TABLE_Q_SERVICETYPE + " (" +
            Constants.Q_SERVICETYPEID + " INTEGER PRIMARY KEY, " +
            Constants.Q_SERVICETYPENAME + " TEXT NOT NULL)";

    private static final String CREATE_TABLE_SERVICE_PROVIDER = "CREATE TABLE " +
            Constants.TABLE_Q_SERVICEPROVIDER + " (" +
            Constants.Q_SERVICEPROVIDERID + " INTEGER PRIMARY KEY, " +
            Constants.Q_SERVICEPROVIDERNAME + " TEXT NOT NULL, " +
            Constants.Q_SERVICETYPEID +" INTEGER NOT NULL, " +
            "FOREIGN KEY(" + Constants.Q_SERVICETYPEID + ") REFERENCES " + Constants.TABLE_Q_SERVICETYPE + "(" + Constants.Q_SERVICETYPEID + "))";

    /*private static final String CREATE_TABLE_SERVICE_PROVIDER = "CREATE TABLE " +
            Constants.TABLE_Q_SERVICEPROVIDER + " (" +
            Constants.Q_SERVICEPROVIDERID + " INTEGER PRIMARY KEY, " +
            Constants.Q_SERVICEPROVIDERNAME + " TEXT NOT NULL, " +
            Constants.Q_SERVICETYPEID +" INTEGER FOREINGKEY REFERENCES " + Constants.TABLE_Q_SERVICETYPE + "(" + Constants.Q_SERVICETYPEID + "))";*/

    private static final String CREATE_TABLE_SERVICE_NAME = "CREATE TABLE " +
            Constants.TABLE_Q_SERVICE + " (" +
            Constants.Q_SERVICENAMEID + " INTEGER PRIMARY KEY, " +
            Constants.Q_SERVICENAME + " TEXT NOT NULL, " +
            Constants.Q_SERVICETYPEID + " INTEGER NOT NULL, " +
            Constants.Q_SERVICEPROVIDERID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + Constants.Q_SERVICETYPEID + ") REFERENCES " + Constants.TABLE_Q_SERVICETYPE + "(" + Constants.Q_SERVICETYPEID + "), "+
            "FOREIGN KEY(" + Constants.Q_SERVICEPROVIDERID + ") REFERENCES " + Constants.TABLE_Q_SERVICEPROVIDER + "(" + Constants.Q_SERVICEPROVIDERID + "))";

    private static final String CREATE_TABLE_QTABS = "CREATE TABLE " +
            Constants.TABLE_NAME_Q + " (" +
            Constants.Q_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Constants.Q_SERVICENAMEID + " INTEGER FOREIGNKEY REFERENCES " + Constants.TABLE_Q_SERVICE + "(" + Constants.Q_SERVICENAMEID + "), " +
            Constants.Q_NAME + " TEXT NOT NULL, " +
            Constants.Q_TAB_POSITION + " INTEGER NOT NULL, " +
            Constants.Q_POSITION + " TEXT NOT NULL)";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.v("MyDBhelper onCreate", "Creating all the tables");
        try
        {
            db.execSQL(CREATE_TABLE_SERVICE_TYPE);
            db.execSQL(CREATE_TABLE_SERVICE_PROVIDER);
            db.execSQL(CREATE_TABLE_SERVICE_NAME);
            db.execSQL(CREATE_TABLE_QTABS);
        }
        catch(SQLiteException ex)
        {
            Log.v("Create table exception", ex.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("drop table if exists " + Constants.TABLE_Q_SERVICETYPE);
        db.execSQL("drop table if exists " + Constants.TABLE_Q_SERVICEPROVIDER);
        db.execSQL("drop table if exists " + Constants.TABLE_Q_SERVICE);
        db.execSQL("drop table if exists " + Constants.TABLE_NAME_Q);
        onCreate(db);
    }
}

