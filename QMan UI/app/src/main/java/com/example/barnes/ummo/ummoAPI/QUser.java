package com.example.barnes.ummo.ummoAPI;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.barnes.ummo.JSONParser;
import com.example.barnes.ummo.R;
import com.example.barnes.ummo.db.Db;

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
import java.util.HashMap;
import java.util.List;

/**
 * Created by barnes on 9/27/15.
 */
public class QUser
{
    JSONArray qInfo = null;
    private Activity callingActivity;
    private String uCellNumber="76583262";
    private boolean registered = false;
    private String uName;
    private boolean mBound=false;
    private UmmoDaemon daemon;
    private UmmoDaemon ummoDaemon;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> qUserInfoArrayList;
    List<QUserInfo> qUser_info;
    Db db;

    private static final String Q_TAG = "availQs";
    private static final String Q_CATEGORY_ID = "category_id";
    private static final String Q_CATEGORY_NAME = "qcategoy";
    private static final String Q_SERVICE_PROVIDER_ID = "serviceprovider_id";
    private static final String Q_SERVICE_PROVIDER_NAME = "qservice";
    private static final String Q_SERVICE_NAME_ID = "servicename_id";
    private static final String Q_SERVICE_NAME = "qname";
    private static final String Q_USERCELL = "usercellnumber";

    private ServiceConnection mConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            UmmoDaemon.LocalBinder binder = (UmmoDaemon.LocalBinder) service;
            ummoDaemon = binder.getService();
            mBound = true;
            ummoDaemon.setCalee(callingActivity);
            ummoDaemon.getUpadates(QUser.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public boolean isRegistered() {
        return registered;
    }

    public void setCallingActivity(Activity activity) {
        callingActivity = activity;
    }

    public void register(final String name, final String cellNumber) {
        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL) + "/user/register";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("cellnum", cellNumber);
            formPoster.add("alias", name);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final String uname = name;
                        final String cellphone = cellNumber;
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while ((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        String objString = response.toString();
                        ((QUserListner) callingActivity).userRegistered(objString);
                        //This would mean the registration was compleate
                        //
                        //JSONObject obj = new JSONObject(objString);
                        //Set the Shared Preferences for User Name and CellNumber
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(callingActivity);
                        sp.edit().putString(callingActivity.getString(R.string.PREF_USER_NAME), uname).apply();
                        sp.edit().putString(callingActivity.getString(R.string.PREF_USER_CELLNUMBER), cellphone).apply();
                        sp.edit().putBoolean(callingActivity.getString(R.string.PREF_USER_REGISTERED), true).apply();
                        //Log.d("Response",id);
                        //Toast.makeText(calee,"Sent Information",Toast.LENGTH_LONG).show();
                    } catch (final IOException ioe) {

                        Log.e("IO Exception", ioe.toString());
                        Log.e("IO Exception", ioe.toString());
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).userRegistrationError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        } catch (MalformedURLException me) {
            Log.e("NetWork Exception", me.toString());
        }
    }

    public QUser(Activity activity) {
        setCallingActivity(activity);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(callingActivity);
        registered = sp.getBoolean(callingActivity.getString(R.string.PREF_USER_REGISTERED), false);
        if (registered) {
            uName = sp.getString(callingActivity.getString(R.string.PREF_USER_NAME), "NO NAME");
            uCellNumber = sp.getString(callingActivity.getString(R.string.PREF_USER_CELLNUMBER), "NO NUMBER");
            //   Intent intent = new Intent(Register.this, MainActivity.class);
            //  startActivity(intent);
            //  finish();
            Log.d("prefs", "registered");
        }
    }

