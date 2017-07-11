package com.example.sasiboy.ocpda.model;


import android.content.Context;
import android.content.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static SharedPreferenceManager mInstance  = null;
    private static Context ctx;

    private static final String SHARED_PREFERENCE_NAME = "ocpda";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_NAME = "keyusername";
    private static final String KEY_USER_STATUS = "keyuseremail";



    private SharedPreferenceManager(Context context)
    {
        ctx = context;
    }

    public static synchronized SharedPreferenceManager getmInstance(Context context){
        if (mInstance==null)
            mInstance = new SharedPreferenceManager(context);
        return mInstance;
    }

    public boolean userLogin(User user){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,user.getId());
        editor.putString(KEY_USER_NAME,user.getUsername());
        editor.putString(KEY_USER_STATUS,user.getStatus());
        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USER_NAME,null) != null)
            return true;
        return false;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID,0),
                sharedPreferences.getString(KEY_USER_NAME,null),
                sharedPreferences.getString(KEY_USER_STATUS,null)
        );
    }

    public String getStatus(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        String status = sharedPreferences.getString(KEY_USER_STATUS,null);
        return  status;
    }

    public int getUserId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        int a = sharedPreferences.getInt(KEY_USER_ID,0);
        return  a;
    }


    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}

