package com.androidtutorialpoint.ineed.proj.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.androidtutorialpoint.ineed.proj.models.Skills;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mukesh.permissions.AppPermissions;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.androidtutorialpoint.ineed.proj.activities.Search.jobseekerid;
import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class DashboardJobseeker extends Fragment implements ImageInputHelper.ImageActionListener, View.OnClickListener {
    private final int  REQUEST_CAMERA=0, SELECT_FILE = 1;
    private String userChoosenTask;
    boolean result;
    AppPermissions appPermissions;
    TextView txtaddeduc;
    LinearLayout ll_savecancel;
    ImageView imgUser, imgCamera,imgedit;
    Gson gson;
    LoginData loginData;
    String img,language, userId, obj, skills, name="", dob = "", desig = "", nationality =  "",
            exp ="", loc = "", salary = "",permitCountry = "",jobtype="", jobtypeid ="",currentComp="",gender="", expMonth="", expYear="",mobile ="", workpermit="",workpermitid;
    RequestQueue requestQueue;
    private ImageInputHelper imageInputHelper;
    JobseekerProileData jobseekerProileData;
    List<JobseekerProileData.EducationsListBean>educationsListBeans ;
    List<JobseekerProileData.WorksListBean>worksListBeans;
    View view;
    TinyDB tinyDB;
    LinearLayout workLayout,workview, eduLayout, eduView, skillsLayout, noticeLayout, toLayout;
    TextView txtResumeView, txtResumeUpload, txtSkillsEditHeading, txtSaveObj, txtCancle, txt_personal, txtName, txtDob, txtDesignation, txtNationaliaty,
            txtExp, txtCurrentLocation, txtSalary,txtWorkSalary, txtJobtype, txtMobile, txtEmail, txtWorkingexp,txtJobTitle, txtCompanyName, txtJobHeading, txtWorkingFrom, txtWrokingTo,
    txtNotice, txtIndustry, txtDepartment,txtSaveSkills, txtCancelSkill, txtTo,txtCoursetype, txtSpecilization, txtInstitute, txtYear, txtDepartMent,txt_addwk;
    EditText edtobjective, edtSkills;
    private static final int ALL_REQUEST_CODE = 3;
    ArrayList<Skills>skillsList = new ArrayList<>();

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
        appPermissions = new AppPermissions(getActivity());
        workLayout = view.findViewById(R.id.work_exp);
        skillsLayout = view.findViewById(R.id.ll_savecancelskills);
        workview = view.findViewById(R.id.layout_work_exp);
        eduLayout = view.findViewById(R.id.edu_exp);
        eduView = view.findViewById(R.id.layout_edu_exp);
        txtResumeView = view.findViewById(R.id.txt_resume_view);
        txtResumeUpload = view.findViewById(R.id.txt_resume_upload);

        for (int i=0;i<=5;i++){
            skillsList.add(new Skills("HTML","2"));
        }
        Log.d(TAG, "onCreateView: "+skillsList.size());

        String jsonStudents = gson.toJson(skillsList);
        System.out.println("jsonStudents = " + jsonStudents);

        // Converts JSON string into a collection of Student object.
        Type type = new TypeToken<List<Skills>>() {}.getType();
        List<Skills> studentList = gson.fromJson(jsonStudents, type);


//        Log.d(TAG, "onCreateView: "+jsonArray);
//        find id
        imgCamera =  view.findViewById(R.id.edt_img_camera);
        imgUser =  view.findViewById(R.id.edt_img_profile);
        imgedit=view.findViewById(R.id.edt_personal_img_edit);
        txtName = view.findViewById(R.id.jobseerker_profileName);
        txtDob = view.findViewById(R.id.jobseeker_profileAge);
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
        if (appPermissions.hasPermission(ALL_PERMISSIONS)){
            result = true;
        } else {
            appPermissions.requestPermission(getActivity(), ALL_PERMISSIONS, ALL_REQUEST_CODE);
        }

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
                getProfile(userId);
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
                selectImage();
            }
        });
        return view;
    }

    private static final String[] ALL_PERMISSIONS = {

            Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
    };

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
                            getProfile(userId);
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

        if (jobseekerid==null){
            getProfile(userId);
        } else {
            imgCamera.setVisibility(View.GONE);
            imgedit.setVisibility(View.GONE);
            txtSkillsEditHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            txt_personal.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            txt_addwk.setVisibility(View.GONE);
            txtaddeduc.setVisibility(View.GONE);
            txtResumeUpload.setVisibility(View.GONE);
            getProfile(jobseekerid);
        }



    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if (appPermissions.hasPermission( Manifest.permission.CAMERA)){
                        result = true;
                        cameraIntent();
                    } else {
                        appPermissions.requestPermission(getActivity(),  Manifest.permission.CAMERA, REQUEST_CAMERA);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if (appPermissions.hasPermission( Manifest.permission.READ_EXTERNAL_STORAGE)){
                        result = true;
                        galleryIntent();
                        Toast.makeText(getContext(), "All granted gal"+result, Toast.LENGTH_SHORT).show();
                    } else {

                        appPermissions.requestPermission(getActivity(),  Manifest.permission.READ_EXTERNAL_STORAGE, SELECT_FILE);

                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgUser.setImageBitmap(thumbnail);
        Glide.with(this).load(thumbnail).apply(RequestOptions.circleCropTransform()).into(imgUser);

        img = Utillity.BitMapToString(thumbnail);
        uploading(img);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                Glide.with(this).load(bm).apply(RequestOptions.circleCropTransform()).into(imgUser);

                img = Utillity.BitMapToString(bm);
                uploading(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
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


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProfile(String userId){
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
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Log.d(TAG, "onResponse:data "+response.toString());
                if (response!=null){
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
                            if (jsonObject.getString("user_dob").length()>1&&jsonObject.getString("user_dob").length()>1){
                                dob = jobseekerProileData.getUser_list().getUser_dob();
                                gender =  jobseekerProileData.getUser_list().getUser_gender();
                                txtDob.setText(dob+ ",  "+gender);
                            }
                            if (!jsonObject.getString("user_nationality").equals("0") &&jsonObject.getString("user_nationality").length()>1){
                                nationality  = jobseekerProileData.getUser_list().getUser_nationality();
                                String workpc ;
                                if (jobseekerProileData.getUser_list().getUser_workpermit().equals("no")){
                                    workpc = "No";
                                } else {
                                    workpc = (String) jobseekerProileData.getUser_list().getUser_permitcountry();
                                    permitCountry = (String) jobseekerProileData.getUser_list().getUser_permitcountry();
                                }
                                txtNationaliaty.setText(jobseekerProileData.getUser_list().getUser_nationality()
                                        +", "+" with "+workpc+" work permit");
                                workpermit = jobseekerProileData.getUser_list().getUser_workpermit();
                            }
                            txtEmail.setText(jobseekerProileData.getUser_list().getUser_email());


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

                                desig = jobseekerProileData.getUser_list().getProfile_summary_positions();
                                jobtype = jobseekerProileData.getUser_list().getProfile_summary_jobtype();
                                jobtypeid = jobseekerProileData.getUser_list().getProfile_summary_jobtype_id();
                                currentComp =  jobseekerProileData.getUser_list().getProfile_summary_companyname();
                                txtDesignation.setText(desig.toUpperCase()+ " | "+ currentComp
                                +" | " + jobtype);

                            }
//                                if (jsonObject.getString("profile_summary_totalyear").length()>1&&jsonObject.getString("profile_summary_totalyear").length()>1){
//                                    txtExp.setText(jobseekerProileData.getUser_list().getProfile_summary_totalyear()+" year");
//                                    exp = jobseekerProileData.getUser_list().getProfile_summary_totalyear();
//                                }
                            if (jobseekerProileData.getUser_list().getProfile_summary_totalexpyear()!=null){
                                if (jobseekerProileData.getUser_list().getProfile_summary_totalexpmonths()!=null){
                                    expYear = jobseekerProileData.getUser_list().getProfile_summary_totalexpyear();
                                    expMonth  = jobseekerProileData.getUser_list().getProfile_summary_totalexpmonths();
                                    txtExp.setText(jobseekerProileData.getUser_list().getProfile_summary_totalexpyear()
                                            + "." + jobseekerProileData.getUser_list().getProfile_summary_totalexpmonths() +" year");
                                }
                            }

                            if (jsonObject.getString("profile_summary_currentcountry").length()>1&&jsonObject.getString("profile_summary_currentcountry").length()>1){
                                txtCurrentLocation.setText(jobseekerProileData.getUser_list().getProfile_summary_currentcountry());
                                loc = jobseekerProileData.getUser_list().getProfile_summary_currentcountry();
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
                            if (jsonObject.getString("user_resume")!=null  && jsonObject.getString("user_resume").length()>0){
                                final String text = ApiList.IMG_BASE+jsonObject.getString("user_resume");
                                final String afterDecode = URLDecoder.decode(text, "UTF-8");
                                txtResumeView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (afterDecode.length()>0){
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(afterDecode));
                                            startActivity(i);
                                        } else {
                                            Utillity.message(getContext(), "Please upload resume");
                                        }
                                    }
                                });
                            }
                            worksListBeans.addAll(jobseekerProileData.getWorks_list());

                            if (worksListBeans != null && worksListBeans.size()>0) {
                                workLayout.setVisibility(View.VISIBLE);
                                for (int i = 0; i <worksListBeans.size(); i++) {
                                    workview = (LinearLayout) View.inflate(getContext(), R.layout.work_experience_view, null);
                                    txtJobHeading = workview.findViewById(R.id.txt_work_experience_heading);
                                    txtJobTitle = workview.findViewById(R.id.txt_work_experienceJobtitle);
                                    txtCompanyName = workview.findViewById(R.id.txt_CompanyName);
                                    txtWorkingexp = workview.findViewById(R.id.txt_work_experienceWorkFrom);
                                    txtNotice = workview.findViewById(R.id.txt_work_experienceNotice);
                                    txtIndustry = workview.findViewById(R.id.txt_work_experiencetxtIndustry);
                                    txtDepartment = workview.findViewById(R.id.txt_work_experiencetxtDepartment);
                                    txtTo = workview.findViewById(R.id.txt_work_experienceTo);
                                    noticeLayout = workview.findViewById(R.id.notice_layout);
                                    toLayout = workview.findViewById(R.id.to_layout);
                                    txtWorkSalary = workview.findViewById(R.id.txt_work_experienceSalary);
                                    txtJobtype = workview.findViewById(R.id.txt_work_experienceJobType);

                                    if (jobseekerid!=null) {
                                        txtJobHeading.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    }
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
                                        txtNotice.setText(jobseekerProileData.getWorks_list().get(i).getJobseeker_workexp_noticeperiod());
                                        txtIndustry.setText(worksListBeans.get(i).getJobseeker_workexp_companyindus());
                                        txtWorkingexp.setText(worksListBeans.get(i).getJobseeker_workexp_totalyear()+" year");
                                        txtJobTitle.setText(worksListBeans.get(i).getPositions());
                                        txtCompanyName.setText(worksListBeans.get(i).getJobseeker_workexp_companyname());
                                        txtDepartment.setText(worksListBeans.get(i).getJobseeker_workexp_dept());
                                        txtWorkSalary.setText((String)worksListBeans.get(i).getJobseeker_workexp_annualsalary());
                                        txtJobtype.setText((String) worksListBeans.get(i).getJobseeker_workexp_jobtype());
                                        workLayout.addView(workview);

                                    } else if (worksListBeans.get(i).getJobseeker_workexp_employertype().equals("p")){
                                        txtJobHeading.setText("Previous");
                                        txtIndustry.setText(worksListBeans.get(i).getJobseeker_workexp_companyindus());
                                        txtWorkingexp.setText(worksListBeans.get(i).getJobseeker_workexp_totalyear()+" year");
                                        txtJobTitle.setText(worksListBeans.get(i).getPositions());
                                        txtCompanyName.setText(worksListBeans.get(i).getJobseeker_workexp_companyname());
                                        txtDepartment.setText(worksListBeans.get(i).getJobseeker_workexp_dept());
                                        txtWorkSalary.setText((String) worksListBeans.get(i).getJobseeker_workexp_annualsalary());
                                        txtJobtype.setText((String) worksListBeans.get(i).getJobseeker_workexp_jobtype());
                                        workLayout.addView(workview);

//                                            noticeLayout.setVisibility(View.GONE);
//                                            toLayout.setVisibility(View.VISIBLE);
//                                            txtTo.setText(" 12/2/2019");
                                    } else {
                                        workLayout.removeAllViews();
                                    }

                                    int count = workLayout.getChildCount();
                                    View v = null;
                                    for(int k=0; k<count; k++) {
                                        v = workLayout.getChildAt(k);
                                        final TextView txtJobHeading = v.findViewById(R.id.txt_work_experience_heading);
                                        final TextView txtJobTitle = v.findViewById(R.id.txt_work_experienceJobtitle);
                                        final TextView txtCompanyName = v.findViewById(R.id.txt_CompanyName);
                                        final TextView txtWorkingexp = v.findViewById(R.id.txt_work_experienceWorkFrom);
//                                        txtWorkingFrom = workview.findViewById(R.jobseekerid.txt_work_experienceWorkFrom);
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
                                                intent.putExtra("jobseekerid", worksListBeans.get(finalK).getJobseeker_workexp_id());
                                                intent.putExtra("salary", (String) worksListBeans.get(finalK).getJobseeker_workexp_annualsalary());
                                                intent.putExtra("jobtypeid", (String) worksListBeans.get(finalK).getJobseeker_workexp_jobtype());
                                                startActivity(intent);
                                            }
                                        });

                                        //do something with your child element
                                    }
                                }
                            } else {
                                workLayout.setVisibility(View.GONE);
                            }
                            educationsListBeans.addAll(jobseekerProileData.getEducations_list());
                            if (educationsListBeans!=null){
                                Log.d(TAG, "onResponse: "+educationsListBeans.size());
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
                                            if (jobseekerid!=null){
                                                txtEduEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                            }
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
                    } catch (UnsupportedEncodingException e) {
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
                intent.putExtra("dob", dob);
                intent.putExtra("desi", desig);
                intent.putExtra("exp", exp);
                intent.putExtra("loc", loc);
                intent.putExtra("salary", salary);
                intent.putExtra("mobile", mobile);
                intent.putExtra("nat", nationality);
                intent.putExtra("workpermit", workpermit);
                intent.putExtra("permitCountry", permitCountry);
                intent.putExtra("expYear", expYear);
                intent.putExtra("expMonth", expMonth);
                intent.putExtra("jobTypeid", jobtypeid);
                intent.putExtra("jobType", jobtype);
                intent.putExtra("gender", gender);
                intent.putExtra("dob",dob);
                intent.putExtra("currentComp",currentComp);
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
                getProfile(userId);
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
                            getProfile(userId);
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
