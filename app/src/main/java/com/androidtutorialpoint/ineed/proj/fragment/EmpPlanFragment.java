package com.androidtutorialpoint.ineed.proj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.adapters.EmpPackageAdapter;
import com.androidtutorialpoint.ineed.proj.models.EmpPackage;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;

import static android.content.ContentValues.TAG;
import static com.androidtutorialpoint.ineed.proj.activities.UpgradePlanActivity.package_id;
import static com.androidtutorialpoint.ineed.proj.activities.UpgradePlanActivity.price;
import static com.androidtutorialpoint.ineed.proj.activities.UpgradePlanActivity.sdk_token;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EmpPlanFragment extends Fragment implements EmpPackageAdapter.Clicklistner{
    RequestQueue requestQueue;
    Gson gson = new Gson();
    List<EmpPackage.ResponseBean.EmployerDataBean> jobSeekerPackage;
    RecyclerView recyclerView;
    EmpPackageAdapter packageAdapter;
    TinyDB tinyDB;
    String language, user_type, userId;
    LoginData loginData = new LoginData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan_emp, container, false);

//        initialize
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        jobSeekerPackage = new ArrayList<>();
        tinyDB =new TinyDB(getContext());
        language= tinyDB.getString("language_id");
        String data = tinyDB.getString("login_data");
        loginData= gson.fromJson(data, LoginData.class);
        language=tinyDB.getString("language_id");
        user_type = loginData.getUser_detail().getUser_type();
        userId= loginData.getUser_detail().getUser_id();


//        find id
        recyclerView = view.findViewById(R.id.plan_fragment);

        packageAdapter = new EmpPackageAdapter(getContext(), jobSeekerPackage);
//        set recyclerview
        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mlayoutManager);
        packageAdapter.setclick(this);
        recyclerView.setAdapter(packageAdapter);
        getJobPackage();

        return view;
    }


    public void getJobPackage(){
        if (jobSeekerPackage!=null){
            jobSeekerPackage.clear();
        }
        if (!language.isEmpty()){
            HashMap<String,String> params=new HashMap<>();
            params.put("user_type",user_type);
            params.put("language",language);
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.GET_PACKAGE,params,
                    this.success(),this.error());
            requestQueue.add(customRequest);
        } else {
            Utillity.message(getContext(),getResources().getString(R.string.language_select));
        }
    }

    private Response.Listener<JSONObject> success()
    {
        Utillity.showloadingpopup(getActivity());
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                if (response!=null){
                    EmpPackage jobSeekerPackage1 = gson.fromJson(response.toString(), EmpPackage.class);
                    jobSeekerPackage.addAll(jobSeekerPackage1.getResponse().getEmployer_data());
                    packageAdapter.notifyDataSetChanged();
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
                Utillity.message(getContext(), getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();

            }
        };
    }

    @Override
    public void itemclick(View v, int post) {

        PlanPaymentFragment thirFragment=new PlanPaymentFragment();
        Bundle args = new Bundle();
        price = jobSeekerPackage.get(post).getEmployers_package_prize();
        package_id = jobSeekerPackage.get(post).getEmployers_package_id();
        args.putString("price", jobSeekerPackage.get(post).getEmployers_package_prize());
        thirFragment.setArguments(args);
        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.upgrade_frame,thirFragment)
                .addToBackStack(null).commit();
    }



}
