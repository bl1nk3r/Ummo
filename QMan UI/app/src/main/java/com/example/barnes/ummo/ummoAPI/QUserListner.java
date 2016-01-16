package com.example.barnes.ummo.ummoAPI;

/**
 * Created by barnes on 11/1/15.
 */
public interface QUserListner
{
    public void userRegistered(String string);
    public void qJoined(String string);
    public void qLeft(String string);
    public void updated(String string);
    public void categoriesReady(String string);
    public void allQsReady(String string);
    public  void qReady(String string);
    public void gotJoinedQs(String string);

    //Errors
    public void joinedQsError(String err);
    public void userRegistrationError(String err);
    public void qJoinedError(String err);
    public void qLeftError(String err);
    public void updateError(String err);
    public void categoriesError(String err);
    public void allQError(String err);
    public void qError(String err);
}