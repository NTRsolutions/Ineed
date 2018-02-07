package com.androidtutorialpoint.ineed.proj.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.fragment.DashboardJobseeker;
import com.androidtutorialpoint.ineed.proj.fragment.JobseekerDashboardFragment;

public class JobseekerDetailActivity extends AppCompatActivity {
    ActionBar actionBar;
    String jobSeekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobseeker_detail);
        setuptoolbar();
        if (getIntent().hasExtra("id")){
            jobSeekerId = getIntent().getStringExtra("id");
        }
        Bundle bundle = new Bundle();

        bundle.putString("id", jobSeekerId);
// set Fragmentclass Arguments
        DashboardJobseeker fragobj = new DashboardJobseeker();
        fragobj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_jobseeker,
                fragobj).commit();

    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Jobseeker");
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
