package com.release.sihle.umoclasses;

import java.util.List;

/**
 * Created by sihle on 2015/07/08.
 */
public class UmmoQ {
    private String qId; //The QID
    private int length; // The Current Length of the Q
    private int myPosition; //My Possition on the Q
    private int TLTDQ; //Time left for me to reach the front
    private int ATTDQ; //Avarage time to dQ one user on theis q;
    private String name;
    private List<UmmoRequirements> requirements;

    public void setName(String qname){
        name = qname;
    }

    public UmmoQ(){

    }

    public String getName(){
        return name;
    }

    public void setId(String _id){
        qId=_id;
    }

    public String getId(){
        return qId;
    }

    public void setMyPossition(int p){
        myPosition= p;

    }

    public int getMyposition(){
        return myPosition;
    }

    public void setLength(int l){
        length = l;
    }

    public int getLength(){
        return length;
    }

}


class UmmoRequirements{
    String required;
    public UmmoRequirements(){

    }
}