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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.models.SkillsModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    LinearLayout skillLayout, container,bottom_toolbar;
    TextView txtAddSkills,txt_save,txt_cancel;
    String[] value_skiil;
    String[] value_year;
    String s="",y="";
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
        if (getIntent().hasExtra("year")){
            value_year = getIntent().getStringArrayExtra("year");
        }
        if (getIntent().hasExtra("skill")){
            value_skiil = getIntent().getStringArrayExtra("skill");
        }
        txtAddSkills = findViewById(R.id.addmore_skills);
        skillLayout = findViewById(R.id.skill_layout);
        container = findViewById(R.id.layout_skills);
        container.setVisibility(View.GONE);
        txtAddSkills.setOnClickListener(this);
        setupbottomtoolbar();
        if (value_skiil!=null && value_skiil.length>0){
            for (int i=0;i<value_skiil.length;i++){
                s = value_skiil[i];
                y=value_year[i];
                addLayout(s,y);
            }
        }

    }

    private void setupbottomtoolbar() {
        bottom_toolbar=findViewById(R.id.bottom_view);
        txt_save=bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=bottom_toolbar.findViewById(R.id.txt_cancel);
        txt_save.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
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
                        Log.d(TAG, "run: "+msg);
                        if (response.body()!=null){
                            try {
                                final JSONObject jsonObject = new JSONObject(msg);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (jsonObject.getString("status").equals("true")){
                                                Utillity.message(getApplicationContext(), " Updated successfully");
                                                finish();

                                            } else {
                                                Utillity.message(getApplicationContext(), getResources().getString(R.string.internetConnection));
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
            }
        });

    }
    private void addLayoutf() {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout=layoutInflater.inflate(R.layout.skill_view, null);

        container.setVisibility(View.VISIBLE);

        ImageView buttonRemove = (ImageView) layout.findViewById(R.id.skills_delete);
        buttonRemove.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((LinearLayout)layout.getParent()).removeView(layout);
            }});
        container.addView(layout);
    }

    private void addLayout(String s, String y) {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout=layoutInflater.inflate(R.layout.skill_view, null);

        container.setVisibility(View.VISIBLE);
        TextView txtSkill = layout.findViewById(R.id.skills_edtskills);
        TextView txtYear = layout.findViewById(R.id.skills_edtyear);
        if (s.length()>0 && y.length()>0){
            txtSkill.setText(s);
            txtYear.setText(y);
        }

        ImageView buttonRemove = (ImageView) layout.findViewById(R.id.skills_delete);
        buttonRemove.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ((LinearLayout)layout.getParent()).removeView(layout);
            }});
        container.addView(layout);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addmore_skills:

                addLayoutf();

              break;
            case R.id.txt_save:

               if (container.getVisibility()==View.VISIBLE){
                   List<SkillsModel.SkiilsListBean>skiilsListBeans = new ArrayList<>();
                   final LinearLayout container = (LinearLayout) findViewById(R.id.layout_skills);
                   Log.d(TAG, "validateOtherAdditionalCost: "+container.getChildCount());
                   for (int indexChild = 0; indexChild < container.getChildCount(); indexChild++) {
                       View childView = container.getChildAt(indexChild);
                       EditText tvyear = (EditText) childView.findViewById(R.id.skills_edtyear);
                       EditText tvskill = (EditText) childView.findViewById(R.id.skills_edtskills);
                       String cost = tvyear.getText().toString().trim();
                       String costClient = tvskill.getText().toString().trim();

                       if (!cost.isEmpty() || !costClient.isEmpty()) {
                           if (!cost.isEmpty() && !costClient.isEmpty() ) {
                               SkillsModel.SkiilsListBean bean=new SkillsModel.SkiilsListBean();
                               System.out.println(cost + costClient);
                               bean.setSkills(costClient);
                               bean.setYesr(cost);
                               skiilsListBeans.add(bean);
                           }

                           else
                           {
                               Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                           }
                       }
                   }
                   Log.d(TAG, "validateOtherAdditionalCost: "+skiilsListBeans.size());
                   skillsModel.setUser_id(Integer.parseInt(userId));
                   skillsModel.setSkiils_list(skiilsListBeans);
                   String s = gson.toJson(skillsModel);

                   updateSkilll(s);
               }
                break;
            case R.id.txt_cancel:
                finish();
                break;
        }
    }


}
