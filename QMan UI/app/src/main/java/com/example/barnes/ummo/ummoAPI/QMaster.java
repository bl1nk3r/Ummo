package com.example.barnes.ummo.ummoAPI;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.barnes.ummo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by barnes on 11/1/15.
 */
public class QMaster
{
    private Activity callingActivity;
    private String uCellNumber;
    private boolean registered=false;
    private String uName;


    public void register(final String name,final String cell) {
        try {

            String urlString = callingActivity.getString(R.string.SERVER_URL)+"qMaster/register";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("uid",cell);
            formPoster.add("fullName",name);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        ((QUserListner) callingActivity).userRegistered(objString);
                        //This would mean the registration was compleate
                        //
                        //JSONObject obj = new JSONObject(objString);
                        //Set the Shared Preferences for User Name and CellNumber
                        SharedPreferences sp = PreferenceManager
                                .getDefaultSharedPreferences(callingActivity);
                        sp.edit().putString(callingActivity.getString(R.string.PREF_USER_NAME), name).apply();
                        sp.edit().putString(callingActivity.getString(R.string.PREF_USER_CELLNUMBER), cell).apply();
                        sp.edit().putBoolean(callingActivity.getString(R.string.PREF_USER_REGISTERED), true).apply();

                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener) callingActivity).registered(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener)callingActivity).registrationError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception", me.toString());
        }




    }

    public boolean isRegistered() {
        return registered;
    }


    public void createQ(String frame,String qname,String qTag) {
        try {
            String urlString = callingActivity.getString(R.string.SERVER_URL)+"qMaster/craeteQ";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("cellnum",uCellNumber);
            formPoster.add("frame",frame);
            formPoster.add("qName",qname);
            formPoster.add("tag",qTag);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener) callingActivity).qCreated(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener)callingActivity).createQError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception", me.toString());
        }




    }

    public void destroyQ() {
        try {
            String urlString = callingActivity.getString(R.string.SERVER_URL)+"qMaster/destroyQ";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("cellnum",uCellNumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener) callingActivity).qDestroyed(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener)callingActivity).onQDestroyError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception", me.toString());
        }




    }

    public void getQUpdates() {
        try {
            String urlString = callingActivity.getString(R.string.SERVER_URL)+"qMaster/updates";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("cellnum",uCellNumber);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener) callingActivity).updatesRecieved(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener)callingActivity).onUpdtaesError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception", me.toString());
        }




    }


    public void dQUser(String ucellnum) {
        try {
            String urlString = callingActivity.getString(R.string.SERVER_URL)+"qMaster/deQUser";
            final FormPoster formPoster = new FormPoster(new URL(urlString));
            formPoster.add("mcellnum",uCellNumber);
            formPoster.add("ucellnum",ucellnum);
            formPoster.add("data", "data");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = formPoster.post();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                        final StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
                        String line;
                        while((line = rd.readLine()) != null) {
                            response.append(line);
                            response.append('\r');
                        }
                        rd.close();

                        final String objString = response.toString();
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener) callingActivity).userDQd(objString);
                            }
                        });


                    }

                    catch (final IOException ioe){
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((QMasterListener)callingActivity).onUserDQError(ioe.toString());
                            }
                        });
                    }

                }
            });

            thread.start();


        }

        catch (MalformedURLException me){
            Log.e("NetWork Exception", me.toString());
        }




    }




    QMaster(Activity activity){
        setCallingActivity(activity);
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(callingActivity);
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

    public void setCallingActivity(Activity activity) {
        callingActivity = activity;
    }


    public void setName() {

    }



    public void setCellNumber() {

    }

    public String getName() {
        return uName;
    }

    public String getCellNumb() {
        return uCellNumber;
    }
}