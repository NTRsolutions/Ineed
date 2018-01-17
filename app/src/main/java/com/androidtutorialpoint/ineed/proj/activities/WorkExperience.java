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

public class WorkExperience extends AppCompatActivity implements View.OnClickListener {
    ActionBar actionBar;
    RadioButton currentRadio, previousRadio;
    LinearLayout bottom_toolbar, noticeLayout, toLayout;
    TextView txt_save,txt_cancel, txtFrom, txtTo;
    EditText edtJobtitle, edtCompanyName;
    String jobtitle="", noticeid = " ", indu ,noticePeriod=" ", departType="",indId =" ",id, companyName, workingfrom=" ", deptid=" ",workingTo=" ", userid,name, desi,
            empType="", salaryId="", jobtypeid="", jobTypename=" ", salaryName=" ";
    Spinner spinner_indus, spinner_notice, spinner_department, spinner_jobtype, spinner_salary;
    ArrayList<String> noticeName, noticeId, industryName, industryId, departmenyName, deptIdList,
            salaryList, salaryListid, jobtypeList, jobTypeIdList;
    List<AdminList.NoticeperiodsBean> noticeperiodsBeans = new ArrayList<>();
    List<AdminList.DepartmentsBean> departmentsBeans = new ArrayList<>();
    List<AdminList.IndustriesBean> industries = new ArrayList<>();
    List<AdminList.CtcsBean> ctcsBeans = new ArrayList<>();
    List<AdminList.JobtypesBean> jobtypesBeans = new ArrayList<>();
    LoginData loginData;
    Gson gson = new Gson();
    TinyDB tinyDB;
    AdminList adminList = new AdminList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);
        setuptoolbar();
        setupbottomtoolbar();
        tinyDB = new TinyDB(getApplicationContext());
        String loginPrefData = tinyDB.getString("login_data");
        loginData = gson.fromJson(loginPrefData, LoginData.class);
        userid = loginData.getUser_detail().getUser_id();



//        find id
        spinner_jobtype = findViewById(R.id.work_experience_spi_jobtype);
        spinner_salary = findViewById(R.id.work_experience_spi_salary);
        edtJobtitle = findViewById(R.id.edt_work_experienceJobtitle);
        edtCompanyName = findViewById(R.id.workexp_txt_CompanyName);
        spinner_department = findViewById(R.id.work_experience_spi_Department);
        spinner_indus = findViewById(R.id.work_experience_spin_Industry);
        spinner_notice = findViewById(R.id.work_experience_spin_Notice);
        txtFrom = findViewById(R.id.txt_work_experienceWorkFrom);
        txtTo = findViewById(R.id.txt_work_experienceWorkTo);
        currentRadio = findViewById(R.id.work_exp_radio_current);
        previousRadio = findViewById(R.id.work_exp_radio_pre);
        noticeLayout = findViewById(R.id.addexp_notice_layout);
        toLayout = findViewById(R.id.addexp_to_layout);

        if (getIntent().hasExtra("type")){
            empType = getIntent().getStringExtra("type");
            if (empType.equals("Current")){
                currentRadio.setChecked(true);
                previousRadio.setChecked(false);
                noticeLayout.setVisibility(View.VISIBLE);
                empType = "c";
            } else {
                empType = "p";
                previousRadio.setChecked(true);
                currentRadio.setChecked(false);
                noticeLayout.setVisibility(View.GONE);
                toLayout.setVisibility(View.VISIBLE);
            }
        }

        if (getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
        }
        if (getIntent().hasExtra("title")){
            edtJobtitle.setText(getIntent().getStringExtra("title"));
        }

        if (getIntent().hasExtra("compName")){
            edtCompanyName.setText(getIntent().getStringExtra("compName"));
        }

        if (getIntent().hasExtra("indu")){
            indu = getIntent().getStringExtra("indu");
        }

        if (getIntent().hasExtra("depart")){
            departType = getIntent().getStringExtra("depart");
        }
        if (getIntent().hasExtra("notice")){
            noticePeriod = getIntent().getStringExtra("notice");
        }

        getAdminList();
