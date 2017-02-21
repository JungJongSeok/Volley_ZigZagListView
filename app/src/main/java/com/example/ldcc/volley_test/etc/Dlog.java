package com.example.ldcc.volley_test.etc;

import android.util.Log;

public class Dlog {
    public static final boolean DEBUG = true;


    public static void d(String key, String value){
        if(DEBUG){
            Log.d(key,value);
        }
    }

    public static void i(String key, String value){
        if(DEBUG){
            Log.i(key,value);
        }
    }

    public static void v(String key, String value){
        if(DEBUG){
            Log.v(key,value);
        }
    }

    public static void e(String key, String value){
        if(DEBUG){
            Log.e(key,value);
        }
    }
}
