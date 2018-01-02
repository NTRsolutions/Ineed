package com.androidtutorialpoint.ineed.proj.webservices;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidtutorialpoint.ineed.proj.MyApplication;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class VolleySingelton {
private static VolleySingelton sInstance=null;
    private RequestQueue mRequestQueue;
    private VolleySingelton()
    {
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
    }
    public static VolleySingelton getsInstance()
    {
        if (sInstance==null)
        {
            sInstance=new VolleySingelton();

        }
        return sInstance;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }
}
