package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by rakhi on 12/26/2017.
 */

public class JobseekerProileData {

    private boolean status;
    private UserListBean user_list;
    private List<WorksListBean> works_list;
    private List<EducationsListBean> educations_list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserListBean getUser_list() {
        return user_list;
    }

    public void setUser_list(UserListBean user_list) {
        this.user_list = user_list;
    }

    public List<WorksListBean> getWorks_list() {
        return works_list;
    }

    public void setWorks_list(List<WorksListBean> works_list) {
        this.works_list = works_list;
    }

    public List<EducationsListBean> getEducations_list() {
        return educations_list;
    }

    public void setEducations_list(List<EducationsListBean> educations_list) {
        this.educations_list = educations_list;
    }

    public static class UserListBean {
        /**
         * user_id : 29
         * user_fname : Brijesh chauhan
         * user_username : brijesh
         * user_email : brijesh@askonlinesolutions.com
         * user_phone : 12345678
         * user_age : 29
         * user_country_id : 101
         * user_country_name : India
         * user_workpermit : Yes
         * user_permitcountry_id : 101
         * user_permitcountry : India
         * user_nationality : indian
         * user_image : brijesh3.jpg
         * user_resume : website-text1.docx
         * user_payment_id : 8
         * user_package_price : 50000
         * user_package_credit : 0
         * user_package_startdate : 2017-12-27
         * user_package_expire_date : 2018-02-27
         * user_package_id : STANDARD
         * user_gender : Male
         * user_dob : 1989-12-01
         * profile_summary_id : 2
         * profile_summary_seekerid : 29
         * profile_summary_totalyear : 2.1
         * profile_summary_currentsalary : 5
         * profile_summary_companyname : ask online solutions
         * profile_summary_positions : php developer
         * profile_summary_currentcountry_id : 95
         * profile_summary_currentcountry : Haiti
         * profile_summary_jobtype_id : 1
         * profile_summary_jobtype : Full- Time
         * profile_summary_noticeperiod_id : 2
         * profile_summary_noticeperiod : 5 Days
         * profile_summary_resumeheadline : I have 3 year experience of php and  codeigniter
         * profile_summary_skills : html,php
         * profile_summary_exp : 1,3
         */

        private String user_id;
        private String user_fname;
        private String user_username;
        private String user_email;
        private String user_phone;
        private String user_age;
        private String user_country_id;
        private String user_country_name;
        private String user_workpermit;
        private String user_permitcountry_id;
        private String user_permitcountry;
        private String user_nationality;
        private String user_image;
        private String user_resume;
        private String user_payment_id;
        private String user_package_price;
        private String user_package_credit;
        private String user_package_startdate;
        private String user_package_expire_date;
        private String user_package_id;
        private String user_gender;
        private String user_dob;
        private String profile_summary_id;
        private String profile_summary_seekerid;
        private String profile_summary_totalyear;
        private String profile_summary_currentsalary;
        private String profile_summary_companyname;
        private String profile_summary_positions;
        private String profile_summary_currentcountry_id;
        private String profile_summary_currentcountry;
        private String profile_summary_jobtype_id;
        private String profile_summary_jobtype;
        private String profile_summary_noticeperiod_id;
        private String profile_summary_noticeperiod;
        private String profile_summary_resumeheadline;
        private String profile_summary_skills;
        private String profile_summary_exp;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_fname() {
            return user_fname;
        }

