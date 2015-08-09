package com.ummo.blinker.ummoqmaster.log_reg;

/**
 * Created by blinker on 7/31/15 for Ummo.
 */
public class QM {
    String fname, uname, service, location, password;

    public QM(String fname, String uname, String service, String location, String password) {
        this.fname    = fname;
        this.uname    = uname;
        this.service  = service;
        this.location = location;
        this.password = password;

    }

    public QM(String fname, String password) {
        this.fname = fname;
        this.password = password;
    }

}
