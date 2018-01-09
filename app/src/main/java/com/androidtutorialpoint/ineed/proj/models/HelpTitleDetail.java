package com.androidtutorialpoint.ineed.proj.models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class HelpTitleDetail {
    private String titledetail;

    public HelpTitleDetail(String titledetail) {
        this.titledetail = titledetail;
    }
    public String getTitle() {
        return titledetail;
    }

    public void setTitle(String title) {  this.titledetail = title;}

 /*   public HelpTitleDetail(String title, List items) {
        super(title, items);
    }*/
}
