package com.androidtutorialpoint.ineed.proj.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.TermsData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.HashMap;

public class ContactUsActivity extends AppCompatActivity {
    ActionBar actionBar;
    String language, fName, lName, email, phone, msg;
    TinyDB tinyDB;
    EditText edtFName, edtLName, edtEmail, edtPhone, edtMsg;
    TextView txtSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setuptoolbar();
        tinyDB = new TinyDB(getApplicationContext());
        language = tinyDB.getString("language_id");

//        find jobseekerid
        edtFName = (EditText) findViewById(R.id.contact_etFName);
        edtLName = (EditText) findViewById(R.id.contact_etLName);
        edtEmail = (EditText) findViewById(R.id.contact_etEmail);
        edtPhone = (EditText) findViewById(R.id.contact_etPhone);
        edtMsg = (EditText) findViewById(R.id.contact_etMsg);
        txtSubmit = (TextView) findViewById(R.id.contact_send);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName = edtFName.getText().toString().trim();
                lName = edtLName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                phone = edtPhone.getText().toString().trim();
                msg = edtMsg.getText().toString().trim();

                if (!fName.isEmpty()){
                    if (!lName.isEmpty()){
                        if (Utillity.CheckEmail(email)){
                            if (Utillity.CheckPhone(phone)){
                                getContactUs(fName,lName,email,phone,msg);

                            } else {
                                Utillity.message(getApplicationContext(), "Please enter valid mobile number");
                            }
                        }else {
                            Utillity.message(getApplicationContext(), "Please enter valid email");
                        }
                    }  else {
                        Utillity.message(getApplicationContext(), "Please enter your last name");
                    }
                } else {
                    Utillity.message(getApplicationContext(), "Please enter your first name");
                }
            }
        });


    }

    public void getContactUs(String fname, String lName, String  email, String phone, String msg){
        if(Utillity.isNetworkConnected(this))
        {
            Utillity.showloadingpopup(ContactUsActivity.this);
            HashMap<String,String> params=new HashMap<>();
            /*fname,lname,phone,email,message*/
            params.put("language",language);
            params.put("fname",fname);
            params.put("lname",lName);
            params.put("phone",phone);
            params.put("email",email);
            params.put("message",msg);
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.CONTACT_US,params,this.sucesslistener(),this.errorlistener());
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

    private Response.Listener<JSONObject> sucesslistener()
    {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();

                Log.d("TAG", "onResponse: "+response.toString());
                //Utillity.message(getApplicationContext(),""+response);
                Gson gson=new Gson();

                try
                {
                    if (response.getString("status").equals("true")){
                        Utillity.message(getApplicationContext(),"Message send successfully.");
                        finish();
                    } else {
                        Utillity.message(getApplicationContext(),"Message not sent.");
                    }
                }
                catch (Exception e)
                {
                    Utillity.message(getApplicationContext(),getResources().getString(R.string.internetConnection));

                }

                Log.d("Response",""+response);
            }
        };
    }

    private Response.ErrorListener errorlistener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utillity.hidepopup();
                Utillity.message(getApplicationContext(),getResources().getString(R.string.internetConnection));

                Log.d("Error Respons",""+error);
            }
        };
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText(R.string.contactus);
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
                Utillity.hideSoftKeyboard(ContactUsActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
