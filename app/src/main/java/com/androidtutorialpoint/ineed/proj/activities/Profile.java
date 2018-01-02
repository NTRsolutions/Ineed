package com.androidtutorialpoint.ineed.proj.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialpoint.ineed.R;

public class Profile extends AppCompatActivity {
TextView txt_proftitle,txt_personal;
    EditText etEmail,etcontact,etcompany,etdesignation,etexperience,etresume,etdob,etgender,etlocation,etskills;
    LinearLayout ll_savecancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txt_proftitle= (TextView) findViewById(R.id.txt_proftitle);

        etEmail= (EditText) findViewById(R.id.et_email);
        etcontact= (EditText) findViewById(R.id.et_contact);
        etcompany=(EditText) findViewById(R.id.et_company);
        etdesignation=(EditText) findViewById(R.id.et_designation);
        etexperience=(EditText) findViewById(R.id.et_experience);
        etresume=(EditText) findViewById(R.id.et_resume);
        etdob=(EditText) findViewById(R.id.et_dob);
        etgender=(EditText) findViewById(R.id.et_gender);
        etlocation=(EditText) findViewById(R.id.et_loc);
        etskills=(EditText) findViewById(R.id.et_skills);

        ll_savecancel= (LinearLayout) findViewById(R.id.ll_savecancel);

        etEmail.setEnabled(false);
        etcontact.setEnabled(false);
        etcompany.setEnabled(false);
        etdesignation.setEnabled(false);
        etexperience.setEnabled(false);
        etresume.setEnabled(false);
        etdob.setEnabled(false);
        etgender.setEnabled(false);
        etlocation.setEnabled(false);
        etskills.setEnabled(false);

        txt_personal= (TextView) findViewById(R.id.txt_personal);
        txt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(getApplication(),"Hello",Toast.LENGTH_SHORT).show();
            }
        });
        txt_personal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickonDrawable(v,event);
                return false;
            }
        });
        txt_proftitle.setMovementMethod(new ScrollingMovementMethod());
    }
    boolean clickonDrawable(View v,MotionEvent event)
    {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(event.getRawX() >= (txt_personal.getRight() - txt_personal.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                etEmail.setEnabled(true);
                etcontact.setEnabled(true);
                etcompany.setEnabled(true);
                etdesignation.setEnabled(true);
                etexperience.setEnabled(true);
                etresume.setEnabled(true);
                etdob.setEnabled(true);
                etgender.setEnabled(true);
                etlocation.setEnabled(true);
                etskills.setEnabled(true);
                ll_savecancel.setVisibility(View.VISIBLE);
            }
        }
        return true;
    }
}
