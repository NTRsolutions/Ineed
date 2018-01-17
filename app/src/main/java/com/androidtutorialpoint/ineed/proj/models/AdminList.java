package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Rakhi on 1/15/2018.
 * Mobile number 9958187463
 */

public class AdminList {

    /**
     * status : true
     * msg : All list of filters
     * jobtypes : [{"jobtype_id":"1","jobtype_name":"Full- Time"},{"jobtype_id":"2","jobtype_name":"Part -Time"},{"jobtype_id":"3","jobtype_name":"Contract"},{"jobtype_id":"4","jobtype_name":"full time"}]
     * industries : [{"industry_id":"1","industry_name":"Accounting/Finance/Insurance","industry_status":"1"},{"industry_id":"2","industry_name":"Agriculture/Dairy","industry_status":"1"},{"industry_id":"3","industry_name":"Chemicals/PetroChemical/Plastic/Rubber","industry_status":"1"},{"industry_id":"4","industry_name":"Courier/Transportation/Freight","industry_status":"1"},{"industry_id":"5","industry_name":"Education/Teaching/Training","industry_status":"1"},{"industry_id":"6","industry_name":"Electricals / Switchgears","industry_status":"1"},{"industry_id":"7","industry_name":"Export/Import","industry_status":"1"},{"industry_id":"8","industry_name":"Gems & Jewellery","industry_status":"1"},{"industry_id":"9","industry_name":"Internet/Ecommerce/IT-Hardware Networking","industry_status":"1"},{"industry_id":"10","industry_name":"IT-Software/Software Services/Web Services","industry_status":"1"},{"industry_id":"11","industry_name":"Medical/Healthcare/Hospital","industry_status":"1"},{"industry_id":"12","industry_name":"Paper/Publishing","industry_status":"1"},{"industry_id":"13","industry_name":"Real Estate/Property","industry_status":"1"},{"industry_id":"14","industry_name":"Wellness / Fitness / Sports / Beauty","industry_status":"1"},{"industry_id":"15","industry_name":"Other","industry_status":"1"}]
     * departments : [{"department_id":"1","department_name":"System Analysis","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"},{"department_id":"2","department_name":"Tech Support Engg","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"},{"department_id":"3","department_name":"Graphic / Web Developer","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"},{"department_id":"4","department_name":"Software Developer","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"},{"department_id":"5","department_name":"Web Developer","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"},{"department_id":"6","department_name":"Project Manager IT /Software","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"},{"department_id":"7","department_name":"Team Leader / Tech Lead","department_company_id":"0","department_branch_id":"0","department_admin_type":"0","department_status":"1"}]
     * experiences : [{"experience_id":"1","experience_name":"Fresher"},{"experience_id":"2","experience_name":"1-2"},{"experience_id":"3","experience_name":"2-3"},{"experience_id":"4","experience_name":"4-6"},{"experience_id":"5","experience_name":"6-8"},{"experience_id":"6","experience_name":"8-10"},{"experience_id":"7","experience_name":"10-40"}]
     * noticeperiods : [{"noticeperiod_id":"2","noticeperiod_name":"5 Days"},{"noticeperiod_id":"3","noticeperiod_name":"10 Days"},{"noticeperiod_id":"4","noticeperiod_name":"15 Days"},{"noticeperiod_id":"5","noticeperiod_name":"20 Days"},{"noticeperiod_id":"6","noticeperiod_name":"25 Days"},{"noticeperiod_id":"7","noticeperiod_name":"1 Month"},{"noticeperiod_id":"8","noticeperiod_name":"2 Months"},{"noticeperiod_id":"9","noticeperiod_name":"3 Months"},{"noticeperiod_id":"10","noticeperiod_name":"6 Months"}]
     * ctcs : [{"ctc_id":"3","ctc_name":"50000-100000"},{"ctc_id":"4","ctc_name":"100001-150000"},{"ctc_id":"5","ctc_name":"150001-200000"},{"ctc_id":"6","ctc_name":"200001-250000"},{"ctc_id":"7","ctc_name":"250001-300000"},{"ctc_id":"8","ctc_name":"300001-400000"},{"ctc_id":"9","ctc_name":"400001-500000"},{"ctc_id":"10","ctc_name":"500001-700000"},{"ctc_id":"11","ctc_name":"700001-1000000"},{"ctc_id":"12","ctc_name":"1000001 + Above"}]
     * ages : [{"age_id":"1","age_name":"10-20"},{"age_id":"2","age_name":"21-30"},{"age_id":"3","age_name":"31-40"},{"age_id":"4","age_name":"41-50"},{"age_id":"5","age_name":"51-60"},{"age_id":"6","age_name":"61-70"}]
     */

    private boolean status;
    private String msg;
    private List<JobtypesBean> jobtypes;
    private List<IndustriesBean> industries;
    private List<DepartmentsBean> departments;
    private List<ExperiencesBean> experiences;
    private List<NoticeperiodsBean> noticeperiods;
    private List<CtcsBean> ctcs;
    private List<AgesBean> ages;

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

    public List<JobtypesBean> getJobtypes() {
        return jobtypes;
    }

    public void setJobtypes(List<JobtypesBean> jobtypes) {
        this.jobtypes = jobtypes;
    }