//        set onclick listener
        currentRadio.setOnClickListener(this);
        previousRadio.setOnClickListener(this);

        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(WorkExperience.this, dateTo, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(WorkExperience.this, date, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void getAdminList() {
        if(Utillity.isNetworkConnected(WorkExperience.this)) {
            Utillity.showloadingpopup(WorkExperience.this);
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
                    departmenyName = new ArrayList<>();
                    deptIdList = new ArrayList<>();
                    ctcsBeans = new ArrayList<>();
                    jobtypesBeans = new ArrayList<>();
                    jobTypeIdList = new ArrayList<>();
                    jobtypeList = new ArrayList<>();
                    salaryList = new ArrayList<>();
                    salaryListid = new ArrayList<>();

                    try {
                        if (response.getString("status").equals("true")){
//                            for notice
                            noticeperiodsBeans = adminList.getNoticeperiods();
                            noticeName =new ArrayList<>();
                            noticeId =new ArrayList<>();

                            for(int i=0;i<noticeperiodsBeans.size();i++)
                            {
                                String CName=noticeperiodsBeans.get(i).getNoticeperiod_name();
                                String CId=noticeperiodsBeans.get(i).getNoticeperiod_id();
                                noticeName.add(CName);
                                noticeId.add(CId);
                            }
                            notice();



                            departmentsBeans = adminList.getDepartments();
//                            for jobtype
                            jobtypesBeans = adminList.getJobtypes();
                            for(int i = 0; i< jobtypesBeans.size(); i++)
                            {
                                String CName= jobtypesBeans.get(i).getJobtype_name();
                                String CId= jobtypesBeans.get(i).getJobtype_id();
                                jobTypeIdList.add(CId);
                                jobtypeList.add(CName);
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

//                            for department
                            for(int i = 0; i< departmentsBeans.size(); i++)
                            {
                                String CName= departmentsBeans.get(i).getDepartment_name();
                                String CId= departmentsBeans.get(i).getDepartment_id();
                                departmenyName.add(CName);
                                deptIdList.add(CId);
                            }
                            deptSpinner();
//                            for industry

                            industries = adminList.getIndustries();
                            industryName = new ArrayList<>();
                            industryId = new ArrayList<>();
                            for(int i = 0; i< industries.size(); i++)
                            {
                                String CName= industries.get(i).getIndustry_name();
                                String CId= industries.get(i).getIndustry_id();
                                industryName.add(CName);
                                industryId.add(CId);
                            }
                            induSpinner();

                        } else {
                            Utillity.message(WorkExperience.this, "Connection error");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utillity.message(WorkExperience.this, getResources().getString(R.string.internetConnection));
                }
                Utillity.hidepopup();

            }
        };
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

    DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, monthOfYear);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelTo();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        workingfrom = sdf.format(mCalendar.getTime());
        txtFrom.setText(workingfrom);

    }

    private void updateLabelTo() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        workingTo = sdf.format(mCalendar.getTime());
        txtTo.setText(workingTo);

    }


    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Work Experience");
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }
    private void setupbottomtoolbar() {
        bottom_toolbar=findViewById(R.id.bottom_toolbar);
        txt_save=bottom_toolbar.findViewById(R.id.txt_save);
        txt_cancel=bottom_toolbar.findViewById(R.id.txt_cancel);
        txt_save.setOnClickListener(this);
        txt_cancel.setOnClickListener(this);
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

                companyName = edtCompanyName.getText().toString();
                jobtitle = edtJobtitle.getText().toString();
                if (!companyName.isEmpty()){
                    if (!jobtitle.isEmpty()){
                        if (empType.length()>0){
                            if (id==null){
                                saveApi();
                            } else {
                                editsaveApi();
                            }
                        } else {
                            Utillity.message(WorkExperience.this, "Please select job type");
                        }
                    } else {
                        Utillity.message(WorkExperience.this, "Please enter job title");
                    }
                } else {
                    Utillity.message(WorkExperience.this, "Please enter company name");
                }

                break;
            case R.id.txt_cancel:
                finish();
                break;
            case R.id.work_exp_radio_current:
                currentRadio.setChecked(true);
                previousRadio.setChecked(false);
                noticeLayout.setVisibility(View.VISIBLE);
                toLayout.setVisibility(View.GONE);
                empType= "c";
                break;
            case R.id.work_exp_radio_pre:
                currentRadio.setChecked(false);
                previousRadio.setChecked(true);
                noticeLayout.setVisibility(View.GONE);
                toLayout.setVisibility(View.VISIBLE);
                empType = "p";
                break;

        }
    }

    private void editsaveApi() {
        if(Utillity.isNetworkConnected(WorkExperience.this)) {
            Utillity.showloadingpopup(WorkExperience.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id",userid);
            params.put("employertype",empType);
            params.put("user_id",userid);
            params.put("job_title",jobtitle);
            params.put("company_name",companyName);
            params.put("working_from",workingfrom);
            params.put("working_to",workingTo);
            params.put("industry", indId);
            params.put("notice",noticeid);
            params.put("department",deptid);
            params.put("jobseeker_workexp_id", id);

            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_ADD_WORK,
                    params, this.success(), this.errorListener());
            queue.add(customRequest);
        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }

    private void saveApi() {
        if(Utillity.isNetworkConnected(WorkExperience.this)) {
            Utillity.showloadingpopup(WorkExperience.this);
            RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id",userid);
            params.put("employertype",empType);
            params.put("user_id",userid);
            params.put("job_title",jobtitle);
            params.put("company_name",companyName);
            params.put("working_from",workingfrom);
            params.put("working_to",workingTo);
            params.put("industry", indId);
            params.put("notice",noticeid);
            params.put("department",deptid);

            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.JOBSEEKER_ADD_WORK,
                    params, this.success(), this.errorListener());
            queue.add(customRequest);
        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(WorkExperience.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }

    private Response.Listener<JSONObject> success() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response!=null){
                    try {
                        if (response.getString("status").equals("true")){
                            Utillity.message(WorkExperience.this, "Experience added");
                            finish();
                        } else {
                            Utillity.message(WorkExperience.this, "Connection error");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void notice() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row,noticeName);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_notice.setAdapter(arrayAdapter);
        if (noticePeriod != null) {
            int spinnerPosition = arrayAdapter.getPosition(noticePeriod);
            spinner_notice.setSelection(spinnerPosition);
        }

        spinner_notice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noticeid = noticeId.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void induSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row, industryName);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_indus.setAdapter(arrayAdapter);
        if (indu != null) {
            int spinnerPosition = arrayAdapter.getPosition(indu);
            spinner_indus.setSelection(spinnerPosition);
        }

//        spinner_indus.setSelection(indu);
        spinner_indus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                indId = industryId.get(position);
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
//        if (departType != null) {
//            int spinnerPosition = arrayAdapter.getPosition(departType);
//            spinner_department.setSelection(spinnerPosition);
//        }

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


    private void jobTypeSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row, jobtypeList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_jobtype.setAdapter(arrayAdapter);
//        if (departType != null) {
//            int spinnerPosition = arrayAdapter.getPosition(departType);
//            spinner_department.setSelection(spinnerPosition);
//        }

        spinner_jobtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jobtypeid = jobTypeIdList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void deptSpinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_row, departmenyName);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_row);
        spinner_department.setAdapter(arrayAdapter);
        if (departType != null) {
            int spinnerPosition = arrayAdapter.getPosition(departType);
            spinner_department.setSelection(spinnerPosition);
        }

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptid = deptIdList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
