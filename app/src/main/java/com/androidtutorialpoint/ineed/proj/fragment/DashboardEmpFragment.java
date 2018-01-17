package com.androidtutorialpoint.ineed.proj.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.androidtutorialpoint.ineed.proj.activities.LoginActivity;
import com.androidtutorialpoint.ineed.proj.activities.ProfileViewed;
import com.androidtutorialpoint.ineed.proj.activities.UpgradePlanActivity;
import com.androidtutorialpoint.ineed.proj.models.EmployerProfileData;
import com.androidtutorialpoint.ineed.proj.models.ImageInputHelper;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import static com.helpshift.support.webkit.CustomWebViewClient.TAG;


public class DashboardEmpFragment extends Fragment implements ImageInputHelper.ImageActionListener{
    EditText etEmail,etName,etcontact,etcompany;
    TextView txt_proftitle,txt_personal,txtSave,txtCancle, txtProfileView, txtPackage, txtExpired, txtCredit,
            txtUpgrade, txtLeft;
    LinearLayout ll_savecancel;
    private ImageInputHelper imageInputHelper;
    ImageView imgUser, imgCamera;
    String img,language, userId, name, company, email, phone;
    TinyDB tinyDB;
    LoginData loginData;
    EmployerProfileData profileDetailMOdel = new EmployerProfileData();

    RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard_emp, container, false);
        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);
        loginData = new LoginData();
        tinyDB = new TinyDB(getContext());
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();

