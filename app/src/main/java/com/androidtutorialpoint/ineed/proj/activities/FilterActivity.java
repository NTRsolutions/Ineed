package com.androidtutorialpoint.ineed.proj.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class FilterActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        fl_jobtype=findViewById(R.id.sp_jobtype);
        fl_experience=findViewById(R.id.sp_experience);
        fl_noticeperiod=findViewById(R.id.sp_noticeperiod);
        fl_ctc=findViewById(R.id.sp_ctc);
        fl_age=findViewById(R.id.sp_age);
        fl_gender=findViewById(R.id.sp_gender);
        getfilterApi();
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
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,jobs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fl_jobtype.setAdapter(adapter);
        fl_jobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
}
