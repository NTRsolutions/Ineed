package com.androidtutorialpoint.ineed.proj.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtutorialpoint.ineed.R;
import com.androidtutorialpoint.ineed.proj.models.HelpTitle;
import com.androidtutorialpoint.ineed.proj.models.HelpTitleDetail;
import com.androidtutorialpoint.ineed.proj.viewholders.HelpViewDetailHolder;
import com.androidtutorialpoint.ineed.proj.viewholders.TitleViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class HelpListAdapter extends ExpandableRecyclerViewAdapter<TitleViewHolder,HelpViewDetailHolder> {

    public HelpListAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);

    }

    @Override
    public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.helptitle,parent,false);
        return new TitleViewHolder(view);
    }

    @Override
    public HelpViewDetailHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.helptitledetail,parent,false);
        return new HelpViewDetailHolder(view);    }

    @Override
    public void onBindChildViewHolder(HelpViewDetailHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        HelpTitleDetail helpTitle= (HelpTitleDetail) group.getItems().get(childIndex);
        holder.SetHelpdetail(helpTitle.getTitle());
    }

    @Override
    public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitletext(group.getTitle());
    }
}
