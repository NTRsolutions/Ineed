package com.androidtutorialpoint.ineed.proj.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.AdminList;
import com.androidtutorialpoint.ineed.proj.models.CountryList;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonalAdd extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    Toolbar toolbar;
    String CountryId, userEmail,exp, locid,salary,age, workCoutId, gender, userid, workPermit,nationalityId,
            name, desi, no;
    LinearLayout bottom_toolbar;
    TextView txt_save,txt_cancel;
    EditText edtName, edtDesig,edtExp, edtSalary, edtNo, edtAge, edtNationality;
    TinyDB tinyDB;
    RadioButton workPermitYes, workPermitNo, maleRadioButton, femaleRadioButton;
    Spinner select_location, spinner_workPermit;
    ArrayList<String> Cname,Cid;
    LoginData loginData;
    List<CountryList.CountryListBean> countrylst=new ArrayList<>();

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add);
        setuptoolbar();
        setupbottomtoolbar();
        tinyDB = new TinyDB(getApplicationContext());
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userid = loginData.getUser_detail().getUser_id();
        userEmail = loginData.getUser_detail().getUser_email();

        /* intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("desi", desig);
                intent.putExtra("exp", exp);
                intent.putExtra("loc", loc);
                intent.putExtra("salary", salary);
                intent.putExtra("mobile", mobile);*/
//        get intent

        name  = getIntent().getStringExtra("name");
        age  = getIntent().getStringExtra("age");
        desi  = getIntent().getStringExtra("desi");
        exp  = getIntent().getStringExtra("exp");
        locid= getIntent().getStringExtra("loc");
        salary  = getIntent().getStringExtra("salary");
        no = getIntent().getStringExtra("mobile");
        nationalityId = getIntent().getStringExtra("nat");
        workPermit = getIntent().getStringExtra("workpermit");

//        find id
        select_location = findViewById(R.id.spinner_location);
        spinner_workPermit = findViewById(R.id.permit_country_spinner);
//        spinner_age = findViewById(R.id.spinner_age);
        edtExp = findViewById(R.id.spinner_exp);
        edtSalary = findViewById(R.id.spinner_salary);
        workPermitYes = findViewById(R.id.radio_work_permit_yes);
        workPermitNo = findViewById(R.id.radio_work_permit_no);
        maleRadioButton = findViewById(R.id.radio_gender_male);
        femaleRadioButton = findViewById(R.id.radio_gender_female);
        edtNationality = findViewById(R.id.spinner_nationality);
        edtName = findViewById(R.id.txt_personal_edtname);
        edtDesig = findViewById(R.id.edt_personal_desi);
        edtNo = findViewById(R.id.edt_personal_no);
        edtAge = findViewById(R.id.edt_age);

//        set value
        edtExp.setText(exp);
        edtSalary.setText(salary);
        edtNationality.setText(nationalityId);
        edtName.setText(name);
        edtNo.setText(no);
        edtExp.setText(exp);
        edtAge.setText(age);
        edtDesig.setText(desi);
        if (workPermit.equals("yes")){
            workPermitYes.setChecked(true);
            workPermitNo.setChecked(false);
            spinner_workPermit.setVisibility(View.VISIBLE);

        } else {
            workPermitNo.setChecked(true);
            workPermitYes.setChecked(false);
            spinner_workPermit.setVisibility(View.GONE);
        }

        workPermitNo.setOnClickListener(this);
        workPermitYes.setOnClickListener(this);
        maleRadioButton.setOnClickListener(this);
        femaleRadioButton.setOnClickListener(this);
        gender = "male";
        workPermit = "no";
