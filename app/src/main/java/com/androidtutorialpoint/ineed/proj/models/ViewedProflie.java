package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Rakhi on 1/9/2018.
 * Mobile number 9958187463
 */

public class ViewedProflie {


    /**
     * status : true
     * profile_list : [{"jobseeker_id":"80","designation":"Cook","Year":"2.6","country":"64","user_workpermit":"No","user_permitcountry":"0","user_image":"images_(3).jpg"}]
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
         * designation : Cook
         * Year : 2.6
         * country : 64
         * user_workpermit : No
         * user_permitcountry : 0
         * user_image : images_(3).jpg
         */

        private String jobseeker_id;
        private String designation;
        private String Year;
        private String country;
        private String user_workpermit;
        private String user_permitcountry;
        private String user_image;

        public String getJobseeker_id() {
            return jobseeker_id;
        }

        public void setJobseeker_id(String jobseeker_id) {
            this.jobseeker_id = jobseeker_id;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String Year) {
            this.Year = Year;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
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
    }
}
