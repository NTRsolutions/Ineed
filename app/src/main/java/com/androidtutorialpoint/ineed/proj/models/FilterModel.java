package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class FilterModel {

    /**
     * status : true
     * msg : All list of filters
     * jobtypes : [{"jobtype_id":"1","jobtype_name":"Full- Time"},{"jobtype_id":"2","jobtype_name":"Part -Time"},{"jobtype_id":"3","jobtype_name":"Contract"}]
     * experiences : [{"experience_id":"1","experience_name":"Fresher"},{"experience_id":"2","experience_name":"1-2"},{"experience_id":"3","experience_name":"2-3"},{"experience_id":"4","experience_name":"4-6"},{"experience_id":"5","experience_name":"6-8"},{"experience_id":"6","experience_name":"8-10"},{"experience_id":"7","experience_name":"10 +"}]
     * noticeperiods : [{"noticeperiod_id":"2","noticeperiod_name":"5 Days"},{"noticeperiod_id":"3","noticeperiod_name":"10 Days"},{"noticeperiod_id":"4","noticeperiod_name":"15 Days"},{"noticeperiod_id":"5","noticeperiod_name":"20 Days"},{"noticeperiod_id":"6","noticeperiod_name":"25 Days"},{"noticeperiod_id":"7","noticeperiod_name":"1 Month"},{"noticeperiod_id":"8","noticeperiod_name":"2 Months"},{"noticeperiod_id":"9","noticeperiod_name":"3 Months"},{"noticeperiod_id":"10","noticeperiod_name":"6 Months"}]
     * ctcs : [{"ctc_id":"3","ctc_name":"50000-100000"},{"ctc_id":"4","ctc_name":"100001-150000"},{"ctc_id":"5","ctc_name":"150001-200000"},{"ctc_id":"6","ctc_name":"200001-250000"},{"ctc_id":"7","ctc_name":"250001-300000"},{"ctc_id":"8","ctc_name":"300001-400000"},{"ctc_id":"9","ctc_name":"400001-500000"},{"ctc_id":"10","ctc_name":"500001-700000"},{"ctc_id":"11","ctc_name":"700001-1000000"},{"ctc_id":"12","ctc_name":"1000001 + Above"}]
     * ages : [{"age_id":"1","age_name":"10-20"},{"age_id":"2","age_name":"21-30"},{"age_id":"3","age_name":"31-40"},{"age_id":"4","age_name":"41-50"},{"age_id":"5","age_name":"51-60"},{"age_id":"6","age_name":"61-70"}]
     */

    private boolean status;
    private String msg;
    private List<JobtypesBean> jobtypes;
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
