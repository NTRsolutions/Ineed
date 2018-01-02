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
//        String data = sharpref.getString("login_data");
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
//        radioButton = (RadioGroup) view.findViewById(R.id.radiogroup);
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


    private void requestSDKToken() {
        createFORTMobileSDKToken();
    }

    private void requestOperation(String command, String sdk_token) {
        final String ECI = "ECOMMERCE";
        final String CUSTOMER_EMAIL = SignUpActivity.email;
        final String LANGUAGE = language;
        final String CURRENCY = "SAR";
        final String AMOUNT = price;
        final String MERCHANT_REFERENCE = Utillity.getRandomString(40) ;
        FortRequest fortRequest = new FortRequest();
        fortRequest.setShowResponsePage(true);
        final Map<String, String> requestMap = new HashMap<>();

        requestMap.put("command",command);
        requestMap.put("merchant_reference",MERCHANT_REFERENCE);
        requestMap.put("amount",AMOUNT);
        requestMap.put("currency",CURRENCY);
        requestMap.put("language",LANGUAGE);
        requestMap.put("customer_email",CUSTOMER_EMAIL);
        requestMap.put("sdk_token",sdk_token);


//        requestMap.put("payment_option","AMEX");
//        requestMap.put("eci",ECI);
//        requestMap.put("order_description",command);
//        requestMap.put("customer_ip",command);
//        requestMap.put("customer_name",sdk_token);
//        if (mobile!=null){
//            requestMap.put("phone_number",mobile);
//        }
//        requestMap.put("settlement_reference",command);
//        requestMap.put("return_url",command);

        fortRequest.setRequestMap(requestMap);
        fortCallback = FortCallback.Factory.create();
        boolean showLoading= true;
        try {
            FortSdk.getInstance().registerCallback(getActivity(), fortRequest,FortSdk.ENVIRONMENT.TEST,
                    5, fortCallback,showLoading, new FortInterfaces.OnTnxProcessed() {
                        @Override
                        public void onCancel(Map<String, String> requestParamsMap, Map<String, String> responseMap) {
                            Toast.makeText(getContext(), responseMap.get("response_message"),
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onSuccess(Map<String, String> requestParamsMap, Map<String, String> fortResponseMap) {
                            Log.d(TAG, "onSuccess: "+fortResponseMap.toString());
                            Toast.makeText(getActivity(), fortResponseMap.get("response_message"),
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Map<String, String> requestParamsMap, Map<String, String> fortResponseMap) {
                            Toast.makeText(getActivity(), fortResponseMap.get("response_message"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    final String ACCESS_TOKEN =  "qa2s6awTpBNc04Q65T8v";
    final String MERCHANT_IDENTIFIER =  "GjitDYjm";
    final String REQUEST_PHRASE = "PASS" ;


    private void createFORTMobileSDKToken() {
        OkHttpClient client = new OkHttpClient();
        String device_id = FortSdk.getDeviceId(getContext());
        StringBuilder base = new StringBuilder();
        base.append(REQUEST_PHRASE)
                .append("access_code=").append(ACCESS_TOKEN)
                .append("device_id=").append(device_id)
                .append("language=").append(language)
                .append("merchant_identifier=").append(MERCHANT_IDENTIFIER)
                .append("service_command=").append("SDK_TOKEN")
                .append(REQUEST_PHRASE);

        String signature = Utillity.getSHA256Hash(base.toString());
        final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject() ;
        try {
            jsonObject.put("service_command","SDK_TOKEN") ;
            jsonObject.put("access_code",ACCESS_TOKEN) ;
            jsonObject.put("merchant_identifier",MERCHANT_IDENTIFIER) ;
            jsonObject.put("device_id",device_id) ;
            jsonObject.put("language",language) ;
            jsonObject.put("signature",signature) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
        Request request;
        request = new Request.Builder()
                .url("https://sbpaymentservices.payfort.com/FortAPI/paymentApi")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(body)
                .addHeader("Content-Type", "application/json; charset=utf8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String mMessage = response.body().string();
                if (response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(mMessage);
                        Gson gson = new Gson();
                        TokenResponse tokenResponse = new TokenResponse();
                        tokenResponse = gson.fromJson(json.toString(),TokenResponse.class);
                        sdk_token = tokenResponse.getSdk_token();

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
