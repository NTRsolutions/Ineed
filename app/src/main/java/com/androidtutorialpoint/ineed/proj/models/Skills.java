package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by lenovo on 2/2/2018.
 */

public class Skills {

    /**
     * skills : 206
     * yesr : c
     */

    private String skills;
    private String yesr;

    public Skills( String skills, String yesr) {

        this.skills = skills;
        this.yesr = yesr;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getYesr() {
        return yesr;
    }

    public void setYesr(String yesr) {
        this.yesr = yesr;
    }
}
