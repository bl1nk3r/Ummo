package com.example.barnes.ummo.ummoAPI;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.barnes.ummo.R;
import com.example.barnes.ummo.fragment.Qfragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sihle on 12/19/15.
 */
public class JoinedQ {
    private String qName;
    private String qId;
    private String ttdq;
    private String tltdq;
    private String cellnumb;
    private String myPos;
    private int qLength;
    private String myAlphanumCode;
    private Qfragment fragment;

    public JoinedQ(){

    }

    public void deleateQ(String q_id,Activity activity){
        PreferenceManager.getDefaultSharedPreferences(activity).edit().remove(q_id).commit();
    }

    public void readByQId(String q_id,Activity activity){
        String qJSONString = PreferenceManager.getDefaultSharedPreferences(activity).getString(q_id,"Q_ID");
        if(qJSONString!="Q_ID"){
            try{
                JSONObject object = new JSONObject(qJSONString);
                qName=object.getString("qName");
                qId=object.getString("qId");
                //ttdq=object.getString("ttdq");
                //tltdq=object.getString("tltdq");
                cellnumb=object.getString("cellnumb");
                myPos=object.getString("myPos");
                Log.e("READPOS",myPos);
                qLength=object.getInt("qLength");
                myAlphanumCode=object.getString("myAlphanumCode");
            }

            catch (JSONException jse){
                Log.e("READBYQID",jse.toString());
                Log.e("STRING",qJSONString);
            }
        }

        else {
            Log.e("READQBYID",qJSONString);
        }
    }

    public void save(Activity activity,QUser user){
        try{
            JSONObject object = new JSONObject();
            object.put("qName",qName);
            object.put("qId",qId);
           // object.put("ttdq",ttdq);
           // object.put("tltdq",tltdq);
            object.put("cellnumb",cellnumb);
            object.put("myPos",myPos);
            object.put("qLength",qLength);
            object.put("myAlphanumCode", myAlphanumCode);
            Log.e("SAVING",object.toString());
            if(user.isQJoined(qId)){
                PreferenceManager.getDefaultSharedPreferences(activity).edit().putString(qId,object.toString()).commit();
            }

        }
        catch (JSONException jse){
            Log.e("SAVINGQ",jse.toString());
        }
    }

    public JoinedQ(String name,String id, String ttq, String ltq, String myp,int lenth,String alp,Qfragment f){
        qName=name;
        qId=id;
        ttdq=ttq;
        tltdq=ltq;
        myPos=myp;
        qLength=lenth;
        myAlphanumCode=alp;
        fragment=f;
    }

    public String getqName(){
        return qName;
    }

    public String getqId(){
        return qId;
    }

    public String getTtdq(){
        return ttdq;
    }

    public String getTltdq(){
        return tltdq;
    }

    public String getMyAlphanumCode(){
        return myAlphanumCode;
    }

    public String getMyPos(){
        return myPos;
    }

    public Qfragment getFragment(){
        return fragment;
    }

    public void setName(String name){
        qName = name;
    }

    public void setqId(String id){
        qId = id;

    }

    public void setTtdq(String tdq){
        ttdq = tdq;
    }

    public void setTltdq(String _tltdq){
        tltdq=_tltdq;
    }

    public void setFragment(Qfragment _fragment){
        fragment=_fragment;
    }

    public JoinedQ(String qid,Activity activity){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        String q_data = sp.getString(qid, "Q_DATA");
        String cell = sp.getString(activity.getString(R.string.PREF_USER_CELLNUMBER),"CELL");
        if (q_data!="Q_DATA"){
            JSONObject object;
            try {
                object = new JSONObject(q_data);
                setFromJSON(object,cell);
            }

            catch (JSONException jse){
                Log.e("JSON_ER",jse.toString());
            }

        }
    }



    public JoinedQ(JSONObject object,String cell){
        setFromJSON(object,cell);
    }




    public void setFromJSON(JSONObject object,String cell){
        try {
            cellnumb = cell;
            qName=object.getJSONObject("managedQ").getString("qName");
            qId=object.getString("qId");
            myPos=String.valueOf(object.getJSONObject("managedQ").getJSONObject("qErs").getJSONObject(cellnumb).getInt("position"));
            Log.e("JQPOSSIT",myPos);
            qLength = object.getJSONObject("managedQ").getJSONObject("qErs").length();
            myAlphanumCode=object.getJSONObject("managedQ").getJSONObject("qErs").getJSONObject(cellnumb).getString("numCode");
        }

        catch (JSONException jse){
            Log.e("JOINEDQ",jse.toString());
        }
    }

}


/*This class is instantiated when new Q data from the cloud is got.
The q data is saved as a shared preferance, with the key being the q_id
The q_id is also added to the array of joined qs that are saved as a JSONArray string on the sharedPreferences.
Each time the list of joined qs is got from the internet, the The list of, all q_data is deleated, new q data is created and saved
from the internet data, and new joined q list is created and saved.
 */