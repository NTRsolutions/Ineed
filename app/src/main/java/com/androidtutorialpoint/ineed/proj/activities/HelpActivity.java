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
import com.android.volley.toolbox.Volley;
import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.adapters.HelpAdapter;
import com.androidtutorialpoint.ineed.proj.models.HelpCategoryModel;
import com.androidtutorialpoint.ineed.proj.webservices.ApiList;
import com.androidtutorialpoint.ineed.proj.webservices.CustomRequest;
import com.androidtutorialpoint.ineed.proj.webservices.VolleySingelton;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity implements HelpAdapter.Clicklisten {
    ActionBar actionBar;
    TinyDB tinyDB;
    String language;
    List<HelpCategoryModel.ResponseBean.HelpcatDataBean> listhelp;
    RecyclerView helplist;
    HelpAdapter helpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tinyDB = new TinyDB(getApplicationContext());
        language = tinyDB.getString("language_id");
        listhelp=new ArrayList<>();
        helplist=(RecyclerView) findViewById(R.id.recy_helpcat);
        helpAdapter=new HelpAdapter(this,listhelp);
        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager(this);
        helplist.setLayoutManager(mlayoutManager);
        helpAdapter.seclick(this);
        helplist.setAdapter(helpAdapter);
        setupApi();
        setuptoolbar();
    }

    private void setupApi() {
        if(Utillity.isNetworkConnected(HelpActivity.this)) {
            Utillity.showloadingpopup(HelpActivity.this);
            {
                RequestQueue queue = VolleySingelton.getsInstance().getmRequestQueue();
                HashMap<String, String> params = new HashMap<>();
                params.put("language", "en");
                CustomRequest customRequest = new CustomRequest(Request.Method.POST, ApiList.HELP_CATEGORY, params, this.success(), this.errorListener());
                queue.add(customRequest);
            }
        }
        else
        {
            Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),getResources().getString(R.string.internetConnection),Snackbar.LENGTH_LONG);
            View snackbarview=snackbar.getView();
            snackbarview.setBackgroundColor(getResources().getColor(R.color.appbasecolor));
            snackbar.show();
        }
    }
    private Response.Listener<JSONObject> success()
    {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Utillity.hidepopup();
                try {
                    HelpCategoryModel helpCategoryModel=new HelpCategoryModel();
                    Gson gson=new Gson();
                    helpCategoryModel=gson.fromJson(response.toString(),HelpCategoryModel.class);
                    boolean status=helpCategoryModel.isStatus();
                    if(status==true)
                    {
                        listhelp.addAll(helpCategoryModel.getResponse().getHelpcat_data());
                        helpAdapter.notifyDataSetChanged();
                    }
                    else
                    {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        };
    }
    private Response.ErrorListener errorListener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: "+error.toString());
                Utillity.message(HelpActivity.this, getResources().getString(R.string.internetConnection));
                Utillity.hidepopup();
            }
        };
    }
    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Help");
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
    public void itemclick(View v, int pos) {
             int id= Integer.parseInt(listhelp.get(pos).getHelpcat_id());
            Intent intent=new Intent(HelpActivity.this,HelpDetailActivity.class);
            intent.putExtra("idd",""+id);
            startActivity(intent);
            Utillity.message(getApplication(),""+id);
          //  startActivity(new Intent(HelpActivity.this,HelpDetailActivity.class));
    }
}
