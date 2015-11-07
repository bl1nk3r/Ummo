package com.example.barnes.ummo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barnes on 8/6/15.
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
        db = dbhelper.getWritableDatabase();
        try
        {
            db = dbhelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
        catch(SQLiteException ex)
        {
            db = dbhelper.getReadableDatabase();
        }
    }

    public long insertServiceTypeQ(int serviceTypeId, String qtypename)
    {
        long insert;
        try
        {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put(Constants.Q_SERVICETYPEID, serviceTypeId);
            newTaskValue.put(Constants.Q_SERVICETYPENAME, qtypename);
            insert =  db.insert(Constants.TABLE_Q_SERVICETYPE, null, newTaskValue);
        }
        catch(SQLiteException ex)
        {
            Toast.makeText(context, "Not Saved", Toast.LENGTH_LONG).show();
            insert = -1;
        }
        return insert;
    }

    public long insertServiceProviderQ(int serviceProviderId, String qprovidername, int qserviceTypeId)
    {
        long insert;
        try
        {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put(Constants.Q_SERVICEPROVIDERID, serviceProviderId);
            newTaskValue.put(Constants.Q_SERVICEPROVIDERNAME, qprovidername);
            newTaskValue.put(Constants.Q_SERVICETYPEID, qserviceTypeId);
            insert =  db.insert(Constants.TABLE_Q_SERVICEPROVIDER, null, newTaskValue);
        }
        catch(SQLiteException ex)
        {
            Toast.makeText(context, "Not Saved", Toast.LENGTH_LONG).show();
            insert = -1;
        }
        return insert;
    }

    public long insertServiceNameQ(int serviceId, String qservicename, int qserviceTypeId, int qserviceProviderId)
    {
        long insert;
        try
        {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put(Constants.Q_SERVICENAMEID, serviceId);
            newTaskValue.put(Constants.Q_SERVICENAME, qservicename);
            newTaskValue.put(Constants.Q_SERVICETYPEID, qserviceTypeId);
            newTaskValue.put(Constants.Q_SERVICEPROVIDERID, qserviceProviderId);
            insert =  db.insert(Constants.TABLE_Q_SERVICE, null, newTaskValue);
        }
        catch(SQLiteException ex)
        {
            Toast.makeText(context, "Not Saved", Toast.LENGTH_LONG).show();
            insert = -1;
        }
        return insert;
    }

    public long insertQ(int qserviceid, String qname, int qtabPositon, String qposition)
    {
        long insert;
        try
        {
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put(Constants.Q_SERVICENAMEID, qserviceid);
            newTaskValue.put(Constants.Q_NAME, qname);
            newTaskValue.put(Constants.Q_TAB_POSITION, qtabPositon);
            newTaskValue.put(Constants.Q_POSITION, qposition);
            insert =  db.insert(Constants.TABLE_NAME_Q, null, newTaskValue);
        }
        catch(SQLiteException ex)
        {
            Toast.makeText(context, "Not Saved", Toast.LENGTH_LONG).show();
            insert = -1;
        }
        return insert;
    }

    public boolean deleteQ(String num)
    {
        return db.delete(Constants.TABLE_NAME_Q,Constants.Q_ID + "=" + num,null) > 0;
    }

    public List<String> getJoinedQ_id(String qserviceName)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " WHERE " + Constants.Q_NAME + " = '" + qserviceName +"'";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(0));
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

    public List<String> getQService_id(String qserviceName)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_Q_SERVICE + " WHERE " + Constants.Q_SERVICENAME + " = '" + qserviceName +"'";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(0));
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

    public List<String> getQs_Joined()
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q;
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(3));
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

    public List<String> getQsJoined()
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q;
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(0));
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

    public List<String> getQNameJoined()
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q;
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(2));
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

    public List<String> getQPositionJoined(String qname)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " WHERE " + Constants.Q_NAME + " = '" + qname +"'";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(4));
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

    public List<String> getQNameJoinedFromTabPosition(int tabPosition)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q + " WHERE " + Constants.Q_TAB_POSITION + " = '" + tabPosition + "'";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(2));
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

    public List<String> getQPosition()
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q ;
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(4));
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

    public List<String> getQServiceProviderName(int id)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_Q_SERVICEPROVIDER + " WHERE " + Constants.Q_SERVICETYPEID + " = '" + id +"'";
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

    public List<String> getQServicePid(String pname)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_Q_SERVICEPROVIDER + " WHERE " + Constants.Q_SERVICEPROVIDERNAME + " = '" + pname +"'";
            db = dbhelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);
            if (c.moveToFirst())
            {
                do
                {
                    qName.add(c.getString(0));
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

    public List<String> getQServiceName(int id)
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_Q_SERVICE + " WHERE " + Constants.Q_SERVICEPROVIDERID + " = '" + id +"'";
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

    public List<String> getAllQServiceTypesName()
    {
        List<String> labels = new ArrayList<String>();
        try
        {
            // Select All Query
            String selectQuery = "SELECT * FROM " + Constants.TABLE_Q_SERVICETYPE + " ORDER BY " + Constants.Q_SERVICETYPEID + " DESC";
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

    public List<String> getQServiceTypeid(String tname)
    {
        List<String> labels = new ArrayList<String>();
        try
        {
            // Select All Query
            String selectQuery = "SELECT * FROM " + Constants.TABLE_Q_SERVICETYPE + " WHERE " + Constants.Q_SERVICETYPENAME + " = '" + tname + "';";
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

    public List<String> getJoinedQName()
    {
        List<String> qName = new ArrayList<String>();
        try
        {
            String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME_Q;
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
                    labels.add(c.getString(2));
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


    public boolean updateQPosition(String qid, String qname, String qtabPosition, String qposition)
    {
        ContentValues value = new ContentValues();
        value.put(Constants.Q_NAME, qname);
        value.put(Constants.Q_TAB_POSITION, qtabPosition);
        value.put(Constants.Q_POSITION, qposition);
        return db.update(Constants.TABLE_NAME_Q,value, Constants.Q_ID + "=" + qid,null) > 0;
    }
}
