package com.example.barnes.ummo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by barnes on 7/26/15.
 */
public class DbHelper extends SQLiteOpenHelper
{
    private static final String CREATE_TABLE = "create table " +
            Constants.TABLE_NAME_Q + " (" +
            Constants.Q_ID + " integer primary key autoincrement, " +
            Constants.Q_NAME + " text not null, " +
            Constants.Q_TAB_POSITION + " integer not null, " +
            Constants.Q_POSITION + " text not null);";

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
            db.execSQL(CREATE_TABLE);
        }
        catch(SQLiteException ex)
        {
            Log.v("Create table exception", ex.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("drop table if exists " + Constants.TABLE_NAME_Q);
        onCreate(db);
    }
}
