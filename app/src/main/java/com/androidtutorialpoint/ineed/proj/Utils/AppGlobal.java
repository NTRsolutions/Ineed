package com.androidtutorialpoint.ineed.proj.Utils;

import android.app.Application;
import android.content.Context;

import com.mukesh.tinydb.TinyDB;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AppGlobal extends Application {


    private static AppGlobal appGlobal ;
    public Context context;
    TinyDB sharedpref;

    public String loginData;

    public String getLoginData() {
        return loginData;
    }

    public void setLoginData(String loginData) {
        sharedpref=new TinyDB(context);
        sharedpref.putString("login_data",loginData);
        this.loginData = loginData;
    }


    public void resetAllData(){
        setLoginData("");
    }

    public static AppGlobal getInstancess(){
        if(appGlobal==null){ appGlobal=new AppGlobal(); }
        return appGlobal;
    }




}
