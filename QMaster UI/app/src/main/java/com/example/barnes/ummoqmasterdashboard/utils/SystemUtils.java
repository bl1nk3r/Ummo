package com.example.barnes.ummoqmasterdashboard.utils;

import android.content.res.Resources;

/**
 * Created by barnes on 10/26/15.
 */
public class SystemUtils
{
    public static int getScreenOrientation() {
        return Resources.getSystem().getConfiguration().orientation;
    }
}
