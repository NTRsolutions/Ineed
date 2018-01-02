package com.androidtutorialpoint.ineed.proj;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class MyApplication extends Application {
    public static MyApplication sInstance;
    
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public static MyApplication getInstance()
    {
        return sInstance;
    }
    public static Context getAppContext()
    {
        return sInstance.getApplicationContext();
    }
}
