package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.androidtutorialpoint.ineed.proj.activities.DialogActivity;
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.adapters.PackageAdapter;
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

import static com.androidtutorialpoint.ineed.proj.activities.DialogActivity.price;
import static com.androidtutorialpoint.ineed.proj.activities.DialogActivity.package_id;
import static com.androidtutorialpoint.ineed.proj.activities.DialogActivity.user_type;

/**
 * Created by Rakhi.
 * Contact Number : +91 9
 */
public class CompleteSignup2JobFragment extends Fragment implements PackageAdapter.Clicklistner{
    View view;
    TextView txtlogin;
    Button bt_next;
    TinyDB sharpref;
    String language;
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
        sharpref=new TinyDB(getContext());
        language=sharpref.getString("language_id");
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompleteSignup3frag thirFragment=new CompleteSignup3frag();
                getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.dialog_fragment,thirFragment)
                        .addToBackStack(null).commit();
            }
        });

        String noAccount = "DON'T SELECT PACKAGE LOGOUT ";
        int i = noAccount.indexOf("LO");
        int j = noAccount.indexOf("OUT");
        txtlogin = (TextView) view.findViewById(R.id.txt_sign_Login2);
        txtlogin.setMovementMethod(LinkMovementMethod.getInstance());
        txtlogin.setText(noAccount, TextView.BufferType.SPANNABLE);
        Spannable spannable = (Spannable) txtlogin.getText();
        txtlogin.setVisibility(View.VISIBLE);
        final ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                sharpref.remove("login_data");
                getActivity().finish();
            }

            @Override
            public void updateDrawState(TextPaint dsS) {
                dsS.setColor(Color.BLACK);
                dsS.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan, i, j + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        packageAdapter = new PackageAdapter(getContext(), jobSeekerPackage);
//        set recyclerview
        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        packageAdapter.setclick(this);
        recyclerView.setAdapter(packageAdapter);
        return view;
    }

    public void getJobPackage(){
        if (jobSeekerPackage!=null){
            jobSeekerPackage.clear();
        }
        if (!language.isEmpty()){

            if (user_type.equals("2")){
                HashMap<String,String> params=new HashMap<>();
                params.put("user_type",user_type);
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
        ((DialogActivity)getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.select_package));

        getJobPackage();
    }

    @Override
    public void itemclick(View v, int post) {

        CompleteSignup3frag thirFragment=new CompleteSignup3frag();
        Bundle args = new Bundle();
        price = jobSeekerPackage.get(post).getJobseekars_package_prize();
        package_id = jobSeekerPackage.get(post).getJobseekars_package_id();

        args.putString("price", jobSeekerPackage.get(post).getJobseekars_package_prize());
        thirFragment.setArguments(args);
        getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.dialog_fragment,thirFragment)
                .addToBackStack(null).commit();

    }
}
