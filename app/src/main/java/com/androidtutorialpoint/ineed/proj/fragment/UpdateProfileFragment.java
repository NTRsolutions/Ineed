package com.androidtutorialpoint.ineed.proj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtutorialpoint.ineed.R;

import static com.androidtutorialpoint.ineed.proj.activities.HomeActivity.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class UpdateProfileFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_update_profile,container,false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("Update Profile");
    }
}