//        getAdminList();
        getcountrylist();

    }

    private void setupbottomtoolbar() {
        bottom_toolbar=findViewById(R.id.bottom_toolbar);
        txt_save=bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=bottom_toolbar.findViewById(R.id.txt_cancel);
        txt_save.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
    }

    private void setuptoolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Profile");
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_save:
                name = edtName.getText().toString().trim();
               desi = edtDesig.getText().toString().trim();
               no = edtNo.getText().toString().trim();
               age = edtAge.getText().toString().trim();
               salary = edtSalary.getText().toString().trim();
               exp = edtExp.getText().toString().trim();
               nationalityId = edtNationality.getText().toString().trim();
               if (!name.isEmpty()){
                   if (!desi.isEmpty()){
                       if (Utillity.CheckPhone(no)){
                           InputMethodManager inputMethodManager= (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                           inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
                           saveData();
                       }else {
                           Utillity.message(PersonalAdd.this, "Enter valid number");
                       }
                   } else {
                       Utillity.message(PersonalAdd.this, "Enter designation");

                   }
               }else {
                   Utillity.message(PersonalAdd.this, "Enter name");
               }
                Utillity.message(getApplicationContext(),"Successfully Added");
                finish();
                break;
            case R.id.txt_cancel:
                InputMethodManager inputMethodManager= (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
                finish();
                break;

            case R.id.radio_gender_male:
                gender = "male";
                femaleRadioButton.setChecked(false);
                break;
            case R.id.radio_gender_female:
                gender = "female";
                maleRadioButton.setChecked(false);
                break;
            case R.id.radio_work_permit_no:
                workPermitYes.setChecked(false);
                spinner_workPermit.setVisibility(View.GONE);
                workPermit = "no";
                break;
            case R.id.radio_work_permit_yes:
                workPermit = "yes";
                workPermitNo.setChecked(false);
                spinner_workPermit.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void saveData() {
        if(Utillity.isNetworkConnected(PersonalAdd.this)) {
            Utillity.showloadingpopup(PersonalAdd.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id",userid);
            params.put("name",name);
            params.put("age",age);
            params.put("designation",desi);
            params.put("nationality",nationalityId);
            params.put("workpermit",workPermit);
            params.put("experience",exp);
            params.put("current_location",CountryId);
            params.put("salary",salary);
            params.put("phone",no);
            params.put("gender",gender);
            params.put("email",userEmail);

            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_PERSONAL_EDIT, params, this.success(), this.errorListener());
            queue.add(customRequest);
            customRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }


    private void getAdminList() {
        if(Utillity.isNetworkConnected(PersonalAdd.this)) {
            Utillity.showloadingpopup(PersonalAdd.this);
                            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
                HashMap<String, String> params = new HashMap<>();

                CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_ADMIN_LIST, params, this.success(), this.errorListener());
                queue.add(customRequest);
                customRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }
    private Response.ErrorListener errorListener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(PersonalAdd.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }

    private Response.Listener<JSONObject> success() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    Log.d("TAG", "onResponse: datasuccesss"+response.toString());
                    try {
                        if (response.getString("status").equals("true")){
                            Utillity.message(PersonalAdd.this, "Update successful");
                            finish();
                        } else {
                            Utillity.message(PersonalAdd.this, "Connection error");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utillity.message(PersonalAdd.this, getResources().getString(R.string.internetConnection));
                }
                Utillity.hidepopup();

            }
        };
    }


    private Response.Listener<JSONObject> sucesslistener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                gson=new Gson();
                CountryList countryList=null;

                try {
                    countryList=new CountryList();
                    countryList=gson.fromJson(response.toString(),CountryList.class);
                    boolean status=countryList.isStatus();
                    if(status==true)
                    {
                        countrylst=countryList.getCountry_list();
                        Log.d("List",""+countrylst);
                        Cname=new ArrayList<>();
                        Cid=new ArrayList<>();
                        for(int i=0;i<countrylst.size();i++)
                        {
                            String CName=countrylst.get(i).getCountry_name();
                            String CId=countrylst.get(i).getCountry_id();
                            Cname.add(CName);
                            Cid.add(CId);
                        }
                        locSpinner();
                        workPermitSpinner();

                    }
                } catch (Exception e) {
                    Utillity.message(getApplicationContext(),""+e);
                }
            }
        };
    }

    private void getcountrylist() {
        if(Utillity.isNetworkConnected(this)) {
            Utillity.showloadingpopup(PersonalAdd.this);
            HashMap<String,String> params=new HashMap<>();
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.COUNTRY,params,this.sucesslistener(),this.errorlistener());
            customRequest.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(customRequest);
        }
        else
        {
            Snackbar snackbar= Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarView=snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }



    private void workPermitSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row,Cname);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_workPermit.setAdapter(arrayAdapter);
        spinner_workPermit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                workCoutId = Cid.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void locSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row,Cname);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        select_location.setAdapter(arrayAdapter);
        select_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryId = Cid.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Response.ErrorListener errorlistener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utillity.hidepopup();
                Utillity.message(getApplicationContext(),""+error);
                Log.d("Error Respons",""+error);
            }
        };
    }

}
