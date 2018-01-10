package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Rakhi on 1/9/2018.
 * Mobile number 9958187463
 */

public class ViewedProflie {


    /**
     * status : true
     * profile_list : [{"jobseeker_id":"80","user_name":"John","designation":"Cook","TotalExperience":"2.6","country_location":"Egypt","user_workpermit":"No","user_permitcountry":"","user_age":"28","user_gender":"Male","user_image":"images_(3).jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"150001-200000"}]
     */

    private boolean status;
    private List<ProfileListBean> profile_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ProfileListBean> getProfile_list() {
        return profile_list;
    }

    public void setProfile_list(List<ProfileListBean> profile_list) {
        this.profile_list = profile_list;
    }

    public static class ProfileListBean {
        /**
         * jobseeker_id : 80
         * user_name : John
         * designation : Cook
         * TotalExperience : 2.6
         * country_location : Egypt
         * user_workpermit : No
         * user_permitcountry :
         * user_age : 28
         * user_gender : Male
         * user_image : images_(3).jpg
         * user_noticeperiod : null
         * user_jobtype : Full- Time
         * user_ctc : 150001-200000
         */

        private String jobseeker_id;
        private String user_name;
        private String designation;
        private String TotalExperience;
        private String country_location;
        private String user_workpermit;
        private String user_permitcountry;
        private String user_age;
        private String user_gender;
        private String user_image;
        private Object user_noticeperiod;
        private String user_jobtype;
        private String user_ctc;

        public String getJobseeker_id() {
            return jobseeker_id;
        }

        public void setJobseeker_id(String jobseeker_id) {
            this.jobseeker_id = jobseeker_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getTotalExperience() {
            return TotalExperience;
        }

        public void setTotalExperience(String TotalExperience) {
            this.TotalExperience = TotalExperience;
        }

        public String getCountry_location() {
            return country_location;
        }

        public void setCountry_location(String country_location) {
            this.country_location = country_location;
        }

        public String getUser_workpermit() {
            return user_workpermit;
        }

        public void setUser_workpermit(String user_workpermit) {
            this.user_workpermit = user_workpermit;
        }

        public String getUser_permitcountry() {
            return user_permitcountry;
        }

        public void setUser_permitcountry(String user_permitcountry) {
            this.user_permitcountry = user_permitcountry;
        }

        public String getUser_age() {
            return user_age;
        }

        public void setUser_age(String user_age) {
            this.user_age = user_age;
        }

        public String getUser_gender() {
            return user_gender;
        }

        public void setUser_gender(String user_gender) {
            this.user_gender = user_gender;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public Object getUser_noticeperiod() {
            return user_noticeperiod;
        }

        public void setUser_noticeperiod(Object user_noticeperiod) {
            this.user_noticeperiod = user_noticeperiod;
        }

        public String getUser_jobtype() {
            return user_jobtype;
        }

        public void setUser_jobtype(String user_jobtype) {
            this.user_jobtype = user_jobtype;
        }

        public String getUser_ctc() {
            return user_ctc;
        }

        public void setUser_ctc(String user_ctc) {
            this.user_ctc = user_ctc;
        }
    }
}
