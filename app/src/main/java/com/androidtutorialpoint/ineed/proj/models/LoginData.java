package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by ask on 12/5/2017.
 */

public class LoginData {
    /**
     * status : true
     * msg : Successfully login
     * user_detail : {"user_id":"29","user_title":"","user_fname":"Brijesh","user_lname":"Chauhan","user_username":"brijesh","user_email":"brijesh@askonlinesolutions.com","user_countrycode":"0","user_state":"5","user_phone":"12345678","user_company":"","user_password":"8843028fefce50a6de50acdf064ded27","user_zipcode":"","user_address":"","user_city":null,"user_age":"17","user_country":"3","user_empcode":"","user_image":"brijesh2.jpg","user_type":"2","user_payment_id":"8","user_payment_type":"1","user_payment_start_date":"0000-00-00","user_payment_last_date":"0000-00-00","user_language_id":"","user_package_id":"PREMIUM","user_package_startdate":"2017-12-05","user_package_expire_date":"2018-04-05","user_register_date":"2017-10-03","user_status":"1","user_gender":"الذكر","user_dob":"2009-02-03","jobseeker_workexp_id":"33","jobseeker_workexp_seekerid":"29","jobseeker_workexp_seekertype":"2","jobseeker_workexp_profile_title":"php developer","jobseeker_workexp_jobtype":"1","jobseeker_workexp_totalexp":"2","jobseeker_workexp_annualsalary":"3","jobseeker_workexp_positions":"4","jobseeker_workexp_noticeperoid":"2","jobseeker_workexp_job_title":"dasdad","jobseeker_workexp_companyname":"ask","jobseeker_workexp_companyindus":"1","jobseeker_workexp_dept":"2","jobseeker_workexp_duration":"4 Year","jobseeker_workexp_resume":"Tulips4.jpg"}
     */

