package com.androidtutorialpoint.ineed.proj.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.permissions.AppPermissions;
import com.mukesh.tinydb.TinyDB;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.bouncycastle.util.encoders.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.androidtutorialpoint.ineed.proj.models.ImageInputHelper.REQUEST_PICTURE_FROM_CAMERA;
import static com.androidtutorialpoint.ineed.proj.models.ImageInputHelper.REQUEST_PICTURE_FROM_GALLERY;
import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

public class UploadResumeActivity extends AppCompatActivity {
    Button btnUpload;
    FrameLayout frameLayout;
    TinyDB tinyDB;
    Gson gson = new Gson();
    LoginData loginData = new LoginData();
    RequestQueue requestQueue;
    ActionBar actionBar;
    String user_id,extension;
    private AppPermissions mRuntimePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);
//        initialize
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        tinyDB = new TinyDB(getApplicationContext());
        String data = tinyDB.getString("login_data");
        loginData= gson.fromJson(data, LoginData.class);
        user_id = loginData.getUser_detail().getUser_id();
        mRuntimePermission = new AppPermissions(UploadResumeActivity.this);
//        find jobseekerid
        frameLayout = findViewById(R.id.frame_upload);
        btnUpload = findViewById(R.id.upload);

//        set onclick
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                select file
                if (mRuntimePermission.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showFileChooser();
                } else {
                    mRuntimePermission.requestPermission(Manifest.permission.CAMERA, REQUEST_PICTURE_FROM_CAMERA);
                }
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                select file
                showFileChooser();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                } else {
                    Toast.makeText(getApplicationContext(), "Gallery Permissions granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Update resume");
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        set toolbar
        setuptoolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            String a;
            if (resultCode == Activity.RESULT_OK) {
                File file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));

                try {
                    a = encodeFileToBase64Binary(file);
//                    update resume
                    upResume(a);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

//    update resume
    public void upResume(String file){
        Utillity.checkCameraPermission(UploadResumeActivity.this);
        HashMap<String,String> params=new HashMap<>();

        params.put("user_file",file);
        params.put("user_id",user_id);
        params.put("ext",extension);
        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_UPLOAD_RESUME,params,
                this.successResume(),this.error());
        requestQueue.add(customRequest);
    }

    private Response.Listener<JSONObject> successResume() {
        Utillity.showloadingpopup(this);
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Log.d(TAG, "onResponse:data "+response.toString());
                if (response!=null){
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (jsonObject.getString("status").equals("true")){
                            Log.d(TAG, "onResponse: "+response);
                            Utillity.message(UploadResumeActivity.this, "Resume uploaded");
                            finish();
                        } else {
                            Utillity.message(UploadResumeActivity.this, "No file selected");
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
                Utillity.message(UploadResumeActivity.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }

    private void showFileChooser() {

        if (mRuntimePermission.hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new MaterialFilePicker().withActivity(this)
                    .withRequestCode(0)
                    .start();
        } else {
            mRuntimePermission.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 0);
        }
    }

    private String encodeFileToBase64Binary(File fileName)
            throws IOException {
        byte[] bytes = loadFile(fileName);
        byte[] encoded = Base64.encode(bytes);
        extension = fileName.getAbsolutePath().substring(fileName.getAbsolutePath().lastIndexOf("."));
        String encodedString = new String(encoded);
            Log.d(TAG, "upResume: "+extension.replace(".",""));
        return encodedString;
    }
    private byte[] loadFile(File file) throws IOException {

        InputStream is = new FileInputStream(file);
        int maxBufferSize = 1 * 1024 * 1024;
        long length = file.length();
        byte[] bytes = new byte[0];

        if (length > maxBufferSize) {
            // File is too large
            Utillity.message(UploadResumeActivity.this, "File size not greater than 2MB");
        } else {
            bytes = new byte[(int)length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "+file.getName());
            }
            is.close();
        }
        return bytes;
    }

}
