package com.androidtutorialpoint.ineed.proj.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.activities.HomeActivity;


public class JobseekerDashboardFragment extends Fragment implements View.OnClickListener{
    TextView txtMyProfile, txtExpiry, txtViewedProfile, txtPlan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobseeker_dashboard, container, false);

//        find id
        txtMyProfile = (TextView) view.findViewById(R.id.jobseekerdash_txtMyProfile);
        txtExpiry = (TextView) view.findViewById(R.id.jobseekerdash_expiryDate);
        txtViewedProfile = (TextView) view.findViewById(R.id.jobseekerdash_viewNo);
        txtPlan = (TextView) view.findViewById(R.id.jobseekerdash_currentPlan);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

//        set onclick
        txtMyProfile.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        DashboardJobseeker dashboardJobseeker = new DashboardJobseeker();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, dashboardJobseeker).addToBackStack(null).commit();

    }
}
