package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by Rakhi on 1/10/2018.
 * Mobile number 9958187463
 */

public class EmployerProfileData {

    /**
     * status : true
     * profile_detail : {"user_fname":"","user_lname":null,"user_email":"rs@g.i","user_company":"","user_phone":"","user_image":"","user_package_id":"PRO","user_package_price":"145","user_package_credit":"119","user_package_expire_date":"2018-04-13","user_credit_use":1}
     */

    private boolean status;
    private ProfileDetailBean profile_detail;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ProfileDetailBean getProfile_detail() {
        return profile_detail;
    }

    public void setProfile_detail(ProfileDetailBean profile_detail) {
        this.profile_detail = profile_detail;
    }

    public static class ProfileDetailBean {
        /**
         * user_fname :
         * user_lname : null
         * user_email : rs@g.i
         * user_company :
         * user_phone :
         * user_image :
         * user_package_id : PRO
         * user_package_price : 145
         * user_package_credit : 119
         * user_package_expire_date : 2018-04-13
         * user_credit_use : 1
         */

        private String user_fname;
        private Object user_lname;
        private String user_email;
        private String user_company;
        private String user_phone;
        private String user_image;
        private String user_package_id;
        private String user_package_price;
        private String user_package_credit;
        private String user_package_expire_date;
        private int user_credit_use;

        public String getUser_fname() {
            return user_fname;
        }

        public void setUser_fname(String user_fname) {
            this.user_fname = user_fname;
        }

        public Object getUser_lname() {
            return user_lname;
        }

        public void setUser_lname(Object user_lname) {
            this.user_lname = user_lname;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_company() {
            return user_company;
        }

        public void setUser_company(String user_company) {
            this.user_company = user_company;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getUser_package_id() {
            return user_package_id;
        }

        public void setUser_package_id(String user_package_id) {
            this.user_package_id = user_package_id;
        }

        public String getUser_package_price() {
            return user_package_price;
        }

        public void setUser_package_price(String user_package_price) {
            this.user_package_price = user_package_price;
        }

        public String getUser_package_credit() {
            return user_package_credit;
        }

        public void setUser_package_credit(String user_package_credit) {
            this.user_package_credit = user_package_credit;
        }

        public String getUser_package_expire_date() {
            return user_package_expire_date;
        }

        public void setUser_package_expire_date(String user_package_expire_date) {
            this.user_package_expire_date = user_package_expire_date;
        }

        public int getUser_credit_use() {
            return user_credit_use;
        }

        public void setUser_credit_use(int user_credit_use) {
            this.user_credit_use = user_credit_use;
        }
    }
}
