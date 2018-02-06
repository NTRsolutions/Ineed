package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.DialogActivity;
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.activities.SignUpActivity;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.TokenResponse;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;
import com.payfort.fort.android.sdk.base.FortSdk;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.payfort.sdk.android.dependancies.base.FortInterfaces;
import com.payfort.sdk.android.dependancies.models.FortRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image1;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image2;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image3;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 * Uploaded on Git Successfully
 */
public class CompleteSignup3frag extends Fragment {
    View view;
    TextView txtlogin;
    private FortCallBackManager fortCallback;
    private String sdk_token  ;
    String price, language, email=" ", userId, mobile=" ";
    TinyDB sharpref;
    RadioGroup radioButton;
    LoginData loginData ;
    Gson gson = new Gson();
    Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.signup3frag,container,false);

        mContext = getActivity();

//        initialize
        sharpref=new TinyDB(getContext());
//        loginData = new LoginData();
//        String data = tinyDB.getString("login_data");
//        loginData = gson.fromJson(data, LoginData.class);
//        email = loginData.getUser_detail().getUser_email();
//        userId = loginData.getUser_detail().getUser_id();
//        mobile = loginData.getUser_detail().getUser_phone();
        language=sharpref.getString("language_id");

        String noAccount = "DON'T SELECT PACKAGE LOGOUT ";
        int i = noAccount.indexOf("LO");
        int j = noAccount.indexOf("OUT");
        price = getArguments().getString("price");

        txtlogin= (TextView)view.findViewById(R.id.txt_sign_Login3);
//        radioButton = (RadioGroup) view.findViewById(R.jobseekerid.radiogroup);
        txtlogin.setMovementMethod(LinkMovementMethod.getInstance());
        txtlogin.setText(noAccount, TextView.BufferType.SPANNABLE);
        txtlogin.setVisibility(View.GONE);
        Spannable spannable= (Spannable) txtlogin.getText();
        final ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK));
                sharpref.remove("login_data");
                getActivity().finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(Color.BLACK);
                ds.setUnderlineText(false);
            }
        };

        spannable.setSpan(clickableSpan,i,j+3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Complete Payment");

    }

}