        public void setUser_fname(String user_fname) {
            this.user_fname = user_fname;
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

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_age() {
            return user_age;
        }

        public void setUser_age(String user_age) {
            this.user_age = user_age;
        }

        public String getUser_country_id() {
            return user_country_id;
        }

        public void setUser_country_id(String user_country_id) {
            this.user_country_id = user_country_id;
        }

        public String getUser_country_name() {
            return user_country_name;
        }

        public void setUser_country_name(String user_country_name) {
            this.user_country_name = user_country_name;
        }

        public String getUser_workpermit() {
            return user_workpermit;
        }

        public void setUser_workpermit(String user_workpermit) {
            this.user_workpermit = user_workpermit;
        }

        public String getUser_permitcountry_id() {
            return user_permitcountry_id;
        }

        public void setUser_permitcountry_id(String user_permitcountry_id) {
            this.user_permitcountry_id = user_permitcountry_id;
        }

        public String getUser_permitcountry() {
            return user_permitcountry;
        }

        public void setUser_permitcountry(String user_permitcountry) {
            this.user_permitcountry = user_permitcountry;
        }

        public String getUser_nationality() {
            return user_nationality;
        }

        public void setUser_nationality(String user_nationality) {
            this.user_nationality = user_nationality;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getUser_resume() {
            return user_resume;
        }

        public void setUser_resume(String user_resume) {
            this.user_resume = user_resume;
        }

        public String getUser_payment_id() {
            return user_payment_id;
        }

        public void setUser_payment_id(String user_payment_id) {
            this.user_payment_id = user_payment_id;
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

        public String getUser_package_id() {
            return user_package_id;
        }

        public void setUser_package_id(String user_package_id) {
            this.user_package_id = user_package_id;
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

        public String getProfile_summary_id() {
            return profile_summary_id;
        }

        public void setProfile_summary_id(String profile_summary_id) {
            this.profile_summary_id = profile_summary_id;
        }

        public String getProfile_summary_seekerid() {
            return profile_summary_seekerid;
        }

        public void setProfile_summary_seekerid(String profile_summary_seekerid) {
            this.profile_summary_seekerid = profile_summary_seekerid;
        }

        public String getProfile_summary_totalyear() {
            return profile_summary_totalyear;
        }

        public void setProfile_summary_totalyear(String profile_summary_totalyear) {
            this.profile_summary_totalyear = profile_summary_totalyear;
        }

        public String getProfile_summary_currentsalary() {
            return profile_summary_currentsalary;
        }

        public void setProfile_summary_currentsalary(String profile_summary_currentsalary) {
            this.profile_summary_currentsalary = profile_summary_currentsalary;
        }

        public String getProfile_summary_companyname() {
            return profile_summary_companyname;
        }

        public void setProfile_summary_companyname(String profile_summary_companyname) {
            this.profile_summary_companyname = profile_summary_companyname;
        }

        public String getProfile_summary_positions() {
            return profile_summary_positions;
        }

        public void setProfile_summary_positions(String profile_summary_positions) {
            this.profile_summary_positions = profile_summary_positions;
        }

        public String getProfile_summary_currentcountry_id() {
            return profile_summary_currentcountry_id;
        }

        public void setProfile_summary_currentcountry_id(String profile_summary_currentcountry_id) {
            this.profile_summary_currentcountry_id = profile_summary_currentcountry_id;
        }

        public String getProfile_summary_currentcountry() {
            return profile_summary_currentcountry;
        }

        public void setProfile_summary_currentcountry(String profile_summary_currentcountry) {
            this.profile_summary_currentcountry = profile_summary_currentcountry;
        }

        public String getProfile_summary_jobtype_id() {
            return profile_summary_jobtype_id;
        }

        public void setProfile_summary_jobtype_id(String profile_summary_jobtype_id) {
            this.profile_summary_jobtype_id = profile_summary_jobtype_id;
        }

        public String getProfile_summary_jobtype() {
            return profile_summary_jobtype;
        }

        public void setProfile_summary_jobtype(String profile_summary_jobtype) {
            this.profile_summary_jobtype = profile_summary_jobtype;
        }

        public String getProfile_summary_noticeperiod_id() {
            return profile_summary_noticeperiod_id;
        }

        public void setProfile_summary_noticeperiod_id(String profile_summary_noticeperiod_id) {
            this.profile_summary_noticeperiod_id = profile_summary_noticeperiod_id;
        }

        public String getProfile_summary_noticeperiod() {
            return profile_summary_noticeperiod;
        }

        public void setProfile_summary_noticeperiod(String profile_summary_noticeperiod) {
            this.profile_summary_noticeperiod = profile_summary_noticeperiod;
        }

        public String getProfile_summary_resumeheadline() {
            return profile_summary_resumeheadline;
        }

        public void setProfile_summary_resumeheadline(String profile_summary_resumeheadline) {
            this.profile_summary_resumeheadline = profile_summary_resumeheadline;
        }

        public String getProfile_summary_skills() {
            return profile_summary_skills;
        }

        public void setProfile_summary_skills(String profile_summary_skills) {
            this.profile_summary_skills = profile_summary_skills;
        }

        public String getProfile_summary_exp() {
            return profile_summary_exp;
        }

        public void setProfile_summary_exp(String profile_summary_exp) {
            this.profile_summary_exp = profile_summary_exp;
        }
    }

    public static class WorksListBean {
        /**
         * jobseeker_workexp_id : 72
         * jobseeker_workexp_seekerid : 29
         * positions : html designer
         * jobseeker_workexp_totalyear : 7.11
         * jobseeker_workexp_noticeperiod_id : null
         * jobseeker_workexp_noticeperiod : null
         * jobseeker_workexp_companyname : infosys   a
         * jobseeker_workexp_companyindus_id : 6
         * jobseeker_workexp_companyindus : Electricals / Switchgears
         * jobseeker_workexp_dept_id : 6
         * jobseeker_workexp_dept : Project Manager IT /Software
         */

        private String jobseeker_workexp_id;
        private String jobseeker_workexp_seekerid;
        private String positions;
        private String jobseeker_workexp_totalyear;
        private Object jobseeker_workexp_noticeperiod_id;
        private Object jobseeker_workexp_noticeperiod;
        private String jobseeker_workexp_companyname;
        private String jobseeker_workexp_companyindus_id;
        private String jobseeker_workexp_companyindus;
        private String jobseeker_workexp_dept_id;
        private String jobseeker_workexp_dept;

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

        public String getPositions() {
            return positions;
        }

        public void setPositions(String positions) {
            this.positions = positions;
        }

        public String getJobseeker_workexp_totalyear() {
            return jobseeker_workexp_totalyear;
        }

        public void setJobseeker_workexp_totalyear(String jobseeker_workexp_totalyear) {
            this.jobseeker_workexp_totalyear = jobseeker_workexp_totalyear;
        }

        public Object getJobseeker_workexp_noticeperiod_id() {
            return jobseeker_workexp_noticeperiod_id;
        }

        public void setJobseeker_workexp_noticeperiod_id(Object jobseeker_workexp_noticeperiod_id) {
            this.jobseeker_workexp_noticeperiod_id = jobseeker_workexp_noticeperiod_id;
        }

        public Object getJobseeker_workexp_noticeperiod() {
            return jobseeker_workexp_noticeperiod;
        }

        public void setJobseeker_workexp_noticeperiod(Object jobseeker_workexp_noticeperiod) {
            this.jobseeker_workexp_noticeperiod = jobseeker_workexp_noticeperiod;
        }

        public String getJobseeker_workexp_companyname() {
            return jobseeker_workexp_companyname;
        }

        public void setJobseeker_workexp_companyname(String jobseeker_workexp_companyname) {
            this.jobseeker_workexp_companyname = jobseeker_workexp_companyname;
        }

        public String getJobseeker_workexp_companyindus_id() {
            return jobseeker_workexp_companyindus_id;
        }

        public void setJobseeker_workexp_companyindus_id(String jobseeker_workexp_companyindus_id) {
            this.jobseeker_workexp_companyindus_id = jobseeker_workexp_companyindus_id;
        }

        public String getJobseeker_workexp_companyindus() {
            return jobseeker_workexp_companyindus;
        }

        public void setJobseeker_workexp_companyindus(String jobseeker_workexp_companyindus) {
            this.jobseeker_workexp_companyindus = jobseeker_workexp_companyindus;
        }

        public String getJobseeker_workexp_dept_id() {
            return jobseeker_workexp_dept_id;
        }

        public void setJobseeker_workexp_dept_id(String jobseeker_workexp_dept_id) {
            this.jobseeker_workexp_dept_id = jobseeker_workexp_dept_id;
        }

        public String getJobseeker_workexp_dept() {
            return jobseeker_workexp_dept;
        }

        public void setJobseeker_workexp_dept(String jobseeker_workexp_dept) {
            this.jobseeker_workexp_dept = jobseeker_workexp_dept;
        }
    }

    public static class EducationsListBean {
        /**
         * jobseeker_education_id : 44
         * jobseeker_education_seekerid : 29
         * jobseeker_education_seekertype : 2
         * jobseeker_education_course : Diploma
         * jobseeker_education_special : Information technology
         * jobseeker_education_institute : shanti institute of technology meerut
         * jobseeker_education_coursetype : 1
         * jobseeker_education_year : 2011
         */

        private String jobseeker_education_id;
        private String jobseeker_education_seekerid;
        private String jobseeker_education_seekertype;
        private String jobseeker_education_course;
        private String jobseeker_education_special;
        private String jobseeker_education_institute;
        private String jobseeker_education_coursetype;
        private String jobseeker_education_year;

        public String getJobseeker_education_id() {
            return jobseeker_education_id;
        }

        public void setJobseeker_education_id(String jobseeker_education_id) {
            this.jobseeker_education_id = jobseeker_education_id;
        }

        public String getJobseeker_education_seekerid() {
            return jobseeker_education_seekerid;
        }

        public void setJobseeker_education_seekerid(String jobseeker_education_seekerid) {
            this.jobseeker_education_seekerid = jobseeker_education_seekerid;
        }

        public String getJobseeker_education_seekertype() {
            return jobseeker_education_seekertype;
        }

        public void setJobseeker_education_seekertype(String jobseeker_education_seekertype) {
            this.jobseeker_education_seekertype = jobseeker_education_seekertype;
        }

        public String getJobseeker_education_course() {
            return jobseeker_education_course;
        }

        public void setJobseeker_education_course(String jobseeker_education_course) {
            this.jobseeker_education_course = jobseeker_education_course;
        }

        public String getJobseeker_education_special() {
            return jobseeker_education_special;
        }

        public void setJobseeker_education_special(String jobseeker_education_special) {
            this.jobseeker_education_special = jobseeker_education_special;
        }

        public String getJobseeker_education_institute() {
            return jobseeker_education_institute;
        }

        public void setJobseeker_education_institute(String jobseeker_education_institute) {
            this.jobseeker_education_institute = jobseeker_education_institute;
        }

        public String getJobseeker_education_coursetype() {
            return jobseeker_education_coursetype;
        }

        public void setJobseeker_education_coursetype(String jobseeker_education_coursetype) {
            this.jobseeker_education_coursetype = jobseeker_education_coursetype;
        }

        public String getJobseeker_education_year() {
            return jobseeker_education_year;
        }

        public void setJobseeker_education_year(String jobseeker_education_year) {
            this.jobseeker_education_year = jobseeker_education_year;
        }
    }
}