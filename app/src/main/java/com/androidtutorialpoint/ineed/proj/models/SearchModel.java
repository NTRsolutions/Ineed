package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SearchModel {

    /**
     * status : true
     * profile_list : [{"designation":"php developer","TotalExperience":"2.1","country_location":"India","user_age":"29","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"India","user_image":"brijesh3.jpg","user_noticeperiod":"5 Days","user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"designation":"Designer","TotalExperience":"3.1","country_location":"United Arab Emirates","user_age":"23","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Algeria","user_image":"inu.jpg","user_noticeperiod":null,"user_jobtype":"Part -Time","user_ctc":"200001-250000"},{"designation":"PHP developer","TotalExperience":"6.2","country_location":"India","user_age":"2","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Austria","user_image":"download.png","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"500001-700000"},{"designation":"Cook","TotalExperience":"2.6","country_location":"Egypt","user_workpermit":"No","user_permitcountry":"","user_age":"28","user_gender":"Male","user_image":"images_(3).jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"designation":"manger","TotalExperience":"4.1","country_location":"Kuwait","user_age":"28","user_gender":"Female","user_workpermit":"Yes","user_permitcountry":"Kuwait","user_image":"Amy_Shaker_-_Headshot.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"250001-300000"},{"designation":"Team manager","TotalExperience":"4.3","country_location":"Lebanon","user_workpermit":"No","user_permitcountry":"","user_age":"28","user_gender":"Male","user_image":"images_(2).jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"designation":"Assit","TotalExperience":"6.4","country_location":"Oman","user_age":"28","user_gender":"Female","user_workpermit":"Yes","user_permitcountry":"Oman","user_image":"1212.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"300001-400000"},{"designation":"Php developer","TotalExperience":"8.4","country_location":"South Georgia","user_age":"1","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"India","user_image":"looking41.jpg","user_noticeperiod":"10 Days","user_jobtype":"Contract","user_ctc":"300001-400000"},{"designation":"php developer","TotalExperience":"1.2","country_location":"Saudi Arabia","user_workpermit":"No","user_permitcountry":"","user_age":"18","user_gender":"Male","user_image":"yashu.png","user_noticeperiod":"25 Days","user_jobtype":"Full- Time","user_ctc":"100001-150000"},{"designation":"driver","TotalExperience":"2.7","country_location":"Algeria","user_age":"28","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Algeria","user_image":"jaso.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"100001-150000"},{"designation":"marketing","TotalExperience":"3.6","country_location":"United Arab Emirates","user_age":"28","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"United Arab Emirates","user_image":"gjhggkh.jpg","user_noticeperiod":null,"user_jobtype":"Part -Time","user_ctc":"250001-300000"},{"designation":"Director (Sales)","TotalExperience":"12.11","country_location":"India","user_age":"35","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Saudi Arabia","user_image":"297956_629808970367393_1948872163_n.jpg","user_noticeperiod":"3 Months","user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"designation":null,"TotalExperience":".","country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"19","user_gender":"male","user_image":"11023800-23.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":null,"user_workpermit":"","user_permitcountry":"","user_age":"19","user_gender":"","user_image":"inu.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"22","user_gender":"male","user_image":"Chrysanthemum1.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"35","user_gender":"male","user_image":"fiths.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"35","user_gender":"male","user_image":"images3.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"36","user_gender":"male","user_image":"john.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":"Algeria","user_workpermit":"","user_permitcountry":"","user_age":"0","user_gender":"male","user_image":"images.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"designation":null,"TotalExperience":".","country_location":"Algeria","user_workpermit":"","user_permitcountry":"","user_age":"0","user_gender":"male","user_image":"images_(1).jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null}]
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
         * designation : php developer
         * TotalExperience : 2.1
         * country_location : India
         * user_age : 29
         * user_gender : Male
         * user_workpermit : Yes
         * user_permitcountry : India
         * user_image : brijesh3.jpg
         * user_noticeperiod : 5 Days
         * user_jobtype : Full- Time
         * user_ctc : 150001-200000
         */

        private String designation;
        private String TotalExperience;
        private String country_location;
        private String user_age;
        private String user_gender;
        private String user_workpermit;
        private String user_permitcountry;
        private String user_image;
        private String user_noticeperiod;
        private String user_jobtype;
        private String user_ctc;

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

