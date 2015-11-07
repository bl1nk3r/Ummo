package com.example.sihle.networklib;

/**
 * Created by sihle on 9/27/15.
 */

/*
exports.createQ=craeteQ;
exports.register=register;
exports.destroyQ=destroyQ;
exports.dQUser=dQUser;
exports.moveUser=moveUser;
exports.getFeedbacks=getFeedbacks;
exports.getMyques = getMyques;
exports.destroyAllQs=destroyAllQs;
exports.update=update;
 */
public interface QMasterListener {
    //Success Functions
    public void qCreated(String string);
    public void registered(String string);
    public void qDestroyed(String string);
    public void userDQd(String string);
    public void userMoved(String string);
    public void feedBackRecieved(String string);
    public void myQRecieved(String string);
    public void updatesRecieved(String string);

    //Error Handling Functions

    public void createQError(String string);
    public void registrationError(String string);
    public void onQDestroyError(String sting);
    public void onUserDQError(String string);
    public void onUserMoveError(String string);
    public void onFeedBackError(String string);
    public void onUpdtaesError(String string);


}
