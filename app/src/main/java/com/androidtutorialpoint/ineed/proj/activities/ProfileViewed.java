package com.androidtutorialpoint.ineed.proj.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.adapters.PackageAdapter;
import com.androidtutorialpoint.ineed.proj.adapters.ProfileViewdAdapter;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.ViewedProflie;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

public class ProfileViewed extends AppCompatActivity implements ProfileViewdAdapter.Clicklistner {
    ActionBar actionBar;
    TinyDB tinyDB;
    LoginData loginData = new LoginData();
    Gson gson = new Gson();
    String userId;
    List<ViewedProflie.ProfileListBean> profileListBean = new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    ProfileViewdAdapter profileViewdAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_viewed);
        tinyDB = new TinyDB(getApplicationContext());
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userId = loginData.getUser_detail().getUser_id();
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        recyclerView = findViewById(R.id.viewed_recycler);

//        profileViewdAdapter.setclick(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setuptoolbar();
        getViewdProfile();
    }


    public void getViewdProfile(){
            if (profileListBean!=null){
                profileListBean.clear();
            }
        HashMap<String,String> params=new HashMap<>();
        params.put("employer_id",userId);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.EMPLOYER_VIEWED,params,
                this.success(),this.error());
        requestQueue.add(customRequest);
    }


    private Response.Listener<JSONObject> success()
    {
        Utillity.showloadingpopup(ProfileViewed.this);
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Log.d(TAG, "onResponse:data "+response.toString());
                if (response!=null){
                    ViewedProflie profileListBeans = new ViewedProflie();
                    profileListBeans = gson.fromJson(response.toString(), ViewedProflie.class);
                    profileListBean = profileListBeans.getProfile_list();

                    profileViewdAdapter = new ProfileViewdAdapter(getApplicationContext(), profileListBean);
//        set recyclerview
                    RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mlayoutManager);
                    recyclerView.setAdapter(profileViewdAdapter);
                    profileViewdAdapter.notifyDataSetChanged();

                }
            }
        };
    }

    private Response.ErrorListener error()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(getApplicationContext(), getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText(R.string.profileview);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void itemclick(View v, int post) {

    }
}