    private boolean status;
    private String msg;
    private UserDetailBean user_detail;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserDetailBean getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetailBean user_detail) {
        this.user_detail = user_detail;
    }

    public static class UserDetailBean {
        /**
         * user_id : 29
         * user_title :
         * user_fname : Brijesh
         * user_lname : Chauhan
         * user_username : brijesh
         * user_email : brijesh@askonlinesolutions.com
         * user_countrycode : 0
         * user_state : 5
         * user_phone : 12345678
         * user_company :
         * user_password : 8843028fefce50a6de50acdf064ded27
         * user_zipcode :
         * user_address :
         * user_city : null
         * user_age : 17
         * user_country : 3
         * user_empcode :
         * user_image : brijesh2.jpg
         * user_type : 2
         * user_payment_id : 8
         * user_payment_type : 1
         * user_payment_start_date : 0000-00-00
         * user_payment_last_date : 0000-00-00
         * user_language_id :
         * user_package_id : PREMIUM
         * user_package_startdate : 2017-12-05
         * user_package_expire_date : 2018-04-05
         * user_register_date : 2017-10-03
         * user_status : 1
         * user_gender : الذكر
         * user_dob : 2009-02-03
         * jobseeker_workexp_id : 33
         * jobseeker_workexp_seekerid : 29
         * jobseeker_workexp_seekertype : 2
         * jobseeker_workexp_profile_title : php developer
         * jobseeker_workexp_jobtype : 1
         * jobseeker_workexp_totalexp : 2
         * jobseeker_workexp_annualsalary : 3
         * jobseeker_workexp_positions : 4
         * jobseeker_workexp_noticeperoid : 2
         * jobseeker_workexp_job_title : dasdad
         * jobseeker_workexp_companyname : ask
         * jobseeker_workexp_companyindus : 1
         * jobseeker_workexp_dept : 2
         * jobseeker_workexp_duration : 4 Year
         * jobseeker_workexp_resume : Tulips4.jpg
         */

        private String user_id;
        private String user_title;
        private String user_fname;
        private String user_lname;
        private String user_username;
        private String user_email;
        private String user_countrycode;
        private String user_state;
        private String user_phone;
        private String user_company;
        private String user_password;
        private String user_zipcode;
        private String user_address;
        private Object user_city;
        private String user_age;
        private String user_country;
        private String user_empcode;
        private String user_image;
        private String user_type;
        private String user_payment_id;
        private String user_payment_type;
        private String user_payment_start_date;
        private String user_payment_last_date;
        private String user_language_id;
        private String user_package_id;
        private String user_package_startdate;
        private String user_package_expire_date;
        private String user_register_date;
        private String user_status;
        private String user_gender;
        private String user_dob;
        private String jobseeker_workexp_id;
        private String jobseeker_workexp_seekerid;
        private String jobseeker_workexp_seekertype;
        private String jobseeker_workexp_profile_title;
        private String jobseeker_workexp_jobtype;
        private String jobseeker_workexp_totalexp;
        private String jobseeker_workexp_annualsalary;
        private String jobseeker_workexp_positions;
        private String jobseeker_workexp_noticeperoid;
        private String jobseeker_workexp_job_title;
        private String jobseeker_workexp_companyname;
        private String jobseeker_workexp_companyindus;
        private String jobseeker_workexp_dept;
        private String jobseeker_workexp_duration;
        private String jobseeker_workexp_resume;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_title() {
            return user_title;
        }

        public void setUser_title(String user_title) {
            this.user_title = user_title;
        }

        public String getUser_fname() {
            return user_fname;
        }

        public void setUser_fname(String user_fname) {
            this.user_fname = user_fname;
        }

        public String getUser_lname() {
            return user_lname;
        }

        public void setUser_lname(String user_lname) {
            this.user_lname = user_lname;
        }

        public String getUser_username() {
            return user_username;
        }

        public void setUser_username(String user_username) {
            this.user_username = user_username;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_countrycode() {
            return user_countrycode;
        }

        public void setUser_countrycode(String user_countrycode) {
            this.user_countrycode = user_countrycode;
        }

        public String getUser_state() {
            return user_state;
        }

        public void setUser_state(String user_state) {
            this.user_state = user_state;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_company() {
            return user_company;
        }

        public void setUser_company(String user_company) {
            this.user_company = user_company;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_zipcode() {
            return user_zipcode;
        }

        public void setUser_zipcode(String user_zipcode) {
            this.user_zipcode = user_zipcode;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public Object getUser_city() {
            return user_city;
        }

        public void setUser_city(Object user_city) {
            this.user_city = user_city;
        }

        public String getUser_age() {
            return user_age;
        }

        public void setUser_age(String user_age) {
            this.user_age = user_age;
        }

        public String getUser_country() {
            return user_country;
        }

        public void setUser_country(String user_country) {
            this.user_country = user_country;
        }

        public String getUser_empcode() {
            return user_empcode;
        }

        public void setUser_empcode(String user_empcode) {
            this.user_empcode = user_empcode;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_payment_id() {
            return user_payment_id;
        }

        public void setUser_payment_id(String user_payment_id) {
            this.user_payment_id = user_payment_id;
        }

        public String getUser_payment_type() {
            return user_payment_type;
        }

        public void setUser_payment_type(String user_payment_type) {
            this.user_payment_type = user_payment_type;
        }

        public String getUser_payment_start_date() {
            return user_payment_start_date;
        }

        public void setUser_payment_start_date(String user_payment_start_date) {
            this.user_payment_start_date = user_payment_start_date;
        }

        public String getUser_payment_last_date() {
            return user_payment_last_date;
        }

        public void setUser_payment_last_date(String user_payment_last_date) {
            this.user_payment_last_date = user_payment_last_date;
        }

        public String getUser_language_id() {
            return user_language_id;
        }

        public void setUser_language_id(String user_language_id) {
            this.user_language_id = user_language_id;
        }

        public String getUser_package_id() {
            return user_package_id;
        }

        public void setUser_package_id(String user_package_id) {
            this.user_package_id = user_package_id;
        }

        public String getUser_package_startdate() {
            return user_package_startdate;
        }

        public void setUser_package_startdate(String user_package_startdate) {
            this.user_package_startdate = user_package_startdate;
        }

        public String getUser_package_expire_date() {
            return user_package_expire_date;
        }

        public void setUser_package_expire_date(String user_package_expire_date) {
            this.user_package_expire_date = user_package_expire_date;
        }

        public String getUser_register_date() {
            return user_register_date;
        }

        public void setUser_register_date(String user_register_date) {
            this.user_register_date = user_register_date;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getUser_gender() {
            return user_gender;
        }

        public void setUser_gender(String user_gender) {
            this.user_gender = user_gender;
        }

        public String getUser_dob() {
            return user_dob;
        }

        public void setUser_dob(String user_dob) {
            this.user_dob = user_dob;
        }

        public String getJobseeker_workexp_id() {
            return jobseeker_workexp_id;
        }

        public void setJobseeker_workexp_id(String jobseeker_workexp_id) {
            this.jobseeker_workexp_id = jobseeker_workexp_id;
        }

        public String getJobseeker_workexp_seekerid() {
            return jobseeker_workexp_seekerid;
        }

        public void setJobseeker_workexp_seekerid(String jobseeker_workexp_seekerid) {
            this.jobseeker_workexp_seekerid = jobseeker_workexp_seekerid;
        }

        public String getJobseeker_workexp_seekertype() {
            return jobseeker_workexp_seekertype;
        }

        public void setJobseeker_workexp_seekertype(String jobseeker_workexp_seekertype) {
            this.jobseeker_workexp_seekertype = jobseeker_workexp_seekertype;
        }

        public String getJobseeker_workexp_profile_title() {
            return jobseeker_workexp_profile_title;
        }

        public void setJobseeker_workexp_profile_title(String jobseeker_workexp_profile_title) {
            this.jobseeker_workexp_profile_title = jobseeker_workexp_profile_title;
        }

        public String getJobseeker_workexp_jobtype() {
            return jobseeker_workexp_jobtype;
        }

        public void setJobseeker_workexp_jobtype(String jobseeker_workexp_jobtype) {
            this.jobseeker_workexp_jobtype = jobseeker_workexp_jobtype;
        }

        public String getJobseeker_workexp_totalexp() {
            return jobseeker_workexp_totalexp;
        }

        public void setJobseeker_workexp_totalexp(String jobseeker_workexp_totalexp) {
            this.jobseeker_workexp_totalexp = jobseeker_workexp_totalexp;
        }

        public String getJobseeker_workexp_annualsalary() {
            return jobseeker_workexp_annualsalary;
        }

        public void setJobseeker_workexp_annualsalary(String jobseeker_workexp_annualsalary) {
            this.jobseeker_workexp_annualsalary = jobseeker_workexp_annualsalary;
        }

        public String getJobseeker_workexp_positions() {
            return jobseeker_workexp_positions;
        }

        public void setJobseeker_workexp_positions(String jobseeker_workexp_positions) {
            this.jobseeker_workexp_positions = jobseeker_workexp_positions;
        }

        public String getJobseeker_workexp_noticeperoid() {
            return jobseeker_workexp_noticeperoid;
        }

        public void setJobseeker_workexp_noticeperoid(String jobseeker_workexp_noticeperoid) {
            this.jobseeker_workexp_noticeperoid = jobseeker_workexp_noticeperoid;
        }

        public String getJobseeker_workexp_job_title() {
            return jobseeker_workexp_job_title;
        }

        public void setJobseeker_workexp_job_title(String jobseeker_workexp_job_title) {
            this.jobseeker_workexp_job_title = jobseeker_workexp_job_title;
        }

        public String getJobseeker_workexp_companyname() {
            return jobseeker_workexp_companyname;
        }

        public void setJobseeker_workexp_companyname(String jobseeker_workexp_companyname) {
            this.jobseeker_workexp_companyname = jobseeker_workexp_companyname;
        }

        public String getJobseeker_workexp_companyindus() {
            return jobseeker_workexp_companyindus;
        }

        public void setJobseeker_workexp_companyindus(String jobseeker_workexp_companyindus) {
            this.jobseeker_workexp_companyindus = jobseeker_workexp_companyindus;
        }

        public String getJobseeker_workexp_dept() {
            return jobseeker_workexp_dept;
        }

        public void setJobseeker_workexp_dept(String jobseeker_workexp_dept) {
            this.jobseeker_workexp_dept = jobseeker_workexp_dept;
        }

        public String getJobseeker_workexp_duration() {
            return jobseeker_workexp_duration;
        }

        public void setJobseeker_workexp_duration(String jobseeker_workexp_duration) {
            this.jobseeker_workexp_duration = jobseeker_workexp_duration;
        }

        public String getJobseeker_workexp_resume() {
            return jobseeker_workexp_resume;
        }

        public void setJobseeker_workexp_resume(String jobseeker_workexp_resume) {
            this.jobseeker_workexp_resume = jobseeker_workexp_resume;
        }
    }
}
