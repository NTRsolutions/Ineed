package com.androidtutorialpoint.ineed.proj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;

import static com.androidtutorialpoint.ineed.proj.activities.HomeActivity.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class ChangePasswordFragment extends Fragment {
    View view;
    ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_change_password,container,false);

        return view;
    }
    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        TextView textView= (TextView)toolbar.findViewById(R.id.toolbar_txt);
        textView.setText("Change Password");
       /* getActivity().setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("Change Password");
        //  setuptoolbar();
    }
}
