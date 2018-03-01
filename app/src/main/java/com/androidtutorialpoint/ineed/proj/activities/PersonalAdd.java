package com.androidtutorialpoint.ineed.proj.activities;

import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PersonalAdd extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    Toolbar toolbar;
    String CountryId, userEmail,exp="", locid,salary,age, expYear, gender="", userid, workPermit,nationalityId,
            name, desi, no,currentcomp="", dob,jobType, jobtypeid, salaryId,permitCountry,permitCountryId="",
            workpermitcount="", expMonth;
    LinearLayout bottom_toolbar;
    TextView txt_save,txt_cancel,edDob;
    EditText edtName, edtDesig,edtCurentComp, edtNo,  edtNationality;
    TinyDB tinyDB;
    RadioButton workPermitYes, workPermitNo, maleRadioButton, femaleRadioButton;
    Spinner select_location, spinner_workPermit, spinner_expYear,spinner_jobtype, spinner_expMonth,spinner_salary;
    ArrayList<String> Cname,Cid, expyearList, expMonthList, salaryList, salaryListid,jobTypeList, jobtypeListid;
    LoginData loginData;
    AdminList adminList = new AdminList();
    List<AdminList.CtcsBean> ctcsBeans = new ArrayList<>();
    List<AdminList.JobtypesBean> jobtypesBeans = new ArrayList<>();
    List<CountryList.CountryListBean> countrylst=new ArrayList<>();

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_add);
        setuptoolbar();
        setupbottomtoolbar();
        expMonthList = new ArrayList<>();
        expyearList = new ArrayList<>();
        tinyDB = new TinyDB(getApplicationContext());
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userid = loginData.getUser_detail().getUser_id();
        userEmail = loginData.getUser_detail().getUser_email();

        expyearList.add(0,"Select year");
        for (int i=0; i<40; i++){
            expyearList.add(String.valueOf(i));
        }

        expMonthList.add(0,"Select month");
        for (int i=0; i<12; i++){
            expMonthList.add(String.valueOf(i));
        }

        //        get intent
        jobType = getIntent().getStringExtra("jobType");
        name  = getIntent().getStringExtra("name");
        age  = getIntent().getStringExtra("age");
        desi  = getIntent().getStringExtra("desi");
        exp  = getIntent().getStringExtra("exp");
        locid= getIntent().getStringExtra("loc");
        salary  = getIntent().getStringExtra("salary");
        no = getIntent().getStringExtra("mobile");
        nationalityId = getIntent().getStringExtra("nat");
        gender = getIntent().getStringExtra("gender");
        if (gender.length()==0){
            gender = "male";
        }
        dob = getIntent().getStringExtra("dob");
        jobtypeid = getIntent().getStringExtra("jobTypeid");
        currentcomp = getIntent().getStringExtra("currentComp");

        if (getIntent().hasExtra("workpermit")){
            workPermit = getIntent().getStringExtra("workpermit");
        }
        if (getIntent().hasExtra("permitCountry")){
            permitCountry = getIntent().getStringExtra("permitCountry");
        }
        if (getIntent().hasExtra("expYear")){
            expYear = getIntent().getStringExtra("expYear");
        }
        if (getIntent().hasExtra("expMonth")){
            expMonth = getIntent().getStringExtra("expMonth");
        }

//        find jobseekerid
        edtCurentComp = (EditText) findViewById(R.id.edt_personal_curent);
        select_location = (Spinner)findViewById(R.id.spinner_location);
        spinner_workPermit = (Spinner)findViewById(R.id.permit_country_spinner);
