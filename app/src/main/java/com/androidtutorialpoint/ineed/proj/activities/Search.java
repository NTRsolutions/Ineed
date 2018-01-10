package com.androidtutorialpoint.ineed.proj.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.CountryList;
import com.androidtutorialpoint.ineed.proj.models.SearchModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    String set,CountryId;
    LinearLayout linearLayout;
    ActionBar actionBar;
    Spinner select_country;
    List<CountryList.CountryListBean> countrylst=new ArrayList<>();
    ArrayList<String> Cname,Cid;
    List<SearchModel.ProfileListBean> searchlist=new ArrayList<>();
    RecyclerView recsearch;
    EditText et_search;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recsearch=findViewById(R.id.search_recy);
        et_search=findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(this);
       // linearLayout= (LinearLayout) findViewById(R.id.ll_linear);
        select_country=findViewById(R.id.sp_selectCountry);
       // linearLayout.setOnClickListener(this);
        Intent it=getIntent();
        set=it.getStringExtra("Login");
        getcountrylist();
    }

    private void getcountrylist() {
        if(Utillity.isNetworkConnected(this)) {
            Utillity.showloadingpopup(Search.this);
            HashMap<String,String> params=new HashMap<>();
            RequestQueue requestQueue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.COUNTRY,params,this.sucesslistener(),this.errorlistener());
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
                    Utillity.message(getApplicationContext(),""+e);
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
                    Utillity.message(getApplicationContext(), CountryId);
                }
                else
                {
                    CountryId="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Response.ErrorListener errorlistener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utillity.hidepopup();
                Utillity.message(getApplicationContext(),""+error);
                Log.d("Error Respons",""+error);
            }
        };
    }

    private void setupoverlay() {
        final Dialog dialog=new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.search_overlay_popup);
        RelativeLayout relativeLayout=(RelativeLayout)dialog.findViewById(R.id.rela_backgrnd);
        TextView textView=(TextView)dialog.findViewById(R.id.txt_msg);
        final Button job=(Button)dialog.findViewById(R.id.overjob);
        Button emp=(Button)dialog.findViewById(R.id.overemp);
        if(set.equalsIgnoreCase("search"))
        {
            relativeLayout.setBackgroundResource(R.drawable.card);
            textView.setText("Are you sure you want to view detail,it will deduct one credit from your account");
            job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it=new Intent(Search.this,DashboardActivity.class);
                    it.putExtra("Login","search");
                    startActivity(it);
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
        else if(set.equalsIgnoreCase("login"))
        {
            textView.setText("To view details please get Login");
            job.setText("Login");
            emp.setText("Cancel");
            job.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Search.this,LoginActivity.class));
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
        //setupoverlay();

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
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v.getId()==R.id.et_search)
        {
            InputMethodManager inputMethodManager= (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(v.getWindowToken(),0);
            search();

        }
        return false;
    }

    private void search() {
        if(Utillity.isNetworkConnected(this)) {
            Utillity.showloadingpopup(Search.this);
            HashMap<String,String> params=new HashMap<>();
            params.put("country",CountryId);
            params.put("key","php");
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
    private Response.Listener<JSONObject> sucess()
    {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                Gson gson=new Gson();
                SearchModel sModel=null;

                try {
                    sModel=new SearchModel();
                    sModel=gson.fromJson(response.toString(),SearchModel.class);
                    boolean status=sModel.isStatus();
                    if(status==true);
                    {
                        searchlist=sModel.getProfile_list();
                        Log.d("List",searchlist.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