    public void getAvailableQs()
    {
        try
        {
            db = new Db(callingActivity);
            db.open();

            String urlString = callingActivity.getString(R.string.SERVER_URL) + "/user/availQs";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            //formPoster.add("uid",uCellNumber);
            formPoster.add("data", "data");

            /*ContentValues values=new ContentValues();
            values.put("","");
            values.put("", "");*/
            HashMap<String, String> params = new HashMap<>();
            //List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.put("", "");
            params.put("", "");
            //params.add(new BasicNameValuePair("", ""));
            JSONObject json = jsonParser.makeHttpRequest(urlString, "GET", params);
            qUser_info = new ArrayList<>();
            HashMap<String, String> map = new HashMap<>();
            try
            {
                qInfo = json.getJSONArray(Q_TAG);
                //looping through All papers
                for (int i = 0; i < qInfo.length(); i++)
                {
                    JSONObject c = qInfo.getJSONObject(i);
                    //Storing each json item in variable
                    String qcategoryid = c.getString(Q_CATEGORY_ID);
                    String qcategoryname = c.getString(Q_CATEGORY_NAME);

                    String qproviderid = c.getString(Q_SERVICE_PROVIDER_ID);
                    String qprovidername = c.getString(Q_SERVICE_PROVIDER_NAME);

                    String qserviceid = c.getString(Q_SERVICE_NAME_ID);
                    String qservicename = c.getString(Q_SERVICE_NAME);

                    String qusercellnumber = c.getString(Q_USERCELL);

                    QUserInfo qui = new QUserInfo( qcategoryid, qcategoryname, qproviderid, qprovidername, qserviceid,qservicename, qusercellnumber);
                    qUser_info.add(qui);

                    int categoryid = Integer.parseInt(qcategoryid);
                    int serviceproviderid = Integer.parseInt(qproviderid);
                    int serviceid = Integer.parseInt(qserviceid);

                    db.insertServiceTypeQ(categoryid, qcategoryname);
                    db.insertServiceProviderQ(serviceproviderid, qprovidername, categoryid);
                    db.insertServiceNameQ(serviceid, qservicename, categoryid, serviceproviderid);

                    //adding each child node to HashMap key => value
                    map.put(Q_CATEGORY_ID, qcategoryid);
                    map.put(Q_CATEGORY_NAME, qcategoryname);

                    map.put(Q_SERVICE_PROVIDER_ID, qproviderid);
                    map.put(Q_SERVICE_PROVIDER_NAME, qprovidername);

                    map.put(Q_SERVICE_NAME_ID, qserviceid);
                    map.put(Q_SERVICE_NAME, qservicename);

                    map.put(Q_USERCELL, qusercellnumber);
                    //adding HashList to ArrayList
                    qUserInfoArrayList.add(map);
                }
                db.close();
                /*if (qUser_info.size() > 0)
                {
                    Collections.sort(qUser_info, new Comparator<QUserInfo>()
                    {
                        @Override
                        public int compare(final QUserInfo object1, final QUserInfo object2)
                        {
                            //return object1.getPaperNum().compareTo(object2.getPaperNum());
                            return 0;//Dumy value
                        }
                    });
                }*/
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while ((line = rd.readLine()) != null)
                        {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).allQsReady(objString);
                            }
                        });


                    } catch (final IOException ioe) {
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).allQError(ioe.toString());
                            }
                        });
                    }

                }
            });
            thread.start();
        } catch (MalformedURLException me)
        {
            Log.e("NetWork Exception", me.toString());
        }
    }

    public void setName() {

    }

    public UmmoDaemon getDaemon() {
        return daemon;
    }

    public void setCellNumber() {

    }

    public String getName() {
        return uName;
    }

    public String getCellNumb() {
        return uCellNumber;
    }

    public void getCategories() {

    }

    public void getProvides() {

    }

    public void updateJoinedQs() {
        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL) + "/user/joinedQs";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid", uCellNumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while ((line = rd.readLine()) != null)
                        {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();
                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).updated(objString);
                            }
                        });
                    } catch (final IOException ioe) {
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).updateError(ioe.toString());
                            }
                        });
                    }
                }
            });
            thread.start();
        } catch (MalformedURLException me) {
            Log.e("NetWork Exception", me.toString());
        }
    }

    public void joinQ(String qCellnumber)
    {
        try
        {
            String urlString = callingActivity.getString(R.string.SERVER_URL) + "/user/joinQ";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid", uCellNumber);
            formPoster.add("qid", qCellnumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while ((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();
                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).qJoined(objString);
                            }
                        });
                    } catch (final IOException ioe) {
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).qJoinedError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        } catch (MalformedURLException me) {
            Log.e("NetWork Exception", me.toString());
        }


    }

    public void leaveQ(String qCellnumber) {

        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL) + "/user/leaveQ";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid", uCellNumber);
            formPoster.add("qid", qCellnumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while ((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).qLeft(objString);
                            }
                        });


                    } catch (final IOException ioe) {
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QUserListner) callingActivity).qLeftError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        } catch (MalformedURLException me) {
            Log.e("NetWork Exception", me.toString());
        }


    }

    public void startUpdatesDaemon() {
        Intent intent = new Intent(callingActivity, UmmoDaemon.class);
        if(callingActivity!=null) {
            callingActivity.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }

    }

    public ServiceConnection getServiceConnection() {
        return new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                // We've bound to LocalService, cast the IBinder and get LocalService instance
                UmmoDaemon.LocalBinder binder = (UmmoDaemon.LocalBinder) service;
                daemon = binder.getService();

                daemon.setCalee((QUser.this).callingActivity);
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                //mBound = false;
            }

        };
    }
}