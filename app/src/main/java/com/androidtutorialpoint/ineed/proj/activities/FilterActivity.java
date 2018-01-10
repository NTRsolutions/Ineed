package com.androidtutorialpoint.ineed.proj.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import com.androidtutorialpoint.ineed.R;

public class FilterActivity extends AppCompatActivity {
    Spinner fl_jobtype,fl_experience,fl_noticeperiod,fl_ctc,fl_age,fl_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        fl_jobtype=findViewById(R.id.sp_jobtype);
        fl_experience=findViewById(R.id.sp_experience);
        fl_noticeperiod=findViewById(R.id.sp_noticeperiod);
        fl_ctc=findViewById(R.id.sp_ctc);
        fl_age=findViewById(R.id.sp_gender);
    }
}
