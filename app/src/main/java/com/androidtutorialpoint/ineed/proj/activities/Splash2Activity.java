package com.androidtutorialpoint.ineed.proj.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.Utils.Utillity;
import com.androidtutorialpoint.ineed.proj.models.LanguageModel;
import com.androidtutorialpoint.ineed.proj.adapters.LanguageAdapter;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

import java.util.ArrayList;
import java.util.Locale;

public class Splash2Activity extends AppCompatActivity {
    ArrayList<LanguageModel> langlist;
    LanguageAdapter languageAdapter;
    ProgressBar progressbar;
    Dialog dialog;
    String Language,LanguageId;
    ActionBar actionBar;
    TinyDB sharpref;
    Gson gson = new Gson();
    LoginData loginData = new LoginData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        Configuration config = getBaseContext().getResources().getConfiguration();
        sharpref=new TinyDB(getApplicationContext());
        Language=sharpref.getString("language_id");


//        if (sharpref.contains("login_data")){
//            startActivity(new Intent(Splash2Activity.this,HomeActivity.class));
//            finish();
//        }

        if(Language!=null && !Language.isEmpty())
        {
            if (! "".equals(Language) && ! config.locale.getLanguage().equals(Language)) {
                Locale locale = new Locale(Language);
                Locale.setDefault(locale);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_splash2);
            }
        }
        else
        {
            Showlanuageselctor();
        }

    }
    private void Showlanuageselctor() {
       /* final Dialog dialog=new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Select Language");
        dialog.setContentView(R.layout.language_selector);
        ListView listView=(ListView) dialog.findViewById(R.jobseekerid.list_language);
        progressbar=(ProgressBar) dialog.findViewById(R.jobseekerid.progressbar);
        languageAdapter =new LanguageAdapter(getApplicationContext(),langlist);
        listView.setAdapter(languageAdapter);
        dialog.show();*/
        langlist=new ArrayList<>();
        LanguageModel languageModel=new LanguageModel();
        languageModel.setId("en");
        languageModel.setLanguage("English");
        langlist.add(languageModel);
        LanguageModel languageModel1=new LanguageModel();
        languageModel1.setId("ar");
        languageModel1.setLanguage("Arabic");
        langlist.add(languageModel1);
        AlertDialog.Builder builder=new AlertDialog.Builder(Splash2Activity.this);
        LayoutInflater inflater=Splash2Activity.this.getLayoutInflater();
        View view=inflater.inflate(R.layout.language_selector,null);
        builder.setView(view);
        ListView listView=(ListView) view.findViewById(R.id.list_language);
        progressbar=(ProgressBar) view.findViewById(R.id.progressbar);
        languageAdapter =new LanguageAdapter(getApplicationContext(),langlist);
        listView.setAdapter(languageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Language=((TextView)view.findViewById(R.jobseekerid.txt_languagename)).getText().toString();
                LanguageId=((TextView)view.findViewById(R.id.txt_languageid)).getText().toString();
                sharpref.putString("language_id",LanguageId);
                addlanguage(LanguageId);
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();
    }

    private void addlanguage(String languageId) {
        Configuration configuration=getBaseContext().getResources().getConfiguration();
        Locale locale=new Locale(languageId);
        Locale.setDefault(locale);
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    public void Login(View view)
    {
        startActivity(new Intent(Splash2Activity.this,LoginActivity.class));
        finish();
    }
    public void Signup(View view)
    {
        startActivity(new Intent(Splash2Activity.this,SignUpActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setuptoolbar();
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Ineed");
        textView.setTextSize(26f);
        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setTitle("");
        }
    }
    public void onjobclick(View view)
    {
        startActivity(new Intent(Splash2Activity.this,LoginActivity.class));
        finish();
    }
}
