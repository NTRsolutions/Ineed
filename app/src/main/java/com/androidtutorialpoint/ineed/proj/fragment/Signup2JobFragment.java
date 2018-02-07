package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.AppGlobal;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.activities.SignUpActivity;
import com.androidtutorialpoint.ineed.proj.activities.UpgradePlanActivity;
import com.androidtutorialpoint.ineed.proj.adapters.PackageAdapter;
import com.androidtutorialpoint.ineed.proj.models.JobSeekerPackage;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image1;

import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image2;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image3;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.packageId;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.price;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.selection;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class Signup2JobFragment extends Fragment implements PackageAdapter.Clicklistner{
    View view;
    TextView txtlogin;
    AppGlobal appGlobal = AppGlobal.getInstancess();
    Button bt_next;
    TinyDB sharpref;
    LoginData loginData = new LoginData();
    String language, usertype, transaction_id, userId;
    RequestQueue requestQueue;
    Gson gson = new Gson();
    List<JobSeekerPackage.ResponseBean.JobsekerDataBean> jobSeekerPackage;
    RecyclerView recyclerView;
    PackageAdapter packageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.signup2jobfrag,container,false);

        bt_next=(Button) view.findViewById(R.id.bt_next);
        recyclerView = (RecyclerView) view.findViewById(R.id.signup2job_recycler);

//        initialize
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        jobSeekerPackage = new ArrayList<>();
        appGlobal.context=getActivity();
        sharpref=new TinyDB(getContext());

        userId = SignUpActivity.userid;
        language=sharpref.getString("language_id");
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup3frag thirFragment=new Signup3frag();
                getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.flContent,thirFragment)
                        .addToBackStack(null).commit();
            }
        });

        String noAccount="HAVE AN ACCOUNT? LOGIN";
        int i=noAccount.indexOf("LO");
        int j=noAccount.indexOf("IN");
        txtlogin= (TextView)view.findViewById(R.id.txt_sign_Login2);
        txtlogin.setMovementMethod(LinkMovementMethod.getInstance());
        txtlogin.setText(noAccount, TextView.BufferType.SPANNABLE);
//        txtlogin.setVisibility(View.GONE);
        Spannable spannable= (Spannable) txtlogin.getText();
        final ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class));

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };
//        spannable.setSpan(clickableSpan,i,j+2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        packageAdapter = new PackageAdapter(getContext(), jobSeekerPackage);
//        set recyclerview
        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mlayoutManager);
        packageAdapter.setclick(this);
        recyclerView.setAdapter(packageAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        return view;
    }

    public void getJobPackage(){
        if (jobSeekerPackage!=null){
            jobSeekerPackage.clear();
        }
        if (!language.isEmpty()){
            usertype = selection;
            if (selection.equals("2")){
                HashMap<String,String> params=new HashMap<>();
                params.put("user_type",usertype);
                params.put("language",language);
                CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.GET_PACKAGE,params,
                        this.success(),this.error());
                requestQueue.add(customRequest);

            }
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
                    JobSeekerPackage jobSeekerPackage1 = gson.fromJson(response.toString(), JobSeekerPackage.class);
                    jobSeekerPackage.addAll(jobSeekerPackage1.getResponse().getJobseker_data());
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
    public void onResume() {
        super.onResume();
        image1.setImageResource(R.drawable.ic_one);
        image2.setImageResource(R.drawable.ic_twod);
        image3.setImageResource(R.drawable.ic_three);

        getJobPackage();
    }

    @Override
    public void itemclick(View v, int post) {

        Signup3frag thirFragment=new Signup3frag();
        Bundle args = new Bundle();
        price = jobSeekerPackage.get(post).getJobseekars_package_prize();
        packageId = jobSeekerPackage.get(post).getJobseekars_package_id();
        if (Integer.parseInt(price)>0){
            args.putString("price", jobSeekerPackage.get(post).getJobseekars_package_prize());
            thirFragment.setArguments(args);
            getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.flContent,thirFragment)
                    .addToBackStack(null).commit();
        } else {
            transaction_id = "free";
            upgradePackage();
        }
    }



    public void upgradePackage() {
        if (transaction_id == null) {
            Utillity.message(getActivity(), "Please make payment");
        } else {
            if (!transaction_id.isEmpty()) {

                HashMap<String, String> params = new HashMap<>();
                params.put("user_type", usertype);
                params.put("language", language);
                params.put("transaction_id", transaction_id);
                params.put("package_id", packageId);
                params.put("user_id", userId);
                CustomRequest customRequest = new CustomRequest(com.android.volley.Request.Method.POST, ApiList.MAKE_PAYMENT, params,
                        this.successSign(), this.error());
                requestQueue.add(customRequest);
            } else {
                Utillity.message(getActivity(), "Please make payment before signup ");
            }
        }
    }

    private com.android.volley.Response.Listener<JSONObject> successSign()
    {
        Utillity.showloadingpopup(getActivity());
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                if (response!=null){
                    try {
                        if (response.getString("status").equals("true")){
                            Utillity.message(getActivity(), "Process completed");
                            appGlobal.setLoginData(response.toString());
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                        else {
                            Utillity.message(getActivity(),"Something went wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
