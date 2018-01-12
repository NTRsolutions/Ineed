package com.androidtutorialpoint.ineed.proj.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidtutorialpoint.ineed.R;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
Button btabout,btTermsofUse,btUpdate,btChange,bt_search,bt_resetpass,bt_contactus,bt_daButton,bt_home,bt_profileview,bt_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btabout= (Button) findViewById(R.id.bt_about);
        btTermsofUse= (Button) findViewById(R.id.bt_terms);
        btUpdate= (Button) findViewById(R.id.bt_Update);
        btChange= (Button) findViewById(R.id.bt_Change);
        bt_search= (Button) findViewById(R.id.bt_Search);
        bt_resetpass= (Button) findViewById(R.id.bt_ResetPassword);
        bt_contactus= (Button) findViewById(R.id.bt_contactUs);
        bt_daButton= (Button) findViewById(R.id.bt_dash);
        bt_home=(Button) findViewById(R.id.bt_home);
        bt_profileview=(Button) findViewById(R.id.bt_profview);
        bt_profile=(Button) findViewById(R.id.bt_prof);
        bt_profile.setOnClickListener(this);
        bt_profileview.setOnClickListener(this);
        bt_home.setOnClickListener(this);
        bt_daButton.setOnClickListener(this);
        btabout.setOnClickListener(this);
        btTermsofUse.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btChange.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        bt_resetpass.setOnClickListener(this);
        bt_contactus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_about:
                startActivity(new Intent(Welcome.this,AboutActivity.class));
                break;
            case R.id.bt_terms:
                startActivity(new Intent(Welcome.this,TermsofUse.class));
                break;
            case R.id.bt_Update:
                startActivity(new Intent(Welcome.this,UpdateProfileActivity.class));
                break;
            case  R.id.bt_Change:
                startActivity(new Intent(Welcome.this,ChangePasswordActivity.class));
                break;
            case  R.id.bt_Search:
                Intent it=new Intent(Welcome.this,Search.class);
                it.putExtra("Login","search");
                startActivity(it);
                break;
            case  R.id.bt_ResetPassword:
                startActivity(new Intent(Welcome.this,ResetPasswordsActivity.class));
                break;
            case  R.id.bt_contactUs:
            startActivity(new Intent(Welcome.this,ContactUsActivity.class));
            break;
            case  R.id.bt_dash:
            startActivity(new Intent(Welcome.this,DashboardActivity.class));
            break;
            case  R.id.bt_home:
                startActivity(new Intent(Welcome.this,HomeActivity.class));
                break;
            case  R.id.bt_profview:
            startActivity(new Intent(Welcome.this,ProfileViewed.class));
            break;
            case  R.id.bt_prof:
//            startActivity(new Intent(Welcome.this,Profile.class));
            break;
        }
    }
}
