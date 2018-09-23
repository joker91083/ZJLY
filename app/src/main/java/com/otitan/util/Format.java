package com.otitan.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {

    public static String timeFormat(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        return format.format(new Date());
    }


}
