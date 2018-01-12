package com.androidtutorialpoint.ineed.proj.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.HomeActivity;
import com.androidtutorialpoint.ineed.proj.activities.WorkExperience;
import com.androidtutorialpoint.ineed.proj.models.ImageInputHelper;
import com.androidtutorialpoint.ineed.proj.models.JobSeekerPackage;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.ProfileDetailMOdel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.androidtutorialpoint.ineed.proj.activities.DialogActivity.user_type;
import static com.androidtutorialpoint.ineed.proj.activities.HomeActivity.toolbar;
import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class DashboardJobseeker extends Fragment implements ImageInputHelper.ImageActionListener, View.OnClickListener {
    TextView txt_proftitle, txt_personal,txt_addwk;
    EditText etEmail, etcontact, etcompany, etdesignation, etexperience, etresume, etdob, etgender, etlocation,
            etskills;
    LinearLayout ll_savecancel;
    ImageView imgUser, imgCamera;
    Gson gson;
    LoginData loginData;
    String img,language, userId;
    RequestQueue requestQueue;
    private ImageInputHelper imageInputHelper;
    ProfileDetailMOdel profileDetailMOdel;
    View view;
    TinyDB tinyDB;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile, container, false);

        gson = new Gson();
        loginData = new LoginData();
        profileDetailMOdel = new ProfileDetailMOdel();
        tinyDB = new TinyDB(getContext());
        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Profile");

//        find id
        imgCamera = (ImageView) view.findViewById(R.id.edt_img_camera);
        imgUser = (ImageView) view.findViewById(R.id.edt_img_profile);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etcontact = (EditText) view.findViewById(R.id.et_contact);
        etcompany = (EditText) view.findViewById(R.id.et_company);
        etdesignation = (EditText) view.findViewById(R.id.et_designation);
        etexperience = (EditText) view.findViewById(R.id.et_experience);
        etresume = (EditText) view.findViewById(R.id.et_resume);
        etdob = (EditText) view.findViewById(R.id.et_dob);
        etgender = (EditText) view.findViewById(R.id.et_gender);
        etlocation = (EditText) view.findViewById(R.id.et_loc);
        etskills = (EditText) view.findViewById(R.id.et_skills);

        ll_savecancel = (LinearLayout) view.findViewById(R.id.ll_savecancel);
        txt_proftitle = (TextView) view.findViewById(R.id.txt_proftitle);
        txt_personal = (TextView) view.findViewById(R.id.txt_objective_heading);
        txt_addwk=view.findViewById(R.id.btnAddwk);
        txt_addwk.setOnClickListener(this);
        txt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(getApplication(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });

        txt_personal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickonDrawable(v, event);
                return false;
            }
        });

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo"))
                        {
                            imageInputHelper.takePhotoWithCamera();

                        }
                        else if (options[item].equals("Choose from Gallery"))
                        {
                            imageInputHelper.selectImageFromGallery();

                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        toolbar.setTitle("Dash board");
        etEmail.setEnabled(false);
        etcontact.setEnabled(false);
        etcompany.setEnabled(false);
        etdesignation.setEnabled(false);
        etexperience.setEnabled(false);
        etresume.setEnabled(false);
        etdob.setEnabled(false);
        etgender.setEnabled(false);
        etlocation.setEnabled(false);
        etskills.setEnabled(false);

        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userId = loginData.getUser_detail().getUser_id();
        language = tinyDB.getString("language_id");
        getProfile();

    }

    boolean clickonDrawable(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (txt_personal.getRight() - txt_personal.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                etEmail.setEnabled(true);
                etcontact.setEnabled(true);
                etcompany.setEnabled(true);
                etdesignation.setEnabled(true);
                etexperience.setEnabled(true);
                etresume.setEnabled(true);
                etdob.setEnabled(true);
                etgender.setEnabled(true);
                etlocation.setEnabled(true);
                etskills.setEnabled(true);
                ll_savecancel.setVisibility(View.VISIBLE);
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        imageInputHelper.requestCropImage(uri, 800, 450, 16, 9);
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        imageInputHelper.requestCropImage(uri, 800, 450, 16, 9);

    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            // getting bitmap from uri
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

            // showing bitmap in image view
//            imgUser.setImageBitmap(bitmap);
            Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(imgUser);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProfile(){
          /*  if (jobSeekerPackage!=null){
                jobSeekerPackage.clear();
            }*/
            if (!language.isEmpty()){
                HashMap<String,String> params=new HashMap<>();
                params.put("user_id",userId);
                params.put("language",language);
                CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.VIEW_JOBSEEKER_PROFILE,params,
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
                    Log.d(TAG, "onResponse:data "+response.toString());
                    if (response!=null){
                        try {
                            if (!response.getString("status").equals("false")){
                                profileDetailMOdel = gson.fromJson(response.toString(), ProfileDetailMOdel.class);
                                if (profileDetailMOdel.getProfile_detail() != null) {
                                    etEmail.setText(profileDetailMOdel.getProfile_detail().getUser_email());
                                    etcontact.setText(profileDetailMOdel.getProfile_detail().getUser_phone());
                                    etcompany.setText(profileDetailMOdel.getProfile_detail().getJobseeker_workexp_companyname());
                                    etdesignation.setText(profileDetailMOdel.getProfile_detail().getJobseeker_workexp_dept());
                                    etexperience.setText(profileDetailMOdel.getProfile_detail().getJobseeker_workexp_totalexp());
                                    etresume.setText(profileDetailMOdel.getProfile_detail().getJobseeker_workexp_resume());
                                    etdob.setText(profileDetailMOdel.getProfile_detail().getUser_dob());
                                    etgender.setText(profileDetailMOdel.getProfile_detail().getUser_gender());
                                    etlocation.setText(profileDetailMOdel.getProfile_detail().getUser_address());
                                    etskills.setText(profileDetailMOdel.getProfile_detail().getJobseeker_workexp_job_title());

                                }
                            } else {
                                Utillity.message(getContext(), "No data found");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAddwk:
                startActivity(new Intent(getActivity(), WorkExperience.class));
                break;

        }
    }
}
