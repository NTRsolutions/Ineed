package com.androidtutorialpoint.ineed.proj.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.adapters.SearchAdapter;
import com.androidtutorialpoint.ineed.proj.fragment.DashboardJobseeker;
import com.androidtutorialpoint.ineed.proj.models.CountryList;
import com.androidtutorialpoint.ineed.proj.models.SearchModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Search extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener, SearchAdapter.Clickitem {
    private static final int FILTER_REQUEST = 222 ;
    String set="",CountryId;
    LinearLayout linearLayout;
    ActionBar actionBar;
    Spinner select_country;
    List<CountryList.CountryListBean> countrylst=new ArrayList<>();
    ArrayList<String> Cname,Cid;
    List<SearchModel.ProfileListBean> searchlist=new ArrayList<>();
    RecyclerView recsearch;
    EditText et_search;
    Gson gson;
    SearchAdapter searchAdapte;
    Button txt_filter;
    ArrayList<SearchModel.ProfileListBean> filtserch = new ArrayList<>();
    int value=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recsearch=findViewById(R.id.search_recy);
        et_search=findViewById(R.id.et_search);
        txt_filter=findViewById(R.id.txt_filter);
        txt_filter.setOnClickListener(this);
        et_search.setOnEditorActionListener(this);
        // linearLayout= (LinearLayout) findViewById(R.jobseekerid.ll_linear);
        select_country=findViewById(R.id.sp_selectCountry);
        // linearLayout.setOnClickListener(this);
        Intent it=getIntent();
        set=it.getStringExtra("Login");
        getcountrylist();
        searchAdapte = new SearchAdapter(getApplicationContext(), searchlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recsearch.setLayoutManager(layoutManager);
        recsearch.setAdapter(searchAdapte);
        searchAdapte.setclick(this);
        search();
    }

    private void getcountrylist() {
        if(Utillity.isNetworkConnected(this)) {
            Utillity.showloadingpopup(Search.this);
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

    @Override
    protected void onResume() {
        super.onResume();
        setuptoolbar();
    }

    private Response.Listener<JSONObject> sucesslistener()
    {
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
                        Cname.add(0,"Select Country");
                        Cid.add(0,"0");
                        for(int i=0;i<countrylst.size();i++)
                        {
                            String CName=countrylst.get(i).getCountry_name();
                            String CId=countrylst.get(i).getCountry_id();
                            Cname.add(CName);
                            Cid.add(CId);
                        }
                        handlespinner();
                    }
                } catch (Exception e) {
                    Utillity.message(getApplicationContext(),getResources().getString(R.string.internetConnection));
                }
            }
        };
    }

    private void handlespinner() {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinnerdesign,Cname);
        arrayAdapter.setDropDownViewResource(R.layout.spinnerdesign);
        select_country.setAdapter(arrayAdapter);
        select_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    CountryId = Cid.get(position);
                 //   Utillity.message(getApplicationContext(), CountryId);
                }
                else
                {
                    CountryId="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CountryId="";

            }
        });
    }

    private Response.ErrorListener errorlistener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utillity.hidepopup();
                Utillity.message(getApplicationContext(),getResources().getString(R.string.internetConnection));

                Log.d("Error Respons",""+error);
            }
        };
    }

    private void
    setupoverlay() {
        final Dialog dialog=new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_overlay_popup);
        RelativeLayout relativeLayout=(RelativeLayout)dialog.findViewById(R.id.rela_backgrnd);
        TextView textView=(TextView)dialog.findViewById(R.id.txt_msg);
        final Button job=(Button)dialog.findViewById(R.id.overjob);
        Button emp=(Button)dialog.findViewById(R.id.overemp);
        /*if(set.equalsIgnoreCase("search"))
        {
            relativeLayout.setBackgroundResource(R.drawable.card);
            textView.setText("Are you sure you want to view detail,it will deduct one credit from your account");
            job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getViewed(jobseekerid);
                    startActivity(new Intent(Search.this,
                            JobseekerDetailActivity.class).putExtra("id",jobseekerid));
                    dialog.dismiss();
                }
            });
            emp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        else */if(set.equalsIgnoreCase("login"))
        {
            textView.setText("To view details please get Login");
            job.setText("Login");
            emp.setText("Cancel");
            job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Search.this,LoginActivity.class)
                            .putExtra("search","search").putExtra("id", jobseekerid));
                    finish();
                    dialog.dismiss();
                }
            });
            emp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });


        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);


        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_filter:
                //  startActivity(new Intent(Search.this,FilterActivity.class));
                value=0;
                startActivityForResult(new Intent(Search.this,FilterActivity.class),FILTER_REQUEST);
                break;
        }
        //setupoverlay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FILTER_REQUEST) {

        /*    String Gender=data.getStringExtra("gender");
            Log.d("gender",Gender);
            searchAdapte.getFilter().filter(Gender);*/
            if(data!=null) {
                String experi = data.getStringExtra("experi");
                String ctc = data.getStringExtra("ctc");
                String Age = data.getStringExtra("age");
                String Gender = data.getStringExtra("gender");

                if (!experi.isEmpty()) {
                    experfilter(experi);
                }

                if (!ctc.isEmpty()) {
                    ctcfilter(ctc);
                }

                if (!Age.isEmpty()) {
                    agefiter(Age);
                }

                if (!Gender.isEmpty()) {
                    genderfilter(Gender);

                }
                Log.d("Filter", "" + filtserch.size());
                if (Gender.isEmpty() && experi.isEmpty() && ctc.isEmpty() && Age.isEmpty()) {
                    this.searchlist = searchlist;
                    searchAdapte.notifyDataSetChanged();
                    Toast.makeText(this, "Nothing was found", Toast.LENGTH_SHORT).show();
                } else {
                    if (filtserch.size()>0 )
                    {
                        Set<SearchModel.ProfileListBean> as=new HashSet<>();
                        as.addAll(filtserch);
                        filtserch.clear();
                        filtserch.addAll(as);
                        searchlist.clear();
                        searchlist.addAll(filtserch);
                        searchAdapte.notifyDataSetChanged();
                    }
                    else
                    {
                        Utillity.message(this,"No record Found");
                        if(txt_filter.getVisibility()==View.VISIBLE)
                        {
                            txt_filter.setVisibility(View.GONE);
                        }
                        searchlist.clear();
                        value=0;
                        searchAdapte.notifyDataSetChanged();
                    }
                }
            /*if (!experi.isEmpty() && !ctc.isEmpty() && !Age.isEmpty() && !Gender.isEmpty())
            {
                experfilter(experi);
                ctcfilter(ctc);
                agefiter(Age);
                genderfilter(Gender);
            }
            else if (!Gender.isEmpty() && experi.isEmpty() && ctc.isEmpty() && Age.isEmpty())
            {
                searchAdapte.getFilter().filter(Gender);
            }*/
            }
        }

    }
    private void experfilter(String experi) {
        String[] exp=experi.split("-");
        String Efirst=exp[0];
        String Esecong=exp[1];
        Log.d("first",Efirst);
        Log.d("last",Esecong);
        Log.d("exp",experi);
        int startExp= Integer.parseInt(Efirst);
        int endExp= Integer.parseInt(Esecong);
        if(value<=0) {
            if (filtserch.size()>0 ) {
                ArrayList<SearchModel.ProfileListBean> tab = new ArrayList<>();

                for(int i=0;i<filtserch.size();i++)
                {
                    SearchModel.ProfileListBean sModel=filtserch.get(i);
                    if(!experi.isEmpty())
                    {
                        String fi=sModel.getTotalExperienceyear();
                        String se=sModel.getTotalExperiencemonths();

                        if(fi==null) {
                            fi = "0";
                        }
                        if(se==null) {
                            se = "0";
                        }
                        String ex=fi+"."+se;
                        float exps= Float.parseFloat(ex);
                        if(exps>=startExp && exps<=endExp)
                        {
                            tab.add(sModel);
                        }
                    }
                }
                filtserch.clear();
                filtserch.addAll(tab);
            }
            else
            {
                for(int i=0;i<searchlist.size();i++)
                {
                    SearchModel.ProfileListBean sModel=searchlist.get(i);
                    if(!experi.isEmpty())
                    {
                        String fi=sModel.getTotalExperienceyear();
                        String se=sModel.getTotalExperiencemonths();

                        if(fi==null) {
                            fi = "0";
                        }
                        if(se==null) {
                            se = "0";
                        }
                        String ex=fi+"."+se;
                        float exps= Float.parseFloat(ex);
                        if(exps>=startExp && exps<=endExp)
                        {
                            filtserch.add(sModel);
                        }
                    }
                }
            }
            if(filtserch.size()<=0)
            {
                value=1;
            }
        }
        else
        {
            value=1;
        }
    }
    private void ctcfilter(String ctc) {
        //CTC Filter
        /*String[] ctcs = ctc.split("-");
        String Cfirst = ctcs[0];
        String Csecong = ctcs[1];
        Log.d("first", Cfirst);
        Log.d("last", Csecong);
        Log.d("ctc", ctc);
        int startCtc = Integer.parseInt(Cfirst);
        int endCtc = Integer.parseInt(Csecong);*/
        if(value<=0) {
            if (filtserch.size()>0 ){
                ArrayList<SearchModel.ProfileListBean> tab = new ArrayList<>();
                for (int i = 0; i < filtserch.size(); i++) {
                    SearchModel.ProfileListBean sModel = filtserch.get(i);
                    if (!ctc.isEmpty()) {
                        String ex = sModel.getUser_ctc();
                        if (ex == null) {
                            ex = "0-0";
                        }
                        if (ctc.equalsIgnoreCase(ex)) {
                            tab.add(sModel);
                        }
                    }
                }
                filtserch.clear();
                filtserch.addAll(tab);
            }
            else {
                for (int i = 0; i < searchlist.size(); i++) {
                    SearchModel.ProfileListBean sModel = searchlist.get(i);
                    if (!ctc.isEmpty()) {
                        String ex = sModel.getUser_ctc();
                        // int ctcss= Integer.parseInt(ex);
                        //  if(ctcss>startCtc && ctcss<endCtc)
                        if (ex == null) {
                            ex = "0-0";
                        }
                        if (ctc.equalsIgnoreCase(ex)) {
                            filtserch.add(sModel);
                        }
                    }
                }
            }
            if(filtserch.size()<=0)
            {
                value=2;
            }
        }
        else
        {
            value=2;
        }
    }
    private void genderfilter(String Gender) {

        Log.d("gender",Gender);
        if(value<=0) {

            if (filtserch.size()>0 ){
                ArrayList<SearchModel.ProfileListBean> tab = new ArrayList<>();
                for (int i = 0; i < filtserch.size(); i++) {
                    SearchModel.ProfileListBean sModel = filtserch.get(i);
                    if (!Gender.isEmpty()) {
                        String gen = sModel.getUser_gender();
                        if (gen.equalsIgnoreCase(Gender)) {
                            tab.add(sModel);
                        }
                    }
                }
                filtserch.clear();
                filtserch.addAll(tab);
            }
            else
            {
                for (int i = 0; i < searchlist.size(); i++) {
                    SearchModel.ProfileListBean sModel = searchlist.get(i);
                    if (!Gender.isEmpty()) {
                        String gen = sModel.getUser_gender();
                        if (gen.equalsIgnoreCase(Gender))
                        {
                            if (!filtserch.contains(sModel.getUser_id())) {
                                filtserch.add(sModel);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            value=4;
        }
    }
    private void agefiter(String Age) {
          /*AGE Filter*/
        String[] sp = Age.split("-");
        String first = sp[0];
        String secong = sp[1];
        Log.d("Ages", first);
        Log.d("Ages", secong);
        Log.d("age", Age);
        int startAge = Integer.parseInt(first);
        int endAge = Integer.parseInt(secong);
        if(value<=0) {
            if (filtserch.size()>0 ) {
                ArrayList<SearchModel.ProfileListBean> tab = new ArrayList<>();

                for (int i = 0; i < filtserch.size(); i++) {
                    SearchModel.ProfileListBean sModel = filtserch.get(i);
                    if (!Age.isEmpty()) {
                        String ag = sModel.getUser_age();
                        int age = Integer.parseInt(ag);
                        if (age > startAge && age < endAge) {
                            tab.add(sModel);
                        }
                    }
                }
                filtserch.clear();
                filtserch.addAll(tab);
            } else {
                for (int i = 0; i < searchlist.size(); i++) {
                    SearchModel.ProfileListBean sModel = searchlist.get(i);
                    if (!Age.isEmpty()) {
                        String ag = sModel.getUser_age();
                        int age = Integer.parseInt(ag);
                        if (age > startAge && age < endAge) {
                            filtserch.add(sModel);
                        }
                    }
                }
            }
            if(filtserch.size()<=0)
            {
                value=3;
            }
        }
        else
        {
            value=3;
        }
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText(R.string.search1);
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
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v.getId()==R.id.et_search)
        {
            Utillity.hideSoftKeyboard(Search.this);
            search();
        }
        return false;
    }

    public void getViewed(String jobseekerid) {
        if (Utillity.isNetworkConnected(this)) {
            Utillity.showloadingpopup(Search.this);
            HashMap<String, String> params = new HashMap<>();
            params.put("employer_id", HomeActivity.userid);
            params.put("user_id", jobseekerid);
            RequestQueue requestQueue = VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.EMP_JOBSEEKER_VIEWED,
                    params, this.viewedsucess(), this.errorlistener());
            customRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(customRequest);
        }
    }

    private void search() {
        if(Utillity.isNetworkConnected(this)) {
            if(searchlist!=null)
            {
                searchlist.clear();
            }
            String searchval=et_search.getText().toString();
            Utillity.showloadingpopup(Search.this);
            HashMap<String,String> params=new HashMap<>();
            if(CountryId==null)
            {
                CountryId="";
            }
            params.put("country",CountryId);
            params.put("key",searchval);
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.SEARCH,params,this.sucess(),this.errorlistener());
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
    private Response.Listener<JSONObject> sucess() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(searchlist!=null)
                {
                    searchlist.clear();
                }
                Utillity.hidepopup();
                Gson gson=new Gson();
                SearchModel sModel=null;
                try {
                    sModel=new SearchModel();
                    sModel=gson.fromJson(response.toString(),SearchModel.class);
                    boolean status=sModel.isStatus();
                    if(status==true);
                    {
                        searchlist.addAll(sModel.getProfile_list());
                        Log.d("List", searchlist.toString());
                        if(searchlist.size()>0) {
                            txt_filter.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Utillity.message(getApplicationContext(),"No record Found Data");
                        }
                    }
                    searchAdapte.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Response.Listener<JSONObject> viewedsucess() {
        return new Response.Listener<JSONObject>() {
            @Override
            /*{"msg":"all ready viewed"}*/
            public void onResponse(JSONObject response) {
                if (response!=null){
                    try {
                        String msg = response.getString("msg");
                        if (!msg.equals("all ready viewed")) {
                            final Dialog dialog = new Dialog(Search.this, android.R.style.Theme_Translucent_NoTitleBar);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.search_overlay_popup);
                            RelativeLayout relativeLayout = (RelativeLayout) dialog.findViewById(R.id.rela_backgrnd);
                            TextView textView = (TextView) dialog.findViewById(R.id.txt_msg);
                            final Button job = (Button) dialog.findViewById(R.id.overjob);
                            Button emp = (Button) dialog.findViewById(R.id.overemp);

                                relativeLayout.setBackgroundResource(R.drawable.card);
                                textView.setText("Are you sure you want to view details, It will deduct one credit from your account");
                                job.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(Search.this,
                                                JobseekerDetailActivity.class).putExtra("id", jobseekerid));
                                        dialog.dismiss();
                                    }
                                });
                            emp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            dialog.show();
                        } else {
                            startActivity(new Intent(Search.this,
                                    JobseekerDetailActivity.class).putExtra("id", jobseekerid));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TAG", "onResponse: "+response.toString());
                Utillity.hidepopup();
                Gson gson=new Gson();

            }
        };
    }
    public String jobseekerid;

    @Override
    public void itemclick(View v, int position) {
        if(set.equalsIgnoreCase("login")) {
            jobseekerid = searchlist.get(position).getUser_id();
            setupoverlay();
        }
        else
        {
            jobseekerid = searchlist.get(position).getUser_id();
            getViewed(jobseekerid);

        }
    }
}
