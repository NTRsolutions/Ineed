package com.androidtutorialpoint.ineed.proj.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.EmpPackage;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class PaymentHistoryActivity extends AppCompatActivity {
    ActionBar actionBar;
    Toolbar toolbar;
    RequestQueue requestQueue;
    Gson gson = new Gson();
    TinyDB tinyDB;
    LoginData loginData = new LoginData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        setuptoolbar();
        tinyDB = new TinyDB(getApplicationContext());
        if (tinyDB.contains("login_data")){
            String loginPrefData = tinyDB.getString("login_data");
            loginData = gson.fromJson(loginPrefData, LoginData.class);
            user_id = loginData.getUser_detail().getUser_id();
//            getHistory();
        }

    }

    private void setuptoolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Payment History");
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

    String user_id;
    public void getHistory(){

        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",user_id);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.GET_PACKAGE,params,
                this.success(),this.error());
        requestQueue.add(customRequest);
    }

    private Response.Listener<JSONObject> success()
    {
        Utillity.showloadingpopup(PaymentHistoryActivity.this);
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                if (response!=null){
                    Log.d(TAG, "onResponse: "+response.toString());
                   /* EmpPackage jobSeekerPackage1 = gson.fromJson(response.toString(), EmpPackage.class);
                    jobSeekerPackage.addAll(jobSeekerPackage1.getResponse().getEmployer_data());
                    packageAdapter.notifyDataSetChanged();*/
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
                Utillity.message(PaymentHistoryActivity.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();

            }
        };
    }

}
