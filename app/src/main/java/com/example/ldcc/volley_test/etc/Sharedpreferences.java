package com.example.ldcc.volley_test.etc;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ldcc.volley_test.model.ObjectCPU;
import com.example.ldcc.volley_test.model.ObjectKeyValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Sharedpreferences {

    // 값 불러오기
    public static String getStringPreferences(Context mContext, String key){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getString(key, "");
    }
    // 값 불러오기
    public static boolean getBooleanPreferences(Context mContext, String key){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }
    // 값 불러오기
    public static int getIntegerPreferences(Context mContext, String key){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }
    // 값 불러오기 ArrayList
    public static  ArrayList<ObjectKeyValue> getArrayListPreferences(Context mContext, String key) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(key, null);
        Type type = new TypeToken<ArrayList<ObjectKeyValue>>() {}.getType();
        return gson.fromJson(json, type);
    }
    // 값 불러오기 ArrayListCPU
    public static  ArrayList<ObjectCPU> getArrayListCPUPreferences(Context mContext, String key) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(key, null);
        Type type = new TypeToken<ArrayList<ObjectCPU>>() {}.getType();
        return gson.fromJson(json, type);
    }
    // 값 불러오기 Map
    public static  Map<String,String> getMapPreferences(Context mContext, String key) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        Map<String,String> outputMap = new HashMap<>();
        try{
            if (pref != null){
                String jsonString = pref.getString(key, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String k = keysItr.next();
                    String v = (String) jsonObject.get(k);
                    outputMap.put(k,v);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }
    // 값 저장하기
    public static void savePreferences(Context mContext, String key, String value){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // 값 저장하기
    public static void savePreferences(Context mContext, String key, boolean value){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveArrayListPreferences(Context mContext, String key, ArrayList<ObjectKeyValue> values) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        String json = gson.toJson(values);

        editor.putString(key, json);
        editor.commit();
    }

    public static void saveArrayListCPUPreferences(Context mContext, String key, ArrayList<ObjectCPU> values) {
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();

        String json = gson.toJson(values);

        editor.putString(key, json);
        editor.commit();
    }

    public static void saveMapPreferences(Context mContext, String key, Map<String,String> inputMap){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        JSONObject jsonObject = new JSONObject(inputMap);
        String jsonString = jsonObject.toString();
        editor.remove(key).commit();
        editor.putString(key, jsonString);
        editor.commit();
    }


    // 값 삭제하기
    public static void removePreferences(Context mContext, String key){
        SharedPreferences pref = mContext.getSharedPreferences("pref", mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }
}
