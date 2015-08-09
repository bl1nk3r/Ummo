package com.example.barnes.ummo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barnes on 7/26/15.
 */
public class Db
{
    private SQLiteDatabase db;
    private final Context context;
    private final DbHelper dbhelper;
    public Db(Context c)
    {
        context = c;
        dbhelper = new DbHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        //dbhelper.onCreate(db);
    }
    public void close()
    {
        db.close();
    }
    public void open() throws SQLiteException
    {
        try
        {
            db = dbhelper.getWritableDatabase();
            //db = dbhelper.getReadableDatabase();
        }
        catch(SQLiteException ex)
        {
            db = dbhelper.getReadableDatabase();
        }
    }

    public long insertQ(String qname, int qtabPositon, String qposition)
    {
        long insert = 0;
        try
        {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put(Constants.Q_NAME, qname);
            newTaskValue.put(Constants.Q_TAB_POSITION, qtabPositon);
            newTaskValue.put(Constants.Q_POSITION, qposition);
            insert =  db.insert(Constants.TABLE_NAME_Q, null, newTaskValue);
            Toast t = Toast.makeText(context, qname, Toast.LENGTH_LONG);
            t.show();
        }
        catch(SQLiteException ex)
        {
            Toast.makeText(context, "Not Saved", Toast.LENGTH_LONG).show();
            insert = -1;
        }
        return insert;
    }

    public Cursor getQTabPosition()
    {
        //Cursor c = db.query(Constants.TABLE_NAME_1, null, null, null, null, null, null);
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " ORDER BY " + Constants.Q_TAB_POSITION + " DESC";
        db = dbhelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

    public List<String> getAllQTabPosition()
    {
        List<String> labels = new ArrayList<String>();
        try
        {
            // Select All Query
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " ORDER BY " + Constants.Q_TAB_POSITION + " DESC";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            //Cursor c = db.query(Constants.TABLE_NAME_2, null, null, null, null, null, null);
            // looping through all rows and adding to list
            if (c.moveToFirst())
            {
                do
                {
                    labels.add(c.getString(1));
                }
                while (c.moveToNext());
            }
            // closing connection
            c.close();
            //db.close();
        }
        catch(SQLiteException ex)
        {
            //return -1;
        }
        // returning lables
        return labels;
    }

    public List<String> getAllQId()
    {
        List<String> labels = new ArrayList<String>();
        try
        {
            // Select All Query
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " ORDER BY " + Constants.Q_TAB_POSITION + " DESC";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            //Cursor c = db.query(Constants.TABLE_NAME_2, null, null, null, null, null, null);
            // looping through all rows and adding to list
            if (c.moveToFirst())
            {
                do
                {
                    labels.add(c.getString(0));
                }
                while (c.moveToNext());
            }
            // closing connection
            c.close();
            //db.close();
        }
        catch(SQLiteException ex)
        {
            //return -1;
        }
        // returning lables
        return labels;
    }


    public List<String> getQName(String name)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " WHERE " + Constants.Q_NAME + " = '" + name +"'";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(1));
                }
                while(c.moveToNext());
            }
            else
            {

            }
            c.close();
        }
        catch(SQLiteException ex)
        {
        }
        return qName;
    }


    public List<String> getAllQs()
    {
        List<String> labels = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " ORDER BY " + Constants.Q_TAB_POSITION;
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (c.moveToFirst())
            {
                do
                {
                    labels.add(c.getString(1));
                }
                while (c.moveToNext());
            }
            c.close();
        }
        catch(SQLiteException ex)
        {
        }
        return labels;
    }


    public boolean deleteContact(String num)
    {
        return db.delete(Constants.TABLE_NAME_Q,Constants.Q_ID + "=" + num,null) > 0;
    }

    public boolean updateQPosition(String qid, String qname, String qtabPosition, String qposition)
    {
        ContentValues value = new ContentValues();
        value.put(Constants.Q_NAME, qname);
        value.put(Constants.Q_TAB_POSITION, qtabPosition);
        value.put(Constants.Q_POSITION, qposition);
        return db.update(Constants.TABLE_NAME_Q,value,Constants.Q_ID + "=" + qid,null) > 0;
    }
}
