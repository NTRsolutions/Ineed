package com.androidtutorialpoint.ineed.proj.activities;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.AppGlobal;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.fragment.CompleteSignup2EmpFragment;
import com.androidtutorialpoint.ineed.proj.fragment.CompleteSignup2JobFragment;
import com.androidtutorialpoint.ineed.proj.fragment.Signup2EmpFragment;
import com.androidtutorialpoint.ineed.proj.fragment.Signup2JobFragment;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.TokenResponse;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
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

public class DialogActivity extends AppCompatActivity {
    ActionBar actionBar;
    TinyDB tinyDB;
    AppGlobal appGlobal = AppGlobal.getInstancess();
    private String  language,transaction_id, email, userId  ;
    public FortCallBackManager fortCallback;
    Gson gson = new Gson();
    RequestQueue requestQueue;
    LoginData loginData = new LoginData();
    public static String user_type, price,sdk_token, package_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        this.setFinishOnTouchOutside(false);
        appGlobal.context=getApplicationContext();
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        tinyDB = new TinyDB(getApplicationContext());
        String data = tinyDB.getString("login_data");
        loginData= gson.fromJson(data, LoginData.class);
        language=tinyDB.getString("language_id");
        user_type = loginData.getUser_detail().getUser_type();
        userId= loginData.getUser_detail().getUser_id();
        email= loginData.getUser_detail().getUser_email();
        if(loginData.getUser_detail().getUser_type().equals("2"))     // Checking is it Jobseeker
        {
            CompleteSignup2JobFragment istFragment=new CompleteSignup2JobFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.dialog_fragment,istFragment).addToBackStack(null).commit();
        }
        else if(loginData.getUser_detail().getUser_type().equals("1"))    //Checking is it Employer
        {
            CompleteSignup2EmpFragment ndfragment=new CompleteSignup2EmpFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.dialog_fragment,ndfragment).addToBackStack(null).commit();
        }
        setuptoolbar();
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
//            actionBar.setHomeAsUpIndicator(R.drawable.back);
//            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSDKToken();
    }

    private void requestSDKToken() {
        createFORTMobileSDKToken();
    }

    private void requestOperation(String command, String sdk_token) {
        final String ECI = "ECOMMERCE";
        final String CUSTOMER_EMAIL = email;
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
        Log.d(TAG, "requestOperation: "+requestMap);

        /*requestMap.put("payment_option","AMEX");
        requestMap.put("eci",ECI);
        requestMap.put("order_description",command);
        requestMap.put("customer_ip",command);
        requestMap.put("customer_name",sdk_token);
        if (mobile!=null){
            requestMap.put("phone_number",mobile);
        }
        requestMap.put("settlement_reference",command);
        requestMap.put("return_url",command);*/

        fortRequest.setRequestMap(requestMap);
        fortCallback = FortCallback.Factory.create();
        boolean showLoading= true;
        try {
            FortSdk.getInstance().registerCallback(this, fortRequest,FortSdk.ENVIRONMENT.TEST,
                    5, fortCallback,showLoading, new FortInterfaces.OnTnxProcessed() {
                        @Override
                        public void onCancel(Map<String, String> requestParamsMap, Map<String, String> responseMap) {
                            Log.d(TAG, "onCancel: "+ responseMap.get("response_message"));
                            Utillity.message(getApplicationContext(), "Connection Error ");
                        }
                        @Override
                        public void onSuccess(Map<String, String> requestParamsMap, Map<String, String> fortResponseMap) {
                            Log.d(TAG, "onSuccess: "+fortResponseMap.toString());
                            Log.d(TAG, "onSuccess: "+fortResponseMap.get("response_message"));
                            transaction_id = fortResponseMap.get("fort_id");
                            Utillity.message(getApplicationContext(), "Payment successful ");
                        }
                        @Override
                        public void onFailure(Map<String, String> requestParamsMap, Map<String, String> fortResponseMap) {
                            Toast.makeText(getApplicationContext(), fortResponseMap.get("response_message"),
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
        String device_id = FortSdk.getDeviceId(getApplicationContext());
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
//                for test URL
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fortCallback.onActivityResult(requestCode,resultCode,data);
    }

    public void getSDKToken() {
        requestSDKToken() ;
    }

    public void requestAuthorization() {
        requestOperation("AUTHORIZATION" ,sdk_token) ;
    }

    public void requestPurchase(View view) {
        requestOperation("PURCHASE" ,sdk_token) ;
    }



    public void signUp(View view){
        if (!transaction_id.isEmpty()){

            HashMap<String,String> params=new HashMap<>();
            params.put("user_type",user_type);
            params.put("language",language);
            params.put("transaction_id",transaction_id);
            params.put("package_id",package_id);
            params.put("user_id", userId);
            CustomRequest customRequest=new CustomRequest(com.android.volley.Request.Method.POST, ApiList.MAKE_PAYMENT,params,
                    this.success(),this.error());
            requestQueue.add(customRequest);
        } else {
            Utillity.message(getApplicationContext(), "Please make payment before signup ");
        }
    }

    private com.android.volley.Response.Listener<JSONObject> success()
    {
        Utillity.showloadingpopup(this);
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                if (response!=null){
                    try {
                        if (response.getString("status").equals("true")){
                            Utillity.message(getApplicationContext(), "Process completed");
                            tinyDB.remove("login_data");
                            appGlobal.setLoginData(response.toString());
                            startActivity(new Intent(DialogActivity.this,HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }
                        else {
                            Utillity.message(getApplicationContext(),"Something went wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private com.android.volley.Response.ErrorListener error()
    {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(getApplicationContext(), getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();

            }
        };
    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>1)
        {
            getSupportFragmentManager().popBackStack();
        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout");
            builder.setMessage("Do you want to Logout?");
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    tinyDB.remove("login_data");
                    finish();
                }
            });
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
            //super.onBackPressed();
        }
    }
}
