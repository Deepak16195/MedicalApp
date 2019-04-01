package com.rdavepatient.soft.meetdoctor.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefarence {

    private  static SharePrefarence mInstance;
    private static Context mctx;

    private static final String SHARE_PRE_NAME="MyDocter";
    private static final String USER_NAME="UserName";
    private static final String EMAIL="Emails";
    private static final String USERID="UserID";
    private static final String PHONE="Mobile";
    private static final String PASSWORD="password";
    private static final String LOGOUTVALUE = "log";
    private static final String AREANAME = "Area_name";
    private static final String AREACODE = "Area_code";
    public SharePrefarence(Context context){
        mctx=context;
    }

    public static SharePrefarence getmInstance(Context context){
        if(mInstance==null){
            mInstance=new SharePrefarence(context);
        }
        return mInstance;
    }

    public boolean userid(int id,String userName,String email,String Mobile){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USERID,id);
        editor.putString(USER_NAME,userName);
        editor.putString(EMAIL,email);
        editor.putString(PHONE,Mobile);
        editor.apply();
        return true;
    }

    public boolean setAREANAME(String arename){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AREANAME,arename);
        editor.apply();
        return true;
    }
    public  String getAREANAME(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AREANAME, null);
    }
    public boolean setAreacode(String arecode){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AREACODE,arecode);
        editor.apply();
        return true;
    }

    public  String getAreacode(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AREACODE, null);
    }



    public boolean setPassword(String pass){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD,pass);
        editor.apply();
        return true;
    }

    public  String getPassword(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().commit();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(USER_NAME, null) != null){
            return true;
        }
        return false;
    }

    public String getLogout(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOGOUTVALUE, null);
    }

    public  int getUserId(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(USERID, 0);
    }

    public  String getUserNAme(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_NAME, null);
    }

    public  String getEmail(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL, null);
    }

    public  String getPhone(){
        SharedPreferences sharedPreferences = mctx.getSharedPreferences(SHARE_PRE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE, null);
    }


}
