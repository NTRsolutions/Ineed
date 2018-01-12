package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.AboutActivity;
import com.androidtutorialpoint.ineed.proj.activities.HomeActivity;
import com.androidtutorialpoint.ineed.proj.models.JobseekerDashBoardModel;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class JobseekerDashboardFragment extends Fragment implements View.OnClickListener{
    TextView txtMyProfile, txtExpiry, txtViewedProfile, txtPlan;
    TinyDB tinyDB;
    Gson gson = new Gson();
    View view;
    String userId;
    LoginData loginData = new LoginData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jobseeker_dashboard, container, false);

        tinyDB = new TinyDB(getContext());
        String data = tinyDB.getString("login_data");
        loginData= gson.fromJson(data, LoginData.class);
        userId = loginData.getUser_detail().getUser_id();

//        find id
        txtMyProfile = (TextView) view.findViewById(R.id.jobseekerdash_txtMyProfile);
        txtExpiry = (TextView) view.findViewById(R.id.jobseekerdash_expiryDate);
        txtViewedProfile = (TextView) view.findViewById(R.id.jobseekerdash_viewNo);
        txtPlan = (TextView) view.findViewById(R.id.jobseekerdash_currentPlan);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

//        set onclick
        txtMyProfile.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.jobseekerdash_txtMyProfile:
                DashboardJobseeker dashboardJobseeker = new DashboardJobseeker();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, dashboardJobseeker).addToBackStack(null).commit();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getdetails();
    }

    private void getdetails() {
        if(Utillity.isNetworkConnected(getContext())) {
            Utillity.showloadingpopup(getActivity());
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", userId);
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_DASHBOARD, params, this.sucess(), this.error());
            requestQueue.add(customRequest);
        }
        else
        {
            Snackbar snackbar=Snackbar.make(view.findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }
    private Response.Listener<JSONObject> sucess()
    {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Log.d(TAG, "onResponse: "+response.toString());
                JobseekerDashBoardModel dashBoardModel=null;
                try {
                dashBoardModel=new JobseekerDashBoardModel();
                dashBoardModel=gson.fromJson(response.toString(),JobseekerDashBoardModel.class);
                boolean status=dashBoardModel.isStatus();
                if(status==true)
                {
                    txtExpiry.setText(dashBoardModel.getJobseeker_dashboard().getUser_package_expire_date());
                    txtPlan.setText(dashBoardModel.getJobseeker_dashboard().getUser_package_id());
                    int id=dashBoardModel.getJobseeker_dashboard().getUser_viewed();
                    txtViewedProfile.setText(String.valueOf(id));
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Error",""+e);
                    Utillity.message(getContext(),"Error");

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
}
