package com.androidtutorialpoint.ineed.proj.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.fragment.GalFragment;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new GalFragment()).commit();
    }
}
