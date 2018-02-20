package com.androidtutorialpoint.ineed.proj.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.AboutActivity;
import com.androidtutorialpoint.ineed.proj.activities.HomeActivity;
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.activities.Splash2Activity;
import com.androidtutorialpoint.ineed.proj.activities.UpgradePlanActivity;
import com.androidtutorialpoint.ineed.proj.adapters.LanguageAdapter;
import com.androidtutorialpoint.ineed.proj.models.JobseekerDashBoardModel;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.permissions.AppPermissions;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class JobseekerDashboardFragment extends Fragment implements View.OnClickListener{
    TextView txtMyProfile, txtExpiry, txtPrice,txtStart,txtViewedProfile, txtPlan, txtUpgrade;
    TinyDB tinyDB;
    Gson gson = new Gson();
    View view;
    String userId;
    LoginData loginData = new LoginData();
    private AppPermissions mRuntimePermission;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jobseeker_dashboard, container, false);

        tinyDB = new TinyDB(getContext());
        mRuntimePermission = new AppPermissions(getActivity());

        String data = tinyDB.getString("login_data");
        loginData= gson.fromJson(data, LoginData.class);
        userId = loginData.getUser_detail().getUser_id();

//        find jobseekerid
        txtMyProfile = (TextView) view.findViewById(R.id.jobseekerdash_txtMyProfile);
        txtExpiry = (TextView) view.findViewById(R.id.jobseekerdash_expiryDate);
        txtViewedProfile = (TextView) view.findViewById(R.id.jobseekerdash_viewNo);
        txtPlan = (TextView) view.findViewById(R.id.jobseekerdash_currentPlan);
        txtUpgrade = view.findViewById(R.id.jobseekerdash_upgrade);
        txtPrice = view.findViewById(R.id.jobseekerdash_Planprice);
        txtStart = view.findViewById(R.id.jobseekerdash_plan_start);

        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");
//        set onclick
        txtUpgrade.setOnClickListener(this);
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

            case R.id.jobseekerdash_upgrade:
                startActivity(new Intent(getActivity(), UpgradePlanActivity.class));
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
            customRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(customRequest);
        }
//        else
//        {
//            Snackbar snackbar=Snackbar.make(view.findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
//            View snackbarview=snackbar.getView();
//            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
//            snackbar.show();
//        }
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
                    if (dashBoardModel.getJobseeker_dashboard().getUser_package_id().equals("Expired")){
                        setupoverlay();
                    } else {
                        txtExpiry.setText(dashBoardModel.getJobseeker_dashboard().getUser_package_expire_date());
                        txtPlan.setText(dashBoardModel.getJobseeker_dashboard().getUser_package_id());
                        int id=dashBoardModel.getJobseeker_dashboard().getUser_viewed();
                        txtViewedProfile.setText(String.valueOf(id));
                        txtPrice.setText("$ "+dashBoardModel.getJobseeker_dashboard().getUser_price());
                        txtStart.setText(dashBoardModel.getJobseeker_dashboard().getUser_stardate());
                    }
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


    private void setupoverlay() {
        final Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_overlay_popup);
        final TextView txtlogout = dialog.findViewById(R.id.txtLogout);
        final TextView txtMsg = dialog.findViewById(R.id.txt_msg);
        txtMsg.setText("Your package expired please upgrade");
        final Button upgrade=(Button)dialog.findViewById(R.id.overupgrade);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), UpgradePlanActivity.class));
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                tinyDB.remove("login_data");
                getActivity().finish();

            }
        });

        dialog.show();
    }

}
