package com.androidtutorialpoint.ineed.proj.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.MyApplication;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.AboutUsModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AboutActivity extends AppCompatActivity {
ActionBar actionBar;
TextView text_about;
TinyDB tinyDB;
String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        text_about= (TextView) findViewById(R.id.about_text);
        tinyDB = new TinyDB(getApplicationContext());
        language = tinyDB.getString("language_id");
        setuptoolbar();
        //Abc
        String name="Muhib";
        String ac= "abccccc";//
        //
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("About");
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
    protected void onResume() {
        super.onResume();
        setupApi();
    }

    private void setupApi() {
        if(Utillity.isNetworkConnected(this))
        {
            Utillity.showloadingpopup(AboutActivity.this);
            HashMap<String,String> params=new HashMap<>();
            params.put("language",language);
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST,ApiList.ABOUT,params,this.sucesslistener(),this.errorlistener());
            requestQueue.add(customRequest);
        }
        else
        {
            Snackbar snackbar= Snackbar.make(findViewById(android.R.id.content),"Check Your Internet Connection",Snackbar.LENGTH_LONG);
            View snackbarView=snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
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

    private Response.Listener<JSONObject> sucesslistener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();

                    //Utillity.message(getApplicationContext(),""+response);
                Gson gson=new Gson();
                AboutUsModel aboutUsModel=null;
                try
                {
                    aboutUsModel=new AboutUsModel();
                    aboutUsModel=gson.fromJson(response.toString(),AboutUsModel.class);
                    boolean status=aboutUsModel.isStatus();
                    if(status=true)
                    {
                        String detailtext=aboutUsModel.getResponse().getAbout_list().getCms_language_content();
                        text_about.setText(detailtext);
                       // Utillity.message(getApplicationContext(),""+detailtext);

                    }
                    else
                    {
                        Utillity.message(getApplicationContext(),"Error");

                    }
                }
                catch (Exception e)
                {
                    Utillity.message(getApplicationContext(),""+e);
                }

                Log.d("Response",""+response);
            }
        };
    }

    private Response.ErrorListener errorlistener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utillity.hidepopup();
                Utillity.message(getApplicationContext(),""+error);
                Log.d("Error Respons",""+error);
            }
        };
    }
}
