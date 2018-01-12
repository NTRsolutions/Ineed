package com.androidtutorialpoint.ineed.proj.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.HomeActivity;
import com.androidtutorialpoint.ineed.proj.activities.WorkExperience;
import com.androidtutorialpoint.ineed.proj.models.ImageInputHelper;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.JobseekerProileData;
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
import java.io.IOException;
import java.util.HashMap;

import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class DashboardJobseeker extends Fragment implements ImageInputHelper.ImageActionListener, View.OnClickListener {
    EditText etEmail, etcontact, etcompany, etdesignation, etexperience, etresume, etdob, etgender, etlocation,
            etskills;

    LinearLayout ll_savecancel;
    ImageView imgUser, imgCamera;
    Gson gson;
    LoginData loginData;
    String img,language, userId;
    RequestQueue requestQueue;
    private ImageInputHelper imageInputHelper;
    JobseekerProileData jobseekerProileData;
    View view;
    TinyDB tinyDB;
    LinearLayout workLayout,workview, eduLayout;
    TextView txt_proftitle,txt_addwk, txt_personal, txtName, txtAge, txtDesignation, txtNationaliaty,txtWorkPermit,
            txtExp, txtCurrentLocation, txtSalary, txtMobile, txtEmail, txtSkills;
    EditText edtobjective;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_profile, container, false);

        gson = new Gson();
        loginData = new LoginData();
        jobseekerProileData = new JobseekerProileData();
        tinyDB = new TinyDB(getContext());
        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        workLayout = (LinearLayout)view.findViewById(R.id.work_exp);
        workview = view.findViewById(R.id.layout_work_exp);

//        find id
        imgCamera = (ImageView) view.findViewById(R.id.edt_img_camera);
        imgUser = (ImageView) view.findViewById(R.id.edt_img_profile);
        txtName = view.findViewById(R.id.jobseerker_profileName);
        txtAge = view.findViewById(R.id.jobseeker_profileAge);
        txtDesignation = view.findViewById(R.id.jobseeker_profileDesi);
        txtNationaliaty = view.findViewById(R.id.jobseeker_profileNationality);
//        txtWorkPermit = view.findViewById(R.id.jobseerker_profileName);
        txtExp = view.findViewById(R.id.jobseeker_profileExp);
        txtCurrentLocation = view.findViewById(R.id.jobseeker_profileLoca);
        txtSalary = view.findViewById(R.id.jobseeker_profileSalary);
        txtMobile = view.findViewById(R.id.jobseerker_profileMobile);
        txtEmail = view.findViewById(R.id.jobseerker_profileEmail);
        txtSkills = view.findViewById(R.id.txt_skills_value);
        edtobjective = view.findViewById(R.id.txt_objective);

        eduLayout = view.findViewById(R.id.layout_edu_exp);

        ll_savecancel = (LinearLayout) view.findViewById(R.id.ll_savecancel);
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
                params.put("user_id","29");
                CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_PROFILE,params,
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
                        Log.d(TAG, "onResponse: "+response.toString());
                        try {
                            if (!response.getString("status").equals("false")){
                                JSONObject jsonObject = response.getJSONObject("user_list");
                                if (jsonObject.getString("user_fname")!=null && jsonObject.getString("user_fname").length()>1){
                                    jobseekerProileData = gson.fromJson(response.toString(), JobseekerProileData.class);
                                    txtName.setText(jobseekerProileData.getUser_list().getUser_fname());
                                    txtAge.setText(" " +jobseekerProileData.getUser_list().getUser_age()+" year");
                                    txtDesignation.setText(jobseekerProileData.getUser_list().getProfile_summary_positions());
                                    txtNationaliaty.setText(jobseekerProileData.getUser_list().getUser_nationality()
                                            +", "+" with "+jobseekerProileData.getUser_list().getUser_permitcountry()+" work permit");
                                    txtExp.setText(jobseekerProileData.getUser_list().getProfile_summary_totalyear()+" year");
                                    txtCurrentLocation.setText(jobseekerProileData.getUser_list().getProfile_summary_currentcountry());
                                    txtSalary.setText(jobseekerProileData.getUser_list().getProfile_summary_currentsalary());
                                    txtMobile.setText(jobseekerProileData.getUser_list().getUser_phone());
                                    txtEmail.setText(jobseekerProileData.getUser_list().getUser_email());
                                    txtSkills.setText(jobseekerProileData.getUser_list().getProfile_summary_skills());
                                    edtobjective.setText(jobseekerProileData.getUser_list().getProfile_summary_resumeheadline());
                                }

                                if (jobseekerProileData.getWorks_list() != null && jobseekerProileData.getWorks_list().size()>1) {
                                    Log.d(TAG, "onResponse: sssd"+jobseekerProileData.getWorks_list().size());
                                    workLayout.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < jobseekerProileData.getWorks_list().size(); i++) {
                                        workview = (LinearLayout) View.inflate(getContext(), R.layout.work_experience_view, null);
                                        ((TextView) workview.findViewById(R.id.txt_work_experience_heading))
                                                .setText(jobseekerProileData.getEducations_list().get(i).getJobseeker_education_id()+ (i + 1));
                                        workLayout.addView(workview);
                                    }


                                } else {
                                    Log.d(TAG, "onResponse: "+"No data");
                                    workLayout.setVisibility(View.GONE);
                                }

                                if (jobseekerProileData.getWorks_list()!=null && jobseekerProileData.getWorks_list().size()>1){
                                    Log.d(TAG, "onResponse: "+jobseekerProileData.getEducations_list());
                                    eduLayout.setVisibility(View.VISIBLE);
                                } else {
                                    Log.d(TAG, "onResponse: "+"no data");
                                    eduLayout.setVisibility(View.GONE);
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
