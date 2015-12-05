package com.example.barnes.ummo;

/**
 * Created by blinker on 12/3/15.
 */
public class ummoTester {
    String alias;
    int cellNum, testID;

    public ummoTester (String al, int cell, int id){
        this.alias = al;
        this.cellNum = cell;
        this.testID = id;
    }

    public ummoTester(String al, int cell) {
        this.alias = al;
        this.cellNum = cell;
        this.testID = -1;
    }
}
