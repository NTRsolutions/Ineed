package com.androidtutorialpoint.ineed.proj.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.StatusModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.HashMap;

import static com.androidtutorialpoint.ineed.proj.activities.HomeActivity.toolbar;

public class ChangePasswordActivity extends AppCompatActivity {
    ActionBar actionBar;
    TextView btnUpdate;
    EditText edtCurrent, edtNew, edtConfrim;
    TinyDB tinyDB ;
    String language, userid, oldPass, newPass, confirmPass;
    Gson gson = new Gson();
    LoginData loginData;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setuptoolbar();

//        initialize
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        tinyDB = new TinyDB(getApplicationContext());
        loginData = new LoginData();
        String data = tinyDB.getString("login_data");
        language = tinyDB.getString("language_id");
        loginData = gson.fromJson(data, LoginData.class);
        userid = loginData.getUser_detail().getUser_id();

//        find jobseekerid
        edtConfrim = (EditText) findViewById(R.id.change_pass_edtNewConfirm);
        edtCurrent = (EditText) findViewById(R.id.change_pass_edtCurrent);
        edtNew = (EditText) findViewById(R.id.change_pass_edtNew);
        btnUpdate = (TextView) findViewById(R.id.change_pass_btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPass = edtCurrent.getText().toString().trim();
                newPass = edtNew.getText().toString();
                confirmPass = edtConfrim.getText().toString();
                if (!oldPass.isEmpty()){
                    if (!newPass.isEmpty()){
                        if (confirmPass.equals(newPass)){
                            updatePass();
                        } else {
                            Utillity.message(getApplicationContext(), "Confirm password not match");
                        }
                    }else {
                        Utillity.message(getApplicationContext(), "Please enter new password");
                    }
                } else {
                    Utillity.message(getApplicationContext(), "Please enter old password");
                }
            }
        });

    }


    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText(getResources().getString(R.string.forgotpass));
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }


    public void updatePass(){
        if (Utillity.isNetworkConnected(this))
        {
            HashMap<String,String> params=new HashMap<>();
            /*oldpass,newpass,userid,language*/
            params.put("oldpass",oldPass);
            params.put("newpass",newPass);
            params.put("userid",userid);
            params.put("language",language);
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.UPDATE_PASSWORD,params,
                    this.success(),this.error());
            requestQueue.add(customRequest);
        }
        else
        {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    getResources().getString(R.string.internetConnection), Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }
    private Response.Listener<JSONObject> success()
    {   Utillity.showloadingpopup(this);
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                StatusModel statusModel = new StatusModel();
                if (response!=null){
                    Gson gson = new Gson();
                    statusModel = gson.fromJson(response.toString(),StatusModel.class);
                    Utillity.message(getApplicationContext(), statusModel.getMsg());
                    finish();
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

                Utillity.message(getApplicationContext(), "Connection error ");
                Utillity.hidepopup();

            }
        };
    }



    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("Change Password");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
