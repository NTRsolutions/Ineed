package com.androidtutorialpoint.ineed.proj.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.adapters.HelpListAdapter;
import com.androidtutorialpoint.ineed.proj.models.HelpTitle;
import com.androidtutorialpoint.ineed.proj.models.HelpTitleDetail;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpDetailActivity extends AppCompatActivity {
    RecyclerView rview;
    HelpListAdapter listAdapter;
    ActionBar actionBar;
    List<HelpTitle> helpTitles=new ArrayList<>();;
   String Id;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_detail);
        setuptoolbar();
        rview=(RecyclerView) findViewById(R.id.recy_helpdetail);
        intent=getIntent();
        //Bundle extra=getIntent().getExtras();
       // Id=extra.getString("id");
       // Intent extras = getIntent().getExtras();
        intent=getIntent();
        if(intent.hasExtra("idd"))
        {
             Id=intent.getStringExtra("idd");
            Log.d("Muhib",Id);
        }
        getTitles();

    }

    private void getTitles() {
      /*  helpTitles = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<HelpTitleDetail> details = new ArrayList<>();
            details.add(new HelpTitleDetail("jkl"));
            helpTitles.add(new HelpTitle("Ist", details));
        }*/
        if(Utillity.isNetworkConnected(this))
        {
            Utillity.showloadingpopup(HelpDetailActivity.this);
            HashMap<String,String> params=new HashMap<>();
            params.put("language","en");
            params.put("help_category_id",Id);
            RequestQueue queue= VolleySingelton.getsInstance().getmRequestQueue();
            CustomRequest customRequest=new CustomRequest(Request.Method.POST, ApiList.HELP,params,this.sucess(),this.error());
            queue.add(customRequest);
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
                if(helpTitles!=null)
                {
                    helpTitles.clear();
                }
                try {
                    Boolean staus= response.getBoolean("status");
                    if(staus==true)
                    {
                        JSONObject object=response.getJSONObject("response");
                        JSONArray jsonArray=object.getJSONArray("help_data");
                        helpTitles = new ArrayList<>();

                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            List<HelpTitleDetail> details = new ArrayList<>();
                            details.add(new HelpTitleDetail(jsonObject.getString("help_language_answer")));
                            helpTitles.add(new HelpTitle(jsonObject.getString("help_language_question"), details));
                            Log.d("ob",""+object);

                        }
                        listAdapter=new HelpListAdapter(helpTitles);
                        rview.setLayoutManager(new LinearLayoutManager(HelpDetailActivity.this));
                        rview.setAdapter(listAdapter);
                    }
                    else
                    {
                        String msg=response.getString("msg");
                        Utillity.message(HelpDetailActivity.this,msg);
                        //Log.d("staus",""+staus);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
    private Response.ErrorListener error()
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
    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Help Detail");
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
}
