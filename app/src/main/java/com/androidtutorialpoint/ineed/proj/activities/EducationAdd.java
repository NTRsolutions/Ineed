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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EducationAdd extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    LinearLayout bottom_toolbar;
    TextView txt_save,txt_cancel;
    EditText edtCourseTitle, edtSpeci, edtyear, edtInsti;
    String courseTitle="", speci="", year=" ", insti= " ", userid, eduId=" ";
    TinyDB tinyDB;
    LoginData loginData = new LoginData();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_add);
        setuptoolbar();
        setupbottomtoolbar();

        tinyDB = new TinyDB(getApplicationContext());
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userid = loginData.getUser_detail().getUser_id();

//        find jobseekerid
        edtCourseTitle = findViewById(R.id.txt_edu_course_title);
        edtInsti = findViewById(R.id.txt_institute);
        edtSpeci = findViewById(R.id.txt_Specialization);
        edtyear = findViewById(R.id.txt_edu_year);

       if (getIntent().hasExtra("title")){
           courseTitle = getIntent().getStringExtra("title");
       }

        if (getIntent().hasExtra("speci")){
            speci = getIntent().getStringExtra("speci");
        }

        if (getIntent().hasExtra("insti")){
            insti = getIntent().getStringExtra("insti");
        }

        if (getIntent().hasExtra("year")){
            year = getIntent().getStringExtra("year");
        }
        if (getIntent().hasExtra("eduId")){
            eduId = getIntent().getStringExtra("eduId");
        }

        edtyear.setText(year);
        edtSpeci.setText(speci);
        edtInsti.setText(insti);
        edtCourseTitle.setText(courseTitle);

        txt_cancel.setOnClickListener(this);
        txt_save.setOnClickListener(this);


    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Education");
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    private void setupbottomtoolbar() {
        bottom_toolbar=findViewById(R.id.bottom_toolbar);
        txt_save=bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=bottom_toolbar.findViewById(R.id.txt_cancel);
        txt_save.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
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
                year = edtyear.getText().toString();
                courseTitle = edtCourseTitle.getText().toString();
                insti = edtInsti.getText().toString();
                speci = edtSpeci.getText().toString();

                if (!year.isEmpty()){
                    if (!courseTitle.isEmpty()){
                        if (!insti.isEmpty()){
                            if (!speci.isEmpty()){
                                if (eduId==null){
                                    addEdu(userid, courseTitle,speci,insti,year);
                                } else {
                                    editEdu(userid, courseTitle,speci,insti,year,eduId);
                                }

                            }else {
                                Utillity.message(getApplicationContext(), "Please enter specilization");
                            }
                        } else {
                            Utillity.message(getApplicationContext(), "Please enter institute name");
                        }
                    } else {
                        Utillity.message(getApplicationContext(), "Please enter course");
                    }
                } else {
                    Utillity.message(getApplicationContext(), "Please enter year");
                }
                break;
            case R.id.txt_cancel:
                finish();
                break;
        }
    }

    private void editEdu(String userId, String courseTitle, String speci, String insti, String year, String eduId) {
        if(Utillity.isNetworkConnected(EducationAdd.this)) {
            Utillity.showloadingpopup(EducationAdd.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", userId);
            params.put("course_title", courseTitle);
            params.put("specilization", speci);
            params.put("institute", insti);
            params.put("year", year);
            params.put("jobseeker_education_id", eduId);

            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_ADD_EDU, params, this.success(), this.errorListener());
            queue.add(customRequest);

        } else {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }


    private void addEdu(String userId, String courseTitle, String speci, String insti, String year) {
        if(Utillity.isNetworkConnected(EducationAdd.this)) {
            Utillity.showloadingpopup(EducationAdd.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", userId);
            params.put("course_title", courseTitle);
            params.put("specilization", speci);
            params.put("institute", insti);
            params.put("year", year);
            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_ADD_EDU, params, this.success(), this.errorListener());
            queue.add(customRequest);

        } else {
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
                Utillity.message(EducationAdd.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }
    private Response.Listener<JSONObject> success()
    {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse: edu"+response.toString());
                if (response!=null){
                    try {
                        if (response.getString("status").equals("true")){
                            Utillity.message(getApplicationContext(), "Education added successfully");
                            finish();
                        } else {
                            Utillity.message(getApplicationContext(), "Education not added");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Utillity.hidepopup();

            }
        };
    }
}
