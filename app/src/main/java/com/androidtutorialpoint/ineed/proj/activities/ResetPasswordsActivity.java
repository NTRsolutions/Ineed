package com.androidtutorialpoint.ineed.proj.activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.StatusModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class ResetPasswordsActivity extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    TextView txt_forgetpas;
    RequestQueue requestQueue;
    EditText et_email;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwords);
        txt_forgetpas= (TextView) findViewById(R.id.txt_forgetpass);
        et_email= (EditText) findViewById(R.id.et_email);
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        txt_forgetpas.setOnClickListener(this);
        setuptoolbar();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
    public void onClick(View v) {
        InputMethodManager methodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
        Email=et_email.getText().toString();
        if(Utillity.isNetworkConnected(this)) {
            if (Email != null && !Email.isEmpty()) {
                if (Utillity.CheckEmail(Email)) {
                    Utillity.showloadingpopup(this);
                    HashMap<String,String> params=new HashMap<>();
                    params.put("email",Email);
                    CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.FORGETPASSWORD,params,this.sucesslistener(),this.errorlistener());
                    requestQueue.add(customRequest);
                }
                else
                {
                    Utillity.message(this,getResources().getString(R.string.validemail));
                }
            }
            else
            {
                Utillity.message(this,getResources().getString(R.string.emptyemail));
            }
        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View view=snackbar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }

    private Response.Listener<JSONObject> sucesslistener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Gson gson=new Gson();
                String message;
                StatusModel statusModel=null;
                try {
                  statusModel=new StatusModel();
                    statusModel=gson.fromJson(response.toString(),StatusModel.class);
                    boolean status=statusModel.isStatus();
                    message=statusModel.getMsg();
                    if(status==true)
                    {
                        Utillity.message(ResetPasswordsActivity.this,message);
                        finish();
                    }
                    else
                    {
                        Utillity.message(ResetPasswordsActivity.this,message);
                    }
                }
                catch (Exception e)
                {
                    Utillity.message(ResetPasswordsActivity.this,""+e);

                }
            }
        };
    }
    private Response.ErrorListener errorlistener()
    {
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

