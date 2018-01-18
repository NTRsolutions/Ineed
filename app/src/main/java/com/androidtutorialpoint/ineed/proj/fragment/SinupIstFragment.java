package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.Spanned;
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
import com.androidtutorialpoint.ineed.proj.Utils.AppGlobal;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.StatusModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.HashMap;

import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.email;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image1;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image2;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.image3;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.selection;
import static com.androidtutorialpoint.ineed.proj.activities.SignUpActivity.userid;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SinupIstFragment extends Fragment {
    TextInputEditText et_Username,et_Email,et_Password,et_ConfirmPassword;
    View view;
    Button bt_next;
    TextView txtlogin;
    String Username,Email,Password,ConfirmPassword;
    TinyDB sharpref;
    public static StatusModel statusModel=null;
    AppGlobal appGlobal = AppGlobal.getInstancess();
    RequestQueue requestQueue;
    String language;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.signup1frag,container,false);
        sharpref=new TinyDB(getActivity());
        appGlobal.context=getContext();
        language=sharpref.getString("language_id");
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        et_Username=view.findViewById(R.id.tiet_username);
        et_Email=view.findViewById(R.id.tiet_emailadd);
        et_Password=view.findViewById(R.id.tiet_password);
        et_ConfirmPassword=view.findViewById(R.id.tiet_confipassword);
        txtlogin = (TextView) view.findViewById(R.id.txt_sign_Login1);
        if(language!=null && !language.isEmpty()) {
            if (language.equals("en")) {
                String noAccount = "HAVE AN ACCOUNT? LOGIN";
                int i = noAccount.indexOf("LO");
                int j = noAccount.indexOf("IN");
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

                spannable.setSpan(clickableSpan, i, j+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (language.equals("ar")) {
                String noAccount = "هل لديك حساب بالفعل؟ اسمك";
                int i = noAccount.indexOf(" ا");
                int j = noAccount.indexOf("مك");
                txtlogin.setMovementMethod(LinkMovementMethod.getInstance());
                txtlogin.setText(noAccount, TextView.BufferType.SPANNABLE);
                Spannable spannable = (Spannable) txtlogin.getText();
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(Color.BLACK);
                        ds.setUnderlineText(false);
                    }
                };
                spannable.setSpan(clickableSpan, i, j+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }
        bt_next=(Button) view.findViewById(R.id.bt_next);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if (selection.equals("2")){
                    Signup2JobFragment istFragment=new Signup2JobFragment();
                    getFragmentManager().beginTransaction().replace(R.jobseekerid.flContent,istFragment)
                            .addToBackStack("signupstep2").commit();
                } else {
                    Signup2EmpFragment istFragment=new Signup2EmpFragment();
                    getFragmentManager().beginTransaction().replace(R.jobseekerid.flContent,istFragment)
                            .addToBackStack("signupstep2").commit();
                }*/

//                hit api for sign up
                hitApi();
            }
        });

        return view;

    }

    private void hitApi() {
        if(Utillity.isNetworkConnected(getContext()))
        {
            Username=et_Username.getText().toString().trim();
            email=et_Email.getText().toString().trim();
            Password=et_Password.getText().toString().trim();
            ConfirmPassword=et_ConfirmPassword.getText().toString().trim();
            if(!Username.isEmpty()&& !email.isEmpty() && !Password.isEmpty() && !ConfirmPassword.isEmpty())
            {
                if(Utillity.CheckEmail(email))
                {
                    if (Password.equals(ConfirmPassword))
                    {
                        /*Putting parameters in hashmap*/
                        HashMap<String,String> params=new HashMap<>();
                        params.put("username",Username);
                        params.put("email",email);
                        params.put("password",Password);
                        params.put("user_type", selection);/*1=for employer,2=for jobseeker*/

                        /*Sending Custom Request with */
                        CustomRequest request=new CustomRequest(Request.Method.POST, ApiList.SIGNUP,params,
                                this.sucesslistener(),this.errorlistener());
                        requestQueue.add(request);


                    }
                    else
                    {
                        Utillity.message(getContext(),getResources().getString(R.string.confirequal));
                    }
                }
                else
                {
                    Utillity.message(getContext(),getResources().getString(R.string.validemail));
                }
            }
            else
            {
                Utillity.message(getContext(),getResources().getString(R.string.fieldsmeand));
            }
        }
        else
        {
            Snackbar snackbar= Snackbar.make(view.findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarView=snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        image1.setImageResource(R.drawable.ic_oned);
        image2.setImageResource(R.drawable.ic_two);
        image3.setImageResource(R.drawable.ic_three);
    }


    private Response.Listener<JSONObject> sucesslistener()
    {
        Utillity.showloadingpopup(getActivity());
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Gson gson=new Gson();
                try {
                    statusModel=new StatusModel();
                    String message;
                    statusModel=gson.fromJson(response.toString(),StatusModel.class);
                    boolean status=statusModel.isStatus();
                    message=response.getString("msg");
                    if(status==true)
                    {
//                        appGlobal.setLoginData(response.toString());
                        userid = statusModel.getUser_data().getUser_id();
                        Utillity.message(getActivity(),""+message);
                        if(statusModel.getUser_data().getUser_type().equals("2"))     // Checking is it Jobseeker
                        {
                            Signup2JobFragment istFragment=new Signup2JobFragment();
                            getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.flContent,istFragment).addToBackStack(null).commit();
                        }
                        else if(statusModel.getUser_data().getUser_type().equals("1"))    //Checking is it Employer
                        {
                            Signup2EmpFragment ndfragment=new Signup2EmpFragment();
                            getActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.flContent,ndfragment).addToBackStack(null).commit();
                        }

                    } else {
                        Utillity.message(getActivity(),message);
                        Utillity.hidepopup();

                    }

                }
                catch (Exception e)
                {

                }

            }
        };
    }
    private Response.ErrorListener errorlistener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utillity.message(getActivity(),""+"Connection Error");
                Log.d("Response",""+error);
            }
        };
    }
}
