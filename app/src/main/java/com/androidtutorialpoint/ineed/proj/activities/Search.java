package com.androidtutorialpoint.ineed.proj.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;

public class Search extends AppCompatActivity implements View.OnClickListener {
    String set;
    LinearLayout linearLayout;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        linearLayout= (LinearLayout) findViewById(R.id.ll_linear);
        linearLayout.setOnClickListener(this);
        Intent it=getIntent();
        set=it.getStringExtra("Login");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setuptoolbar();

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
        setupoverlay();

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
}
