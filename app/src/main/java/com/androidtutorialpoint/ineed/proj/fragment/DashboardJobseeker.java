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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class DashboardJobseeker extends Fragment implements ImageInputHelper.ImageActionListener{

    LinearLayout ll_savecancel;
    ImageView imgUser, imgCamera;
    Gson gson;
    LoginData loginData;
    String img,language, userId, obj;
    RequestQueue requestQueue;
    private ImageInputHelper imageInputHelper;
    JobseekerProileData jobseekerProileData;
    List<JobseekerProileData.EducationsListBean>educationsListBeans ;
    List<JobseekerProileData.WorksListBean>worksListBeans;

    View view;
    TinyDB tinyDB;
    LinearLayout workLayout,workview, eduLayout, eduView, noticeLayout, toLayout;
    TextView txt_proftitle,txtSave, txtCancle, txt_personal, txtName, txtAge, txtDesignation, txtNationaliaty,
            txtExp, txtCurrentLocation, txtSalary, txtMobile, txtEmail, txtSkills, txtWorkingexp,txtJobTitle, txtCompanyName, txtJobHeading, txtWorkingFrom, txtWrokingTo,
    txtNotice, txtIndustry, txtDepartment, txtTo,txtCoursetype, txtSpecilization, txtInstitute, txtYear, txtDepartMent;
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
        eduLayout = view.findViewById(R.id.edu_exp);
        eduView = view.findViewById(R.id.layout_edu_exp);

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
        txtCancle = view.findViewById(R.id.txt_cancel);
        txtSave = view.findViewById(R.id.txt_save);
        ll_savecancel = (LinearLayout) view.findViewById(R.id.ll_savecancel);
        txt_personal = (TextView) view.findViewById(R.id.txt_objective_heading);



        txt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtobjective.setEnabled(true);
                ll_savecancel.setVisibility(View.VISIBLE);
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj = edtobjective.getText().toString();

                if (!obj.isEmpty()){
                    updateObj();

                } else {
                    Utillity.message(getActivity(),"Please enter company name");
                }
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

    public void updateObj(){
        HashMap<String,String> params=new HashMap<>();
        params.put("objective",obj);
        params.put("user_id",userId);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_OBJECTIVE,params,
                this.successObj(),this.error());
        requestQueue.add(customRequest);
    }

    private Response.Listener<JSONObject> successObj()
    {
        Utillity.showloadingpopup(getActivity());
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Log.d(TAG, "onResponse:data "+response.toString());
                if (response!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (jsonObject.getString("status").equals("true")){
                            edtobjective.setEnabled(false);
                            Utillity.message(getContext(), "Objective updated successfully");
                            ll_savecancel.setVisibility(View.GONE);
                            getProfile();
                        } else {
                            Utillity.message(getContext(), "Connection wrong");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
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
            educationsListBeans = new ArrayList<>();
            worksListBeans = new ArrayList<>();
            workLayout.removeAllViews();
            eduLayout.removeAllViews();

            if (worksListBeans!=null){
                worksListBeans.clear();
            }
            if (!language.isEmpty()){
                HashMap<String,String> params=new HashMap<>();
                params.put("user_id",userId);
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
                                jobseekerProileData = gson.fromJson(response.toString(), JobseekerProileData.class);
                                if (jsonObject.getString("user_fname")!=null && jsonObject.getString("user_fname").length()>1){
                                    txtName.setText(jobseekerProileData.getUser_list().getUser_fname());
                                  }
                                if (jsonObject.getString("user_email").length()>1&&jsonObject.getString("user_email").length()>1){
                                    txtEmail.setText(jobseekerProileData.getUser_list().getUser_email());
                                }
                                if (jsonObject.getString("user_phone").length()>1&&jsonObject.getString("user_phone").length()>1){
                                    txtMobile.setText(jobseekerProileData.getUser_list().getUser_phone());
                                }
                                if (jsonObject.getString("user_age").length()>1&&jsonObject.getString("user_age").length()>1){
                                    txtAge.setText(jobseekerProileData.getUser_list().getUser_age()+" year");
                                }
                                if (!jsonObject.getString("user_nationality").equals("0") &&jsonObject.getString("user_nationality").length()>1){
                                    txtNationaliaty.setText(jobseekerProileData.getUser_list().getUser_nationality()
                                            +", "+" with "+jobseekerProileData.getUser_list().getUser_permitcountry()+" work permit");
                                }
                                if (jsonObject.getString("profile_summary_resumeheadline").length()>1&&jsonObject.getString("profile_summary_resumeheadline").length()>1){
                                    edtobjective.setText(jobseekerProileData.getUser_list().getProfile_summary_resumeheadline());
                                }
                                if (jsonObject.getString("profile_summary_skills").length()>1&&jsonObject.getString("profile_summary_skills").length()>1){
                                    txtSkills.setText(jobseekerProileData.getUser_list().getProfile_summary_skills());
                                }
                                if (jsonObject.getString("profile_summary_currentsalary").length()>1&&jsonObject.getString("profile_summary_currentsalary").length()>1){
                                    txtSalary.setText(jobseekerProileData.getUser_list().getProfile_summary_currentsalary());
                                }
                                if (jsonObject.getString("profile_summary_positions").length()>1&&jsonObject.getString("profile_summary_positions").length()>1){
                                    txtDesignation.setText(jobseekerProileData.getUser_list().getProfile_summary_positions());
                                }
                                if (jsonObject.getString("profile_summary_totalyear").length()>1&&jsonObject.getString("profile_summary_totalyear").length()>1){
                                    txtExp.setText(jobseekerProileData.getUser_list().getProfile_summary_totalyear()+" year");
                                }
                                if (jsonObject.getString("profile_summary_currentcountry").length()>1&&jsonObject.getString("profile_summary_currentcountry").length()>1){
                                    txtCurrentLocation.setText(jobseekerProileData.getUser_list().getProfile_summary_currentcountry());
                                }
                                worksListBeans.addAll(jobseekerProileData.getWorks_list());
                                educationsListBeans.addAll(jobseekerProileData.getEducations_list());

                                if (worksListBeans != null && worksListBeans.size()>0) {
                                    workLayout.setVisibility(View.VISIBLE);
                                    Log.d(TAG, "onResponse: "+worksListBeans.size());
                                    for (int i = 0; i <worksListBeans.size(); i++) {
                                        workview = (LinearLayout) View.inflate(getContext(), R.layout.work_experience_view, null);
                                        txtJobHeading = workview.findViewById(R.id.txt_work_experience_heading);
                                        txtJobTitle = workview.findViewById(R.id.txt_work_experienceJobtitle);
                                        txtCompanyName = workview.findViewById(R.id.txt_CompanyName);
                                        txtWorkingexp = workview.findViewById(R.id.txt_work_experienceWorkFrom);
//                                        txtWorkingFrom = workview.findViewById(R.id.txt_work_experienceWorkFrom);
                                        txtNotice = workview.findViewById(R.id.txt_work_experienceNotice);
                                        txtIndustry = workview.findViewById(R.id.txt_work_experiencetxtIndustry);
                                        txtDepartment = workview.findViewById(R.id.txt_work_experiencetxtDepartment);
                                        txtTo = workview.findViewById(R.id.txt_work_experienceTo);
                                        noticeLayout = workview.findViewById(R.id.notice_layout);
                                        toLayout = workview.findViewById(R.id.to_layout);
                                        Collections.sort(worksListBeans, new Comparator<JobseekerProileData.WorksListBean>() {
                                            @Override
                                            public int compare(JobseekerProileData.WorksListBean worksListBean, JobseekerProileData.WorksListBean t1) {
                                                return worksListBean.getJobseeker_workexp_employertype().compareToIgnoreCase(t1.getJobseeker_workexp_employertype());
                                            }
                                        });

                                        if (worksListBeans.get(i).getJobseeker_workexp_employertype().equals("c")){
                                            txtJobHeading.setText("Current");
                                            noticeLayout.setVisibility(View.VISIBLE);
//                                            toLayout.setVisibility(View.GONE);
                                               txtNotice.setText(jobseekerProileData.getWorks_list().get(i).getJobseeker_workexp_noticeperiod() + " days");
                                        } else {
                                            txtJobHeading.setText("Previous");
//                                            noticeLayout.setVisibility(View.GONE);
//                                            toLayout.setVisibility(View.VISIBLE);
//                                            txtTo.setText(" 12/2/2019");
                                        }
                                        txtIndustry.setText(worksListBeans.get(i).getJobseeker_workexp_companyindus());
                                        txtWorkingexp.setText(worksListBeans.get(i).getJobseeker_workexp_totalyear()+" year");
                                        txtJobTitle.setText(worksListBeans.get(i).getPositions());
                                        txtCompanyName.setText(worksListBeans.get(i).getJobseeker_workexp_companyname());
                                        txtDepartment.setText(worksListBeans.get(i).getJobseeker_workexp_dept());
                                        workLayout.addView(workview);
                                    }
                                } else {
                                    workLayout.setVisibility(View.GONE);
                                }

                                if (educationsListBeans!=null && educationsListBeans.size()>0){
                                    Log.d(TAG, "onResponse: "+jobseekerProileData.getWorks_list());
                                    eduLayout.setVisibility(View.VISIBLE);
                                    int x =jobseekerProileData.getEducations_list().size();
                                    for (int i=0; i<x;i++){
                                        eduView = (LinearLayout) View.inflate(getContext(), R.layout.education_view, null);
                                        txtCoursetype = eduView.findViewById(R.id.txt_edu_course_title);
                                        txtSpecilization = eduView.findViewById(R.id.txt_Specialization);
                                        txtInstitute = eduView.findViewById(R.id.txt_institute);
                                        txtYear = eduView.findViewById(R.id.txt_edu_year);
                                        txtYear.setText(jobseekerProileData.getEducations_list().get(i).getJobseeker_education_year());
                                        txtCoursetype.setText(jobseekerProileData.getEducations_list().get(i).getJobseeker_education_course());
                                        txtInstitute.setText(jobseekerProileData.getEducations_list().get(i).getJobseeker_education_institute());
                                        txtSpecilization.setText(jobseekerProileData.getEducations_list().get(i).getJobseeker_education_special());

                                        eduLayout.addView(eduView);

                                    }

                                } else {
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


}
