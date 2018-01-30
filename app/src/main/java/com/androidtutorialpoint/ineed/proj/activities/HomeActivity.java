package com.androidtutorialpoint.ineed.proj.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.fragment.DashboardEmpFragment;
import com.androidtutorialpoint.ineed.proj.fragment.DashboardJobseeker;
import com.androidtutorialpoint.ineed.proj.fragment.JobseekerDashboardFragment;
import com.androidtutorialpoint.ineed.proj.fragment.UpdateProfileFragment;
import com.androidtutorialpoint.ineed.proj.models.LoginData;
import com.google.gson.Gson;
import com.mukesh.tinydb.TinyDB;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout frameLayout;
    TextView texttollbar;
    NavigationView navigationView;
    ActionBar actionBar;
    public static Toolbar toolbar;
    LoginData  loginData = new LoginData();
    Gson gson = new Gson();
    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tinyDB = new TinyDB(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        String data = tinyDB.getString("login_data");
        loginData = gson.fromJson(data, LoginData.class);
//        set title bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        InputMethodManager methodManager= (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        methodManager.hideSoftInputFromWindow(HomeActivity.this.getWindow().getDecorView().getWindowToken(),0);

        setdefaultconatiner();
//        find jobseekerid

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        frameLayout= (FrameLayout) findViewById(R.id.frame_cont);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        if (jobseekerid!=null){
//            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float move=(navigationView.getWidth()*slideOffset);
                frameLayout.setTranslationX(move);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (loginData!=null){
            if (loginData.getUser_detail().getUser_payment_id().equals("7")){
                Intent intent = new Intent(HomeActivity.this, DialogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

//                setupoverlay();
            }
        }
//        if (getIntent().hasExtra("jobseekerid")){
//            jobseekerid = getIntent().getStringExtra("jobseekerid");
//
//            DashboardJobseeker dashboardJobseeker = new DashboardJobseeker();
////            Bundle args = new Bundle();
////            args.putString("jobseekerid", String.valueOf(jobseekerid));
////            dashboardJobseeker.setArguments(args);
//            getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, dashboardJobseeker).commit();
//        }

    }

    private void setdefaultconatiner() {
        if (loginData!=null){
            if (loginData.getUser_detail().getUser_type().equals("2")) {
                JobseekerDashboardFragment dashboardJobseeker = new JobseekerDashboardFragment();
                navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_search).setVisible(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, dashboardJobseeker).commit();
            } else {
                DashboardEmpFragment dashboardEmpFragment = new DashboardEmpFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, dashboardEmpFragment).commit();
                getSupportActionBar().setTitle("Dashboard");
            }
        }

    }

    private void setuptoolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       texttollbar = (TextView) toolbar.findViewById(R.id.toolbar_txt);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        setdefaultconatiner();

    }

    private void setupoverlay() {
        final Dialog dialog=new Dialog(HomeActivity.this,android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.error_overlay_popup);
        final TextView txtlogout = dialog.findViewById(R.id.txtLogout);
        final TextView txtMsg = dialog.findViewById(R.id.txt_msg);
        txtMsg.setText("Please upgrade your package");
        final Button upgrade=(Button)dialog.findViewById(R.id.overupgrade);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(HomeActivity.this, UpgradePlanActivity.class));
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                tinyDB.remove("login_data");
                finish();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if(getSupportFragmentManager().getBackStackEntryCount()>0)
//        {
//            getSupportFragmentManager().popBackStack();
//        }
//        else {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Exit");
//            builder.setMessage("Do you want to Exit?");
//            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.create();
//            builder.show();
            super.onBackPressed();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (data!=null){
                    if (resultCode == Activity.RESULT_OK) {
                        // TODO Extract the data returned from the child Activity.
                        String returnValue = data.getStringExtra("jobseekerid");
                        DashboardJobseeker dashboardJobseeker = new DashboardJobseeker();
                        getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, dashboardJobseeker).addToBackStack(null).commit();
                    }
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            setdefaultconatiner();
            // Handle the camera action
        } else if (id == R.id.nav_changepass) {
            startActivity(new Intent(HomeActivity.this, ChangePasswordActivity.class));
//            ChangePasswordFragment passwordFragment=new ChangePasswordFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.jobseekerid.subview_container,passwordFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_updateprofile) {
            UpdateProfileFragment passwordFragment=new UpdateProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,passwordFragment).addToBackStack(null).commit();
        } else if (id == R.id.nav_terms) {
            startActivity(new Intent(HomeActivity.this,TermsofUse.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(HomeActivity.this,AboutActivity.class));
        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(HomeActivity.this,ContactUsActivity.class));
        } else if (id == R.id.nav_search) {
            Intent it=new Intent(HomeActivity.this,Search.class);
            it.putExtra("Login","search");
            startActivityForResult(it,1);
        } else if (id == R.id.nav_logout) {
            tinyDB.remove("login_data");
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            Search.jobseekerid = null;
            finish();
        } else if (id == R.id.nav_help){
            startActivity(new Intent(HomeActivity.this,HelpActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
