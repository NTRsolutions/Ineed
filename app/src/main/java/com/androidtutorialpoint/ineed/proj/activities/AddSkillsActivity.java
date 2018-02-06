package com.androidtutorialpoint.ineed.proj.activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.SkillsModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.helpshift.support.webkit.CustomWebViewClient.TAG;

public class AddSkillsActivity extends AppCompatActivity implements View.OnClickListener{
    String userId, skills;
    RequestQueue requestQueue;
    SkillsModel skillsModel = new SkillsModel();
    Gson gson = new Gson();
    LoginData loginData = new LoginData();
    TinyDB tinyDB;
    LinearLayout skillLayout, skillsView,bottom_toolbar;
    TextView txtAddSkills,txt_save,txt_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        setuptoolbar();
        requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
        tinyDB = new TinyDB(getApplicationContext());
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userId = loginData.getUser_detail().getUser_id();

        txtAddSkills = findViewById(R.id.addmore_skills);
        skillLayout = findViewById(R.id.skill_layout);
        skillsView = findViewById(R.id.layout_skills);

        txtAddSkills.setOnClickListener(this);
        setupbottomtoolbar();

    }

    private void setupbottomtoolbar() {
        bottom_toolbar=findViewById(R.id.bottom_view);
        txt_save=bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=bottom_toolbar.findViewById(R.id.txt_cancel);
        txt_save.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
    }

   /* public void updateSkill(String list){

        CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_SKILLS,params,
                this.successSkills(),this.error());
        requestQueue.add(customRequest);
    }*/
    private Response.ErrorListener error() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(getApplicationContext(), getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    final OkHttpClient okHttpClient = new OkHttpClient();
    public void updateSkilll(final String skills){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {


                RequestBody requestBody = RequestBody.create(MEDIA_TYPE,skills);
                final okhttp3.Request request = new okhttp3.Request.Builder()
                        .url( ApiList.JOBSEEKER_SKILLS)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String msg = e.getMessage();
                                Utillity.hidepopup();
                                Utillity.message(getApplicationContext(), "Connection error");
                                Log.d("TAG", "onFailure: "+msg);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        Utillity.hidepopup();
                        String msg = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
            }
        });

    }





    private Response.Listener<JSONObject> successSkills() {
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
//                            edtSkills.setEnabled(false);
                            Utillity.message(getApplicationContext(), " Updated successfully");

                        } else {
                            Utillity.message(getApplicationContext(), "Connection error");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }
    ActionBar actionBar;
    Toolbar toolbar;
    private void setuptoolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Add skills");
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
    EditText txtSkils,txtYear;
    List<EditText> edtSkillList = new ArrayList<>();
    List<EditText> edtYearList = new ArrayList<>();
    String skillss, year;

    List<SkillsModel.SkiilsListBean>skiilsListBeans = new ArrayList<>();
    SkillsModel.SkiilsListBean skiilsListBean = new SkillsModel.SkiilsListBean();
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addmore_skills:
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.skill_view, null);
                ImageView buttonRemove = (ImageView) addView.findViewById(R.id.skills_delete);
                txtSkils = addView.findViewById(R.id.skills_edtskills);
                txtYear = addView.findViewById(R.id.skills_edtyear);
                edtSkillList.add(txtSkils);
                edtYearList.add(txtYear);

                buttonRemove.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                skillsView.addView(addView);

              break;
            case R.id.txt_save:{
                ArrayList<String>skills = new ArrayList<>();
                ArrayList<String>years = new ArrayList<>();
                StringBuilder stringBuilder = new StringBuilder();
                for (EditText editText : edtSkillList) {
                    stringBuilder.append(editText.getText().toString());
                    skills.add((editText.getText().toString()));
                }
                for (EditText editText : edtYearList) {
                    stringBuilder.append(editText.getText().toString());
                    years.add((editText.getText().toString()));
                }

                for (String s : skills){
                    Log.d(TAG, "onClick: "+s);
                }
                Log.d(TAG, "onClick: "+ gson.toJson(years)+  gson.toJson(skills));
                System.out.println(years+  " "+ skills);
                for (int i=0;i<years.size();i++){
//                    for (int k =0;k<skills.size();k++){
                            System.out.println(years.get(i));
                        skiilsListBean.setSkills(skills.get(i));
                        skiilsListBean.setYesr(years.get(i));
                        skiilsListBeans.add(skiilsListBean);
//                    }
                }
            }
            //
            skillsModel.setUser_id(Integer.parseInt(userId));
            skillsModel.setSkiils_list(skiilsListBeans);
            String s = gson.toJson(skillsModel);

            updateSkilll(s);


            for (SkillsModel.SkiilsListBean skiilsListBean: skiilsListBeans){
                Log.d(TAG, "onClick: "+skiilsListBean.getSkills());
            }
                break;
            case R.id.txt_cancel:
                finish();
                break;


        }
    }
}