//        spinner_age = findViewById(R.jobseekerid.spinner_age);
        spinner_expYear = (Spinner)findViewById(R.id.edit_personal_year);
        spinner_expMonth = (Spinner)findViewById(R.id.edit_personal_month);
        spinner_salary =(Spinner) findViewById(R.id.spinner_salary);
        workPermitYes = (RadioButton) findViewById(R.id.radio_work_permit_yes);
        workPermitNo =(RadioButton) findViewById(R.id.radio_work_permit_no);
        maleRadioButton = (RadioButton)findViewById(R.id.radio_gender_male);
        femaleRadioButton = (RadioButton)findViewById(R.id.radio_gender_female);
        edtNationality = (EditText) findViewById(R.id.spinner_nationality);
        edtName =  (EditText)findViewById(R.id.txt_personal_edtname);
        edtDesig =  (EditText)findViewById(R.id.edt_personal_desi);
        edtNo = (EditText) findViewById(R.id.edt_personal_no);
        edDob =  (TextView) findViewById(R.id.edt_dob);
        spinner_jobtype = (Spinner)findViewById(R.id.spinner_jobtype);

//        set value

        edtNationality.setText(nationalityId);
        edtName.setText(name);
        edtNo.setText(no);
        edDob.setText(age);
        edtDesig.setText(desi);
        if (workPermit.equals("yes")){
            workPermit = "yes";
            workPermitYes.setChecked(true);
            workPermitNo.setChecked(false);
            spinner_workPermit.setVisibility(View.VISIBLE);
        } else {
            workPermit = "no";
            permitCountryId="";
            workPermitNo.setChecked(true);
            workPermitYes.setChecked(false);
            spinner_workPermit.setVisibility(View.GONE);
        }

        if (gender!=null&&gender.length()>0){
            if (gender.equals("Male")){
                maleRadioButton.setChecked(true);
                femaleRadioButton.setChecked(false);
            } else if (gender.equals("Female")){
                femaleRadioButton.setChecked(true);
                maleRadioButton.setChecked(false);
            }
        }
        edDob.setText(dob);
        edtCurentComp.setText(currentcomp);
        workPermitNo.setOnClickListener(this);
        workPermitYes.setOnClickListener(this);
        maleRadioButton.setOnClickListener(this);
        edDob.setOnClickListener(this);
        femaleRadioButton.setOnClickListener(this);

        getcountrylist();
        expmonthSpinner();
        expyearSpinner();
        getAdminList();
    }

    private void setupbottomtoolbar() {
        bottom_toolbar=(LinearLayout) findViewById(R.id.bottom_toolbar);
        txt_save=(TextView)bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=(TextView)bottom_toolbar.findViewById(R.id.txt_cancel);
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
                Utillity.hideSoftKeyboard(PersonalAdd.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getAdminList() {
        if(Utillity.isNetworkConnected(PersonalAdd.this)) {
            Utillity.showloadingpopup(PersonalAdd.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();

            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_ADMIN_LIST, params, this.successAdmin(), this.errorListener());
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


    private Response.Listener<JSONObject> successAdmin() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    Log.d("TAG", "onResponse: datasuccesss"+response.toString());
                    adminList = gson.fromJson(response.toString(), AdminList.class);
                    ctcsBeans = new ArrayList<>();
                    jobtypesBeans = new ArrayList<>();
                    salaryList = new ArrayList<>();
                    salaryListid = new ArrayList<>();
                    jobTypeList = new ArrayList<>();
                    jobtypeListid = new ArrayList<>();
                    try {
                        if (response.getString("status").equals("true")){
//                            for jobtypeid
                            jobtypesBeans = adminList.getJobtypes();
                            for(int i = 0; i< jobtypesBeans.size(); i++)
                            {
                                String CName= jobtypesBeans.get(i).getJobtype_name();
                                String CId= jobtypesBeans.get(i).getJobtype_id();
                                jobtypeListid.add(CId);
                                jobTypeList.add(CName);
                            }
                            jobTypeSpinner();

//                            for ctc
                            ctcsBeans = adminList.getCtcs();
                            for(int i = 0; i< ctcsBeans.size(); i++)
                            {
                                String CName= ctcsBeans.get(i).getCtc_name();
                                String CId= ctcsBeans.get(i).getCtc_id();
                                salaryListid.add(CId);
                                salaryList.add(CName);
                            }
                            salarySpinner();

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


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_save:
                name = edtName.getText().toString().trim();
                desi = edtDesig.getText().toString().trim();
                no = edtNo.getText().toString().trim();
                age = edDob.getText().toString().trim();
                currentcomp =edtCurentComp.getText().toString().trim();
                nationalityId = edtNationality.getText().toString().trim();
               if (!name.isEmpty()){
                   if (!desi.isEmpty()){
                       if (Utillity.CheckPhone(no)){
                           Utillity.hideSoftKeyboard(PersonalAdd.this);
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

                break;
            case R.id.txt_cancel:

                finish();
                break;
            case R.id.edt_dob:
                new DatePickerDialog(PersonalAdd.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.radio_gender_male:
                gender = "Male";
                femaleRadioButton.setChecked(false);
                break;
            case R.id.radio_gender_female:
                gender = "Female";
                maleRadioButton.setChecked(false);
                break;
            case R.id.radio_work_permit_no:
                workPermitYes.setChecked(false);
                spinner_workPermit.setVisibility(View.GONE);
                permitCountryId="";
                workPermit = "no";
                break;
            case R.id.radio_work_permit_yes:
                workPermit = "yes";
                workPermitNo.setChecked(false);
                spinner_workPermit.setVisibility(View.VISIBLE);
                break;
        }
    }
    Calendar mCalendar = Calendar.getInstance();
    int year, monthOfYear, dayOfMonth;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob = sdf.format(mCalendar.getTime());
        edDob.setText(dob);

    }

    private void saveData() {
        if(Utillity.isNetworkConnected(PersonalAdd.this)) {
            Utillity.showloadingpopup(PersonalAdd.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id",userid);
            params.put("name",name);
            params.put("designation",desi);
            params.put("nationality",nationalityId);
            params.put("workpermit",workPermit);
            params.put("experience",exp);
            params.put("current_location",CountryId);
            params.put("salary",salaryId);
            params.put("phone",no);
            params.put("gender",gender);
            params.put("email",userEmail);
            params.put("permit_country", permitCountryId);
            params.put("totalexpyear", expYear);
            params.put("totalexpmonths", expMonth);
            params.put("dob",dob);
            params.put("current_company",currentcomp);
            params.put("job_type", jobtypeid);

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

    private void jobTypeSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row, jobTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_jobtype.setAdapter(arrayAdapter);
        if (jobtypeid != null) {
            int spinnerPosition = arrayAdapter.getPosition(jobtypeid);
            spinner_jobtype.setSelection(spinnerPosition);
        }

        spinner_jobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobtypeid = jobtypeListid.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void salarySpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row, salaryList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_salary.setAdapter(arrayAdapter);
        if (salary != null) {
            int spinnerPosition = arrayAdapter.getPosition(salary);
            spinner_salary.setSelection(spinnerPosition);
        }

        spinner_salary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salaryId = salaryListid.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Utillity.hideSoftKeyboard(PersonalAdd.this);
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
        if (permitCountry != null) {
            int spinnerPosition = arrayAdapter.getPosition(permitCountry);
            spinner_workPermit.setSelection(spinnerPosition);
        }
        spinner_workPermit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                permitCountryId = Cid.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void expyearSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row,expyearList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_expYear.setAdapter(arrayAdapter);
        if (expYear != null) {
            int spinnerPosition = arrayAdapter.getPosition(expYear);
            spinner_expYear.setSelection(spinnerPosition);
        }
        spinner_expYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expYear = (String) spinner_expYear.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void expmonthSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row,expMonthList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_expMonth.setAdapter(arrayAdapter);
        if (expMonth != null) {
            int spinnerPosition = arrayAdapter.getPosition(expMonth);
            spinner_expMonth.setSelection(spinnerPosition);
        }
        spinner_expMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expMonth = (String) spinner_expYear.getItemAtPosition(position);
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
        if (locid != null) {
            int spinnerPosition = arrayAdapter.getPosition(locid);
            select_location.setSelection(spinnerPosition);
        }
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