    public List<IndustriesBean> getIndustries() {
        return industries;
    }

    public void setIndustries(List<IndustriesBean> industries) {
        this.industries = industries;
    }

    public List<DepartmentsBean> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentsBean> departments) {
        this.departments = departments;
    }

    public List<ExperiencesBean> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperiencesBean> experiences) {
        this.experiences = experiences;
    }

    public List<NoticeperiodsBean> getNoticeperiods() {
        return noticeperiods;
    }

    public void setNoticeperiods(List<NoticeperiodsBean> noticeperiods) {
        this.noticeperiods = noticeperiods;
    }

    public List<CtcsBean> getCtcs() {
        return ctcs;
    }

    public void setCtcs(List<CtcsBean> ctcs) {
        this.ctcs = ctcs;
    }

    public List<AgesBean> getAges() {
        return ages;
    }

    public void setAges(List<AgesBean> ages) {
        this.ages = ages;
    }

    public static class JobtypesBean {
        /**
         * jobtype_id : 1
         * jobtype_name : Full- Time
         */

        private String jobtype_id;
        private String jobtype_name;

        public String getJobtype_id() {
            return jobtype_id;
        }

        public void setJobtype_id(String jobtype_id) {
            this.jobtype_id = jobtype_id;
        }

        public String getJobtype_name() {
            return jobtype_name;
        }

        public void setJobtype_name(String jobtype_name) {
            this.jobtype_name = jobtype_name;
        }
    }

    public static class IndustriesBean {
        /**
         * industry_id : 1
         * industry_name : Accounting/Finance/Insurance
         * industry_status : 1
         */

        private String industry_id;
        private String industry_name;
        private String industry_status;

        public String getIndustry_id() {
            return industry_id;
        }

        public void setIndustry_id(String industry_id) {
            this.industry_id = industry_id;
        }

        public String getIndustry_name() {
            return industry_name;
        }

        public void setIndustry_name(String industry_name) {
            this.industry_name = industry_name;
        }

        public String getIndustry_status() {
            return industry_status;
        }

        public void setIndustry_status(String industry_status) {
            this.industry_status = industry_status;
        }
    }

    public static class DepartmentsBean {
        /**
         * department_id : 1
         * department_name : System Analysis
         * department_company_id : 0
         * department_branch_id : 0
         * department_admin_type : 0
         * department_status : 1
         */

        private String department_id;
        private String department_name;
        private String department_company_id;
        private String department_branch_id;
        private String department_admin_type;
        private String department_status;

        public String getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getDepartment_company_id() {
            return department_company_id;
        }

        public void setDepartment_company_id(String department_company_id) {
            this.department_company_id = department_company_id;
        }

        public String getDepartment_branch_id() {
            return department_branch_id;
        }

        public void setDepartment_branch_id(String department_branch_id) {
            this.department_branch_id = department_branch_id;
        }

        public String getDepartment_admin_type() {
            return department_admin_type;
        }

        public void setDepartment_admin_type(String department_admin_type) {
            this.department_admin_type = department_admin_type;
        }

        public String getDepartment_status() {
            return department_status;
        }

        public void setDepartment_status(String department_status) {
            this.department_status = department_status;
        }
    }

    public static class ExperiencesBean {
        /**
         * experience_id : 1
         * experience_name : Fresher
         */

        private String experience_id;
        private String experience_name;

        public String getExperience_id() {
            return experience_id;
        }

        public void setExperience_id(String experience_id) {
            this.experience_id = experience_id;
        }

        public String getExperience_name() {
            return experience_name;
        }

        public void setExperience_name(String experience_name) {
            this.experience_name = experience_name;
        }
    }

    public static class NoticeperiodsBean {
        /**
         * noticeperiod_id : 2
         * noticeperiod_name : 5 Days
         */

        private String noticeperiod_id;
        private String noticeperiod_name;

        public String getNoticeperiod_id() {
            return noticeperiod_id;
        }

        public void setNoticeperiod_id(String noticeperiod_id) {
            this.noticeperiod_id = noticeperiod_id;
        }

        public String getNoticeperiod_name() {
            return noticeperiod_name;
        }

        public void setNoticeperiod_name(String noticeperiod_name) {
            this.noticeperiod_name = noticeperiod_name;
        }
    }

    public static class CtcsBean {
        /**
         * ctc_id : 3
         * ctc_name : 50000-100000
         */

        private String ctc_id;
        private String ctc_name;

        public String getCtc_id() {
            return ctc_id;
        }

        public void setCtc_id(String ctc_id) {
            this.ctc_id = ctc_id;
        }

        public String getCtc_name() {
            return ctc_name;
        }

        public void setCtc_name(String ctc_name) {
            this.ctc_name = ctc_name;
        }
    }

    public static class AgesBean {
        /**
         * age_id : 1
         * age_name : 10-20
         */

        private String age_id;
        private String age_name;

        public String getAge_id() {
            return age_id;
        }

        public void setAge_id(String age_id) {
            this.age_id = age_id;
        }

        public String getAge_name() {
            return age_name;
        }

        public void setAge_name(String age_name) {
            this.age_name = age_name;
        }
    }
}
