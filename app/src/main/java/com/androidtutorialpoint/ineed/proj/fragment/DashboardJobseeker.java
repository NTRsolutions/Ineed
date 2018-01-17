package com.androidtutorialpoint.ineed.proj.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.activities.EducationAdd;
import com.androidtutorialpoint.ineed.proj.activities.HomeActivity;
import com.androidtutorialpoint.ineed.proj.activities.PersonalAdd;
import com.androidtutorialpoint.ineed.proj.activities.UploadResumeActivity;
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
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.bouncycastle.util.encoders.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class DashboardJobseeker extends Fragment implements ImageInputHelper.ImageActionListener, View.OnClickListener {

    TextView txtaddeduc;
    LinearLayout ll_savecancel;
    ImageView imgUser, imgCamera,imgedit;
    Gson gson;
    LoginData loginData;
    String img,language, userId, obj, skills, name=" ", age = " ", desig = " ", nationality =  " ",
            exp = " ", loc = " ", salary = " ", mobile =  " ", workpermit,workpermitid;
    RequestQueue requestQueue;
    private ImageInputHelper imageInputHelper;
    JobseekerProileData jobseekerProileData;
    List<JobseekerProileData.EducationsListBean>educationsListBeans ;
    List<JobseekerProileData.WorksListBean>worksListBeans;
    View view;
    TinyDB tinyDB;
    LinearLayout workLayout,workview, eduLayout, eduView, skillsLayout, noticeLayout, toLayout;
    TextView txtResumeView, txtResumeUpload, txtSkillsEditHeading, txtSaveObj, txtCancle, txt_personal, txtName, txtAge, txtDesignation, txtNationaliaty,
            txtExp, txtCurrentLocation, txtSalary,txtWorkSalary, txtJobtype, txtMobile, txtEmail, txtWorkingexp,txtJobTitle, txtCompanyName, txtJobHeading, txtWorkingFrom, txtWrokingTo,
    txtNotice, txtIndustry, txtDepartment,txtSaveSkills, txtCancelSkill, txtTo,txtCoursetype, txtSpecilization, txtInstitute, txtYear, txtDepartMent,txt_addwk;
    EditText edtobjective, edtSkills;

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

        workLayout = view.findViewById(R.id.work_exp);
        skillsLayout = view.findViewById(R.id.ll_savecancelskills);
        workview = view.findViewById(R.id.layout_work_exp);
        eduLayout = view.findViewById(R.id.edu_exp);
        eduView = view.findViewById(R.id.layout_edu_exp);
        txtResumeView = view.findViewById(R.id.txt_resume_view);
        txtResumeUpload = view.findViewById(R.id.txt_resume_upload);

//        find id
        imgCamera =  view.findViewById(R.id.edt_img_camera);
        imgUser =  view.findViewById(R.id.edt_img_profile);
        imgedit=view.findViewById(R.id.edt_personal_img_edit);
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
        edtobjective = view.findViewById(R.id.txt_objective);
        edtSkills = view.findViewById(R.id.txt_skills_value);
        txtCancle = view.findViewById(R.id.txt_cancel);
        txtSaveObj = view.findViewById(R.id.txt_save);
        txtaddeduc = view.findViewById(R.id.btnAddEducation);
        txtSkillsEditHeading = view.findViewById(R.id.txt_skills_heading);
        txtSaveSkills = view.findViewById(R.id.txt_saveskills);
        txtCancelSkill = view.findViewById(R.id.txt_cancelskills);
        ll_savecancel = (LinearLayout) view.findViewById(R.id.ll_savecancel);
        txt_personal = (TextView) view.findViewById(R.id.txt_objective_heading);
        txt_addwk=view.findViewById(R.id.btnAddwk);


//        set onclick listener
        txtaddeduc.setOnClickListener(this);
        txtSkillsEditHeading.setOnClickListener(this);
        imgedit.setOnClickListener(this);
        txtSaveSkills.setOnClickListener(this);
        txtCancelSkill.setOnClickListener(this);

        txt_addwk.setOnClickListener(this);
        txt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtobjective.setEnabled(true);
                ll_savecancel.setVisibility(View.VISIBLE);
            }
        });

        if (imgUser.getDrawable()!=null){
            Drawable drawable= imgUser.getDrawable();
            BitmapDrawable bitmapDrawable = ((BitmapDrawable) drawable);
            Bitmap bitmap = bitmapDrawable .getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
            img = Utillity.BitMapToString(bitmap);
        }


        txtResumeUpload.setOnClickListener(this);
        txtCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtobjective.setEnabled(false);
                ll_savecancel.setVisibility(View.GONE);
                getProfile();
            }
        });
        txtSaveObj.setOnClickListener(new View.OnClickListener() {
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

    private Response.Listener<JSONObject> successObj() {
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
//        set text
        txtEmail.setText(loginData.getUser_detail().getUser_email());

        language = tinyDB.getString("language_id");
        Utillity.checkCameraPermission(getActivity());
        getProfile();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
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

            img = Utillity.BitMapToString(bitmap);
            uploading(img);

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
                customRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(customRequest);
            } else {
                Utillity.message(getContext(),getResources().getString(R.string.language_select));
            }
        }

    private Response.Listener<JSONObject> success() {

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
                                    name = jobseekerProileData.getUser_list().getUser_fname();
                                  }

                                if (jsonObject.getString("user_phone").length()>1&&jsonObject.getString("user_phone").length()>1){
                                    txtMobile.setText(jobseekerProileData.getUser_list().getUser_phone());
                                    mobile = jobseekerProileData.getUser_list().getUser_phone();
                                }
                                if (jsonObject.getString("user_age").length()>1&&jsonObject.getString("user_age").length()>1){
                                    txtAge.setText(" "+jobseekerProileData.getUser_list().getUser_age()+" year");
                                    age = jobseekerProileData.getUser_list().getUser_age();
                                }
                                if (!jsonObject.getString("user_nationality").equals("0") &&jsonObject.getString("user_nationality").length()>1){
                                    nationality  = jobseekerProileData.getUser_list().getUser_nationality();
                                    txtNationaliaty.setText(jobseekerProileData.getUser_list().getUser_nationality()
                                            +", "+" with "+jobseekerProileData.getUser_list().getUser_permitcountry()+" work permit");
                                    workpermit = jobseekerProileData.getUser_list().getUser_workpermit();
                                    workpermitid = jobseekerProileData.getUser_list().getUser_permitcountry();
                                }
                                if (jsonObject.getString("profile_summary_resumeheadline").length()>1&&jsonObject.getString("profile_summary_resumeheadline").length()>1){
                                    edtobjective.setText(jobseekerProileData.getUser_list().getProfile_summary_resumeheadline());
                                }
                                if (jsonObject.getString("profile_summary_skills").length()>1&&jsonObject.getString("profile_summary_skills").length()>1){
                                    edtSkills.setText(jobseekerProileData.getUser_list().getProfile_summary_skills());
                                }
                                if (jsonObject.getString("profile_summary_currentsalary")!=null){
                                    Log.d(TAG, "onResponse: "+jobseekerProileData.getUser_list().getProfile_summary_currentsalary());
                                    txtSalary.setText(jobseekerProileData.getUser_list().getProfile_summary_currentsalary());
                                    salary = jobseekerProileData.getUser_list().getProfile_summary_currentsalary();
                                }
                                if (jsonObject.getString("profile_summary_positions").length()>1&&jsonObject.getString("profile_summary_positions").length()>1){
                                    txtDesignation.setText(jobseekerProileData.getUser_list().getProfile_summary_positions());
                                    desig = jobseekerProileData.getUser_list().getProfile_summary_positions();
                                }
                                if (jsonObject.getString("profile_summary_totalyear").length()>1&&jsonObject.getString("profile_summary_totalyear").length()>1){
                                    txtExp.setText(jobseekerProileData.getUser_list().getProfile_summary_totalyear()+" year");
                                    exp = jobseekerProileData.getUser_list().getProfile_summary_totalyear();
                                }
                                if (jsonObject.getString("profile_summary_currentcountry").length()>1&&jsonObject.getString("profile_summary_currentcountry").length()>1){
                                    txtCurrentLocation.setText(jobseekerProileData.getUser_list().getProfile_summary_currentcountry());
                                    loc = jobseekerProileData.getUser_list().getProfile_summary_currentcountry_id();
                                }
                                if (jsonObject.getString("user_image")!=null && jsonObject.getString("user_image").length()>0){
                                    String url = ApiList.IMG_BASE+jobseekerProileData.getUser_list().getUser_image();
                                    GetImage task = new GetImage();
                                    // Execute the task
                                    task.execute(new String[] { url });
                                } else {
                                    Glide.with(getContext()).load(R.drawable.gfgf)
                                            .apply(RequestOptions.circleCropTransform()).into(imgUser);
                                }
                                if (jsonObject.getString("user_resume")!=null){
                                    final String text = ApiList.IMG_BASE+jsonObject.getString("user_resume");
                                    txtResumeView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(text));
                                            startActivity(i);
                                        }
                                    });
                                }

                                txtResumeView.setMovementMethod(LinkMovementMethod.getInstance());
                                worksListBeans.addAll(jobseekerProileData.getWorks_list());
                                educationsListBeans.addAll(jobseekerProileData.getEducations_list());

                                if (worksListBeans != null && worksListBeans.size()>0) {
                                    workLayout.setVisibility(View.VISIBLE);
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
                                        txtWorkSalary = workview.findViewById(R.id.txt_work_experienceSalary);
                                        txtJobtype = workview.findViewById(R.id.txt_work_experienceJobType);

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
                                     //   txtWorkSalary.setText(worksListBeans.get(i).getJobseeker_workexp_companyindus());

                                        workLayout.addView(workview);

                                        int count = workLayout.getChildCount();
                                        View v = null;
                                        for(int k=0; k<count; k++) {
                                            v = workLayout.getChildAt(k);
                                            final TextView txtJobHeading = v.findViewById(R.id.txt_work_experience_heading);
                                            final TextView txtJobTitle = v.findViewById(R.id.txt_work_experienceJobtitle);
                                            final TextView txtCompanyName = v.findViewById(R.id.txt_CompanyName);
                                            final TextView txtWorkingexp = v.findViewById(R.id.txt_work_experienceWorkFrom);
//                                        txtWorkingFrom = workview.findViewById(R.id.txt_work_experienceWorkFrom);
                                            final TextView   txtNotice = v.findViewById(R.id.txt_work_experienceNotice);
                                            final TextView  txtIndustry = v.findViewById(R.id.txt_work_experiencetxtIndustry);
                                            final TextView  txtDepartment = v.findViewById(R.id.txt_work_experiencetxtDepartment);
                                            final TextView  txtTo = v.findViewById(R.id.txt_work_experienceTo);

                                            final int finalK = k;
                                            txtJobHeading.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(getActivity(), WorkExperience.class);
                                                    intent.putExtra("jobtitle", txtJobTitle.getText().toString());
                                                    intent.putExtra("compName", txtCompanyName.getText().toString());
                                                    intent.putExtra("type", txtJobHeading.getText());
                                                    intent.putExtra("exp", txtExp.getText().toString());
                                                    intent.putExtra("indu", txtIndustry.getText().toString());
                                                    intent.putExtra("depart", txtDepartment.getText().toString());
                                                    intent.putExtra("title", txtJobTitle.getText().toString());
                                                    intent.putExtra("notice", txtNotice.getText().toString());
                                                    intent.putExtra("id", worksListBeans.get(finalK).getJobseeker_workexp_id());
                                                    startActivity(intent);
                                                }
                                            });

                                            //do something with your child element
                                        }
                                    }
                                } else {
                                    workLayout.setVisibility(View.GONE);
                                }

                                if (educationsListBeans!=null){
                                    Log.d(TAG, "onResponse: "+jobseekerProileData.getWorks_list());
                                    eduLayout.setVisibility(View.VISIBLE);

                                    Collections.sort(educationsListBeans, new Comparator<JobseekerProileData.EducationsListBean>() {
                                        @Override
                                        public int compare(JobseekerProileData.EducationsListBean educationsListBean, JobseekerProileData.EducationsListBean t1) {
                                            if(educationsListBean.getJobseeker_education_year().compareTo(t1.getJobseeker_education_year())>0)
                                                return -1;

                                            else
                                                return 0;
                                        }
                                    });

                                    for (int i=0; i<educationsListBeans.size();i++){
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

                                        int id = eduLayout.getChildCount();
                                        View v = null;
                                        for (int k = 0; k<id; k++){
                                            v = eduLayout.getChildAt(k);
                                            if (v!=null){
                                                final TextView txtCoursetype = v.findViewById(R.id.txt_edu_course_title);
                                                final TextView txtSpecilization = v.findViewById(R.id.txt_Specialization);
                                                final TextView txtInstitute = v.findViewById(R.id.txt_institute);
                                                final TextView txtYear = v.findViewById(R.id.txt_edu_year);
                                                TextView txtEduEdit = v.findViewById(R.id.txt_edu_heading);

                                                final int finalK = k;
                                                txtEduEdit.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Intent intent = new Intent(getActivity(), EducationAdd.class);
                                                        intent.putExtra("title", txtCoursetype.getText().toString());
                                                        intent.putExtra("speci", txtSpecilization.getText().toString());
                                                        intent.putExtra("insti", txtInstitute.getText().toString());
                                                        intent.putExtra("year", txtYear.getText().toString());
                                                        intent.putExtra("eduId", educationsListBeans.get(finalK).getJobseeker_education_id());
                                                        startActivity(intent);
                                                    }
                                                });
                                            }
                                        }
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

    private Response.ErrorListener error() {
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
            case R.id.btnAddEducation:
                startActivity(new Intent(getActivity(), EducationAdd.class));
                break;
            case R.id.edt_personal_img_edit:
                Intent intent = new Intent(getActivity(), PersonalAdd.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("desi", desig);
                intent.putExtra("exp", exp);
                intent.putExtra("loc", loc);
                intent.putExtra("salary", salary);
                intent.putExtra("mobile", mobile);
                intent.putExtra("nat", nationality);
                intent.putExtra("workpermit", workpermit);
                startActivity(intent);

                break;
            case R.id.txt_skills_heading:
                edtSkills.setEnabled(true);
                skillsLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.txt_resume_upload:
                startActivity(new Intent(getActivity(), UploadResumeActivity.class));
                break;
            case R.id.txt_saveskills:
                skills = edtSkills.getText().toString();

                if (!skills.isEmpty()){
                    updateSkill();

                } else {
                    Utillity.message(getActivity(),"Please enter company name");
                }
                break;
            case R.id.txt_cancelskills:
                edtSkills.setEnabled(false);
                skillsLayout.setVisibility(View.GONE);
                getProfile();
                break;
        }
    }

    public void updateSkill(){
        HashMap<String,String> params=new HashMap<>();
        params.put("skills",skills);
        params.put("user_id",userId);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_SKILLS,params,
                this.successSkills(),this.error());
        requestQueue.add(customRequest);
    }

    private Response.Listener<JSONObject> successSkills() {
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
                            edtSkills.setEnabled(false);
                            Utillity.message(getContext(), "Objective updated successfully");
                            skillsLayout.setVisibility(View.GONE);
                            getProfile();
                        } else {
                            Utillity.message(getContext(), "Connection error");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }

    public void uploading(String img){
        HashMap<String,String> params=new HashMap<>();
        params.put("user_image",img);
        params.put("user_id",userId);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_PROFILE_PIC,params,
                this.successSkills(),this.error());
        requestQueue.add(customRequest);
    }
    public class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utillity.showloadingpopup(getActivity());
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            Utillity.hidepopup();
            imgUser.setImageBitmap(result);
            img = Utillity.BitMapToString(result);

            Glide.with(getActivity()).load(result)
                    .apply(RequestOptions.circleCropTransform()).into(imgUser);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }


}