//             find id
        txtCredit = view.findViewById(R.id.credit_point);
        txtUpgrade = view.findViewById(R.id.txtUpgradePlan);
        txtCancle = view.findViewById(R.id.txt_cancel);
        txtSave = view.findViewById(R.id.txt_save);
        txtExpired = view.findViewById(R.id.et_expired);
        txtPackage = view.findViewById(R.id.et_package);
        txtLeft = view.findViewById(R.id.credit_left);
        ll_savecancel= (LinearLayout)view.findViewById(R.id.ll_savecancel);
        txtProfileView = (TextView) view.findViewById(R.id.txt_profileViewed);
        etName = (EditText) view.findViewById(R.id.et_name);
        etcompany = (EditText) view.findViewById(R.id.et_company);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etcontact = (EditText) view.findViewById(R.id.et_phone);
        txt_personal = (TextView) view.findViewById(R.id.txt_personal);
        txt_proftitle = (TextView) view.findViewById(R.id.etProfile_name);
        imgUser = (ImageView) view.findViewById(R.id.emp_img_profilew) ;
        imgCamera = (ImageView) view.findViewById(R.id.emp_img_camera);

        txtProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileViewed.class));
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

        etcontact.setEnabled(false);
        etEmail.setEnabled(false);
        etcompany.setEnabled(false);
        etName.setEnabled(false);

        txt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(getApplication(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });

        txt_personal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickonDrawable(v,event);
                return false;
            }
        });

        txtUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpgradePlanActivity.class));
            }
        });

        txtCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.setEnabled(false);
                etcontact.setEnabled(false);
                etcompany.setEnabled(false);
                etName.setEnabled(false);
                ll_savecancel.setVisibility(View.GONE);
                getProfile();
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                name = etName.getText().toString();
                phone = etcontact.getText().toString();
                company = etcompany.getText().toString();
                if (Utillity.CheckEmail(email)){
                    if (Utillity.CheckPhone(phone)){
                        if (!company.isEmpty()){
                            if (!name.isEmpty()){
                                updateProfile();
                            } else {
                                Utillity.message(getActivity(),"Please enter name");
                            }
                        } else {
                            Utillity.message(getActivity(),"Please enter company name");
                        }
                    } else {
                        Utillity.message(getActivity(),"Please enter valid mobile");
                    }
                } else {
                    Utillity.message(getActivity(),"Please enter valid email");
                }
            }
        });
        return view;
    }

    public void updateProfile(){

        HashMap<String,String> params=new HashMap<>();
        params.put("fname",name);
        params.put("company",company);
        params.put("email",email);
        params.put("user_id",userId);
        params.put("phone",phone);

        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.EMPLOYER_PROFILE_EDIT,params,
                this.successProfile(),this.error());
        requestQueue.add(customRequest);
    }

    void clickonDrawable(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (txt_personal.getRight() - txt_personal.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                etEmail.setEnabled(true);
                etcontact.setEnabled(true);
                etcompany.setEnabled(true);
                etName.setEnabled(true);
                ll_savecancel.setVisibility(View.VISIBLE);

            }
        }
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
            Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(imgUser);
            img = Utillity.BitMapToString(bitmap);
            uploading(img);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Gson gson = new Gson();
    @Override
    public void onResume() {
        super.onResume();
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userId = loginData.getUser_detail().getUser_id();
        language = tinyDB.getString("language_id");

        getProfile();
    }

    public void getProfile(){

        HashMap<String,String> params=new HashMap<>();
        params.put("user_id",userId);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.EMPLOYER_PROFILE,params,
                this.success(),this.error());
        requestQueue.add(customRequest);
    }


    private Response.Listener<JSONObject> successProfile() {
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
                            Utillity.message(getContext(), "Profile updated successfully");
                            etEmail.setEnabled(false);
                            etcontact.setEnabled(false);
                            etcompany.setEnabled(false);
                            etName.setEnabled(false);
                            ll_savecancel.setVisibility(View.GONE);
                            getProfile();

                        } else {
                            Utillity.message(getContext(), "Profile not updated");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }


    private Response.Listener<JSONObject> success() {
        Utillity.showloadingpopup(getActivity());
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Log.d(TAG, "onResponse:data "+response.toString());
                if (response!=null){
                    try {
                        if (!response.getString("status").equals("false")){

                            profileDetailMOdel = gson.fromJson(response.toString(), EmployerProfileData.class);
                            if (profileDetailMOdel.getProfile_detail() != null) {
                               if (profileDetailMOdel.getProfile_detail().getUser_package_credit().equals("Expired")){
                                    setupoverlay();
                                } else {
                                   etName.setText(profileDetailMOdel.getProfile_detail().getUser_fname());
                                   etEmail.setText(profileDetailMOdel.getProfile_detail().getUser_email());
                                   etcontact.setText(profileDetailMOdel.getProfile_detail().getUser_phone());
                                   etcompany.setText(profileDetailMOdel.getProfile_detail().getUser_company());
                                   txtExpired.setText(profileDetailMOdel.getProfile_detail().getUser_package_expire_date());
                                   txtCredit.setText(String.valueOf(profileDetailMOdel.getProfile_detail().getUser_package_credit()));
                                   txtLeft.setText(String.valueOf(profileDetailMOdel.getProfile_detail().getUser_credit_use()));
                                   txt_proftitle.setText(profileDetailMOdel.getProfile_detail().getUser_fname());
                                   if (profileDetailMOdel.getProfile_detail().getUser_image()!=null){
                                       String url = ApiList.IMG_BASE+profileDetailMOdel.getProfile_detail().getUser_image();
                                       GetImage task = new GetImage();
                                       // Execute the task
                                       task.execute(new String[] { url });
                                   }
                                    else {
                                       Glide.with(getContext()).load(R.drawable.gfgf)
                                               .apply(RequestOptions.circleCropTransform()).into(imgUser);
                                   }

                               }
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

    private void setupoverlay() {
        final Dialog dialog=new Dialog(getContext(),android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_overlay_popup);
        final TextView txtlogout = dialog.findViewById(R.id.txtLogout);
        final TextView txtMsg = dialog.findViewById(R.id.txt_msg);
        txtMsg.setText("Your package expired please upgrade");
        final Button upgrade=(Button)dialog.findViewById(R.id.overupgrade);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), UpgradePlanActivity.class));
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                tinyDB.remove("login_data");
                getActivity().finish();

            }
        });

        dialog.show();
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


    public void uploading(String img){
        HashMap<String,String> params=new HashMap<>();
        params.put("user_image",img);
        params.put("user_id",userId);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.EMP_PROFILE_PIC,params,
                this.successIMg(),this.error());
        requestQueue.add(customRequest);
    }

    private Response.Listener<JSONObject> successIMg() {
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
                            Utillity.message(getContext(), "Updated successfully");
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
