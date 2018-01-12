package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SearchModel {

    /**
     * status : true
     * profile_list : [{"user_id":"29","designation":"php developer","TotalExperience":"2.1","country_location":"India","user_age":"29","user_nationality":"indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"India","user_image":"brijesh3.jpg","user_noticeperiod":"5 Days","user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"user_id":"36","designation":"PHP developer","TotalExperience":"6.2","country_location":"India","user_age":"2","user_nationality":"Indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Austria","user_image":"download.png","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"500001-700000"},{"user_id":"52","designation":"Php developer","TotalExperience":"8.4","country_location":"South Georgia","user_age":"1","user_nationality":"Indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"India","user_image":"looking41.jpg","user_noticeperiod":"10 Days","user_jobtype":"Contract","user_ctc":"300001-400000"},{"user_id":"201","designation":"php developer","TotalExperience":"1.2","country_location":"Saudi Arabia","user_workpermit":"No","user_permitcountry":"","user_age":"18","user_nationality":"Saudi","user_gender":"Male","user_image":"yashu.png","user_noticeperiod":"25 Days","user_jobtype":"Full- Time","user_ctc":"100001-150000"}]
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
         * user_id : 29
         * designation : php developer
         * TotalExperience : 2.1
         * country_location : India
         * user_age : 29
         * user_nationality : indian
         * user_gender : Male
         * user_workpermit : Yes
         * user_permitcountry : India
         * user_image : brijesh3.jpg
         * user_noticeperiod : 5 Days
         * user_jobtype : Full- Time
         * user_ctc : 150001-200000
         */

        private String user_id;
        private String designation;
        private String TotalExperience;
        private String country_location;
        private String user_age;
        private String user_nationality;
        private String user_gender;
        private String user_workpermit;
        private String user_permitcountry;
        private String user_image;
        private String user_noticeperiod;
        private String user_jobtype;
        private String user_ctc;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getUser_age() {
            return user_age;
        }

        public void setUser_age(String user_age) {
            this.user_age = user_age;
        }

        public String getUser_nationality() {
            return user_nationality;
        }

        public void setUser_nationality(String user_nationality) {
            this.user_nationality = user_nationality;
        }

        public String getUser_gender() {
            return user_gender;
        }

        public void setUser_gender(String user_gender) {
            this.user_gender = user_gender;
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

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getUser_noticeperiod() {
            return user_noticeperiod;
        }

        public void setUser_noticeperiod(String user_noticeperiod) {
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

