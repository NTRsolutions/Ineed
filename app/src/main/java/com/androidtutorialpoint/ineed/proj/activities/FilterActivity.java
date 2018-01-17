package com.androidtutorialpoint.ineed.proj.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.FilterModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int FILTER_REQUEST = 222 ;
    Spinner fl_jobtype,fl_experience,fl_noticeperiod,fl_ctc,fl_age,fl_gender;
    List<FilterModel.JobtypesBean> jobtypes;
    private List<FilterModel.ExperiencesBean> experiences;
    private List<FilterModel.NoticeperiodsBean> noticeperiods;
    private List<FilterModel.CtcsBean> ctcs;
    private List<FilterModel.AgesBean> ages;
    ArrayList<String> jobs =new ArrayList<>();
    ArrayList<String> exp =new ArrayList<>();
    ArrayList<String> notice =new ArrayList<>();
    ArrayList<String> ctc =new ArrayList<>();
    ArrayList<String> age =new ArrayList<>();
    ArrayList<String> gender =new ArrayList<>();
    ActionBar actionBar;
    Button bt_fliteredSearch;
    String Experience,Ctc,Age,Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
      //  fl_jobtype=findViewById(R.id.sp_jobtype);
        fl_experience=findViewById(R.id.sp_experience);
      //  fl_noticeperiod=findViewById(R.id.sp_noticeperiod);
        fl_ctc=findViewById(R.id.sp_ctc);
        fl_age=findViewById(R.id.sp_age);
        fl_gender=findViewById(R.id.sp_gender);
        bt_fliteredSearch=findViewById(R.id.bt_fliteredSearch);
        bt_fliteredSearch.setOnClickListener(this);
        getfilterApi();
        setuptoolbar();
    }

    private void getfilterApi() {
        if(Utillity.isNetworkConnected(this)) {
            Utillity.showloadingpopup(FilterActivity.this);
            HashMap<String,String> params=new HashMap<>();
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.FILTERS,params,this.sucesslistener(),this.errorlistener());
            customRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(customRequest);
        }
        else
        {
            Snackbar snackbar= Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarView=snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }
    private Response.Listener<JSONObject> sucesslistener() {
    return new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Utillity.hidepopup();
            Gson gson=new Gson();
            FilterModel filterModel=null;

            try {
                jobtypes=new ArrayList<>();
                experiences=new ArrayList<>();
                noticeperiods=new ArrayList<>();
                ctcs=new ArrayList<>();
                ages=new ArrayList<>();
                filterModel=new FilterModel();
                filterModel=gson.fromJson(response.toString(),FilterModel.class);
                boolean status=filterModel.isStatus();
                if(status==true)
                {
                jobtypes=filterModel.getJobtypes();
                experiences=filterModel.getExperiences();
                noticeperiods=filterModel.getNoticeperiods();
                ctcs=filterModel.getCtcs();
                ages=filterModel.getAges();
                }
                for (int i=0;i<jobtypes.size();i++) {
                    jobs.add(jobtypes.get(i).getJobtype_name());
                }
                for (int i=0;i<experiences.size();i++) {
                    exp.add(experiences.get(i).getExperience_name());
                }
                for (int i=0;i<noticeperiods.size();i++) {
                    notice.add(noticeperiods.get(i).getNoticeperiod_name());
                }
                for (int i=0;i<ctcs.size();i++) {
                    ctc.add(ctcs.get(i).getCtc_name());
                }
                for (int i=0;i<ages.size();i++) {
                    age.add(ages.get(i).getAge_name());
                }
                age.add(0,"Select Age");
                ctc.add(0,"Select CTC");
                exp.add(0,"Select Experience");
                gender.add(0,"Select Gender");
                gender.add("Male");
                gender.add("Female");
                handelspinners();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        };
    }

    private void handelspinners() {
      /*  Job Type*/
        ArrayAdapter<String> ageadapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinnerfilterdesign,age);
        ageadapter.setDropDownViewResource(R.layout.spinnerfilterdesign);
        fl_age.setAdapter(ageadapter);
        fl_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    Age = (String) parent.getItemAtPosition(position);
                }
                else
                {
                    Age="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender="";
            }
        });
        ArrayAdapter<String> ctcadapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinnerfilterdesign,ctc);
        ctcadapter.setDropDownViewResource(R.layout.spinnerfilterdesign);
        fl_ctc.setAdapter(ctcadapter);
        fl_ctc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    Ctc = String.valueOf(parent.getItemAtPosition(position));
                }
                else
                {
                    Ctc="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender="";
            }
        });
        ArrayAdapter<String> expadapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinnerfilterdesign,exp);
        expadapter.setDropDownViewResource(R.layout.spinnerfilterdesign);
        fl_experience.setAdapter(expadapter);
        fl_experience.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) {
                    Experience = String.valueOf(parent.getItemAtPosition(position));
                }
                else
                {
                    Experience="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender="";
            }
        });
        ArrayAdapter<String> genderadapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinnerfilterdesign,gender);
        genderadapter.setDropDownViewResource(R.layout.spinnerfilterdesign);
        fl_gender.setAdapter(genderadapter);
        fl_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
            Gender= (String) parent.getItemAtPosition(position);
                else
                    Gender="";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Gender="";
            }
        });
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
    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Filter");
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
    public void onClick(View v) {
        Intent it= new Intent();
        it.putExtra("experi",Experience);
        it.putExtra("ctc",Ctc);
        it.putExtra("age",Age);
        it.putExtra("gender",Gender);
        setResult(FILTER_REQUEST,it);
        finish();
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
}
