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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;

import org.json.JSONObject;

import java.util.HashMap;

public class PersonalAdd extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    Toolbar toolbar;
    LinearLayout bottom_toolbar;
    TextView txt_save,txt_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add);
        setuptoolbar();
        setupbottomtoolbar();
    }

    private void setupbottomtoolbar() {
        bottom_toolbar=findViewById(R.id.bottom_toolbar);
        txt_save=bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=bottom_toolbar.findViewById(R.id.txt_cancel);
        txt_save.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
    }

    private void setuptoolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Profile");
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_save:
               // inputMethodManager.hideSoftInputFromInputMethod(v.getWindowToken(),0);
               // saveApi();
                Utillity.message(getApplicationContext(),"Successfully Added");
                finish();
                break;
            case R.id.txt_cancel:
                InputMethodManager inputMethodManager= (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
                finish();
                break;
        }
    }

    private void saveApi() {
        if(Utillity.isNetworkConnected(PersonalAdd.this)) {
            Utillity.showloadingpopup(PersonalAdd.this);
                            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
                HashMap<String, String> params = new HashMap<>();
                params.put("language", "en");
                CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.HELP_CATEGORY, params, this.success(), this.errorListener());
                queue.add(customRequest);
                customRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }
    private Response.ErrorListener errorListener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(PersonalAdd.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }
    private Response.Listener<JSONObject> success()
    {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();

            }
        };
    }
}
