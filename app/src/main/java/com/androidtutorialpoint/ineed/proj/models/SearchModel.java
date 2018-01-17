package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SearchModel {


    /**
     * status : true
     * profile_list : [{"user_id":"29","designation":"php developer","TotalExperienceyear":"0","TotalExperiencemonths":"1","country_location":"India","user_age":"29","user_nationality":"Indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Bahrain","user_image":"brijesh4.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"user_id":"68","designation":"Designer","TotalExperienceyear":"3","TotalExperiencemonths":"1","country_location":"United Arab Emirates","user_age":"23","user_nationality":"UAE","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Algeria","user_image":"inu.jpg","user_noticeperiod":null,"user_jobtype":"Part -Time","user_ctc":"200001-250000"},{"user_id":"36","designation":"PHP developer","TotalExperienceyear":"6","TotalExperiencemonths":"2","country_location":"India","user_age":"2","user_nationality":"Indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Austria","user_image":"download.png","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"500001-700000"},{"user_id":"80","designation":"Cook","TotalExperienceyear":"2","TotalExperiencemonths":"6","country_location":"Egypt","user_workpermit":"No","user_permitcountry":"","user_age":"28","user_nationality":"EGT","user_gender":"Male","user_image":"images_(3).jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"user_id":"197","designation":"manger","TotalExperienceyear":"4","TotalExperiencemonths":"1","country_location":"Kuwait","user_age":"28","user_nationality":"Kuwait","user_gender":"Female","user_workpermit":"Yes","user_permitcountry":"Kuwait","user_image":"Amy_Shaker_-_Headshot.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"250001-300000"},{"user_id":"79","designation":"Team manager","TotalExperienceyear":"4","TotalExperiencemonths":"3","country_location":"Lebanon","user_workpermit":"No","user_permitcountry":"","user_age":"28","user_nationality":"Lebanon","user_gender":"Male","user_image":"images_(2).jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"user_id":"69","designation":"Assit","TotalExperienceyear":"6","TotalExperiencemonths":"4","country_location":"Oman","user_age":"28","user_nationality":"Oman","user_gender":"Female","user_workpermit":"Yes","user_permitcountry":"Oman","user_image":"1212.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"300001-400000"},{"user_id":"52","designation":"Php developer","TotalExperienceyear":"8","TotalExperiencemonths":"4","country_location":"South Georgia","user_age":"1","user_nationality":"Indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"India","user_image":"looking41.jpg","user_noticeperiod":"10 Days","user_jobtype":"Contract","user_ctc":"300001-400000"},{"user_id":"201","designation":"php developer","TotalExperienceyear":"1","TotalExperiencemonths":"2","country_location":"Saudi Arabia","user_workpermit":"No","user_permitcountry":"","user_age":"18","user_nationality":"Saudi","user_gender":"Male","user_image":"yashu.png","user_noticeperiod":"25 Days","user_jobtype":"Full- Time","user_ctc":"100001-150000"},{"user_id":"72","designation":"driver","TotalExperienceyear":"2","TotalExperiencemonths":"7","country_location":"Algeria","user_age":"28","user_nationality":"Algeria","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Algeria","user_image":"jaso.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"100001-150000"},{"user_id":"74","designation":"marketing","TotalExperienceyear":"3","TotalExperiencemonths":"6","country_location":"United Arab Emirates","user_age":"28","user_nationality":"UAE","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"United Arab Emirates","user_image":"206.jpg","user_noticeperiod":null,"user_jobtype":"Part -Time","user_ctc":"250001-300000"},{"user_id":"216","designation":"Director (Sales)","TotalExperienceyear":"12","TotalExperiencemonths":"11","country_location":"India","user_age":"35","user_nationality":"Indian","user_gender":"Male","user_workpermit":"Yes","user_permitcountry":"Saudi Arabia","user_image":"297956_629808970367393_1948872163_n.jpg","user_noticeperiod":"3 Months","user_jobtype":"Full- Time","user_ctc":"150001-200000"},{"user_id":"206","designation":"android developer","TotalExperienceyear":"1","TotalExperiencemonths":"6","country_location":"India","user_workpermit":"yes","user_permitcountry":"","user_age":"23","user_nationality":"Indian","user_gender":"female","user_image":"18.png","user_noticeperiod":"25 Days","user_jobtype":"Full- Time","user_ctc":null},{"user_id":"257","designation":"quality analyst","TotalExperienceyear":"1","TotalExperiencemonths":"11","country_location":"India","user_age":"0","user_nationality":"India","user_gender":"Female","user_workpermit":"Yes","user_permitcountry":"India","user_image":"indian-prof3-b.jpg","user_noticeperiod":null,"user_jobtype":"Full- Time","user_ctc":"200001-250000"},{"user_id":"259","designation":"developer","TotalExperienceyear":"2","TotalExperiencemonths":"10","country_location":"Angola","user_age":"0","user_nationality":"India","user_gender":"Female","user_workpermit":"Yes","user_permitcountry":"Anguilla","user_image":"indian-prof3-b1.jpg","user_noticeperiod":"25 Days","user_jobtype":"Full- Time","user_ctc":"500001-700000"},{"user_id":"17","designation":null,"TotalExperienceyear":null,"TotalExperiencemonths":null,"country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"22","user_nationality":"","user_gender":"male","user_image":"Chrysanthemum1.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"user_id":"26","designation":null,"TotalExperienceyear":null,"TotalExperiencemonths":null,"country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"35","user_nationality":"","user_gender":"male","user_image":"fiths.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"user_id":"70","designation":null,"TotalExperienceyear":null,"TotalExperiencemonths":null,"country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"35","user_nationality":"","user_gender":"male","user_image":"images3.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"user_id":"71","designation":null,"TotalExperienceyear":null,"TotalExperiencemonths":null,"country_location":"Afghanistan","user_workpermit":"","user_permitcountry":"","user_age":"36","user_nationality":"","user_gender":"male","user_image":"john.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"user_id":"77","designation":null,"TotalExperienceyear":null,"TotalExperiencemonths":null,"country_location":"Algeria","user_workpermit":"","user_permitcountry":"","user_age":"0","user_nationality":"","user_gender":"male","user_image":"images.jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null},{"user_id":"78","designation":null,"TotalExperienceyear":null,"TotalExperiencemonths":null,"country_location":"Algeria","user_workpermit":"","user_permitcountry":"","user_age":"0","user_nationality":"","user_gender":"male","user_image":"images_(1).jpg","user_noticeperiod":null,"user_jobtype":null,"user_ctc":null}]
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
         * TotalExperienceyear : 0
         * TotalExperiencemonths : 1
         * country_location : India
         * user_age : 29
         * user_nationality : Indian
         * user_gender : Male
         * user_workpermit : Yes
         * user_permitcountry : Bahrain
         * user_image : brijesh4.jpg
         * user_noticeperiod : null
         * user_jobtype : Full- Time
         * user_ctc : 150001-200000
         */

        private String user_id;
        private String designation;
        private String TotalExperienceyear;
        private String TotalExperiencemonths;
        private String country_location;
        private String user_age;
        private String user_nationality;
        private String user_gender;
        private String user_workpermit;
        private String user_permitcountry;
        private String user_image;
        private Object user_noticeperiod;
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

        public String getTotalExperienceyear() {
            return TotalExperienceyear;
        }

        public void setTotalExperienceyear(String TotalExperienceyear) {
            this.TotalExperienceyear = TotalExperienceyear;
        }

        public String getTotalExperiencemonths() {
            return TotalExperiencemonths;
        }

        public void setTotalExperiencemonths(String TotalExperiencemonths) {
            this.TotalExperiencemonths = TotalExperiencemonths;
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

