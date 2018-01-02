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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.activities.SignUpActivity;
import com.androidtutorialpoint.ineed.proj.adapters.EmpPackageAdapter;
import com.androidtutorialpoint.ineed.proj.adapters.PackageAdapter;
import com.androidtutorialpoint.ineed.proj.models.EmpPackage;
import com.androidtutorialpoint.ineed.proj.models.JobSeekerPackage;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
public class Signup2EmpFragment extends Fragment implements EmpPackageAdapter.Clicklistner{
    View view;
    RequestQueue requestQueue;
    Gson gson = new Gson();
    List<EmpPackage.ResponseBean.EmployerDataBean> jobSeekerPackage;
    RecyclerView recyclerView;
    EmpPackageAdapter packageAdapter;
    TinyDB sharpref;
    String language, usertype;

    TextView txtlogin;
    Button bt_next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup2empfrag, container, false);
        bt_next = (Button) view.findViewById(R.id.bt_next);

        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        jobSeekerPackage = new ArrayList<>();
        sharpref=new TinyDB(getContext());
        language=sharpref.getString("language_id");

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Signup3frag thirFragment = new Signup3frag();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flContent, thirFragment).addToBackStack(null).commit();
            }
        });

        String noAccount = "HAVE AN ACCOUNT? LOGIN ";
        int i = noAccount.indexOf("LO");
        int j = noAccount.indexOf("IN");
        txtlogin = (TextView) view.findViewById(R.id.txt_sign_Login2e);
        txtlogin.setMovementMethod(LinkMovementMethod.getInstance());
        txtlogin.setText(noAccount, TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) txtlogin.getText();
        final ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, i, j + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        recyclerView = (RecyclerView) view.findViewById(R.id.signup2emp_recycler);

        packageAdapter = new EmpPackageAdapter(getContext(), jobSeekerPackage);
//        set recyclerview
        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mlayoutManager);
        packageAdapter.setclick(this);
        recyclerView.setAdapter(packageAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        image1.setImageResource(R.drawable.ic_one);
        image2.setImageResource(R.drawable.ic_twod);
        image3.setImageResource(R.drawable.ic_three);
        getJobPackage();
    }


    public void getJobPackage(){
        if (jobSeekerPackage!=null){
            jobSeekerPackage.clear();
        }
        if (!language.isEmpty()){
            usertype = selection;
            HashMap<String,String> params=new HashMap<>();
            params.put("user_type",usertype);
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

        Signup3frag thirFragment=new Signup3frag();
        price = jobSeekerPackage.get(post).getEmployers_package_prize();
        packageId = jobSeekerPackage.get(post).getEmployers_package_id();
        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.flContent,thirFragment)
                .addToBackStack(null).commit();
    }
}