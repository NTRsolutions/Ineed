package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by ask on 12/6/2017.
 */

public class JobSeekerPackage {

    /**
     * status : true
     * response : {"jobseker_data":[{"jobseekars_package_id":"1","jobseekars_package_name":"Basic","jobseekars_package_prize":"100","jobseekars_package_time_frame":"1","jobseekars_package_start_date":"2017-11-06","jobseekars_package_expire_date":"2017-12-06","jobseekars_package_status":"1","jobseekars_package_language_id":"2","jobseekars_package_language_name":"Basic","jobseekars_package_language_language_code":"en","jobseekars_package_language_jobseekars_package_id":"1","jobseekars_package_language_edit":"1","jobseekars_package_language_status":"1"},{"jobseekars_package_id":"2","jobseekars_package_name":"STANDARD","jobseekars_package_prize":"500","jobseekars_package_time_frame":"2","jobseekars_package_start_date":"2017-12-02","jobseekars_package_expire_date":"2018-02-02","jobseekars_package_status":"1","jobseekars_package_language_id":"4","jobseekars_package_language_name":"STANDARD","jobseekars_package_language_language_code":"en","jobseekars_package_language_jobseekars_package_id":"2","jobseekars_package_language_edit":"1","jobseekars_package_language_status":"1"},{"jobseekars_package_id":"3","jobseekars_package_name":"PREMIUM","jobseekars_package_prize":"1000","jobseekars_package_time_frame":"3","jobseekars_package_start_date":"2017-11-06","jobseekars_package_expire_date":"2018-03-06","jobseekars_package_status":"1","jobseekars_package_language_id":"6","jobseekars_package_language_name":"PREMIUM","jobseekars_package_language_language_code":"en","jobseekars_package_language_jobseekars_package_id":"3","jobseekars_package_language_edit":"1","jobseekars_package_language_status":"1"}]}
     */

    private boolean status;
    private ResponseBean response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        private List<JobsekerDataBean> jobseker_data;

        public List<JobsekerDataBean> getJobseker_data() {
            return jobseker_data;
        }

        public void setJobseker_data(List<JobsekerDataBean> jobseker_data) {
            this.jobseker_data = jobseker_data;
        }

        public static class JobsekerDataBean {
            /**
             * jobseekars_package_id : 1
             * jobseekars_package_name : Basic
             * jobseekars_package_prize : 100
             * jobseekars_package_time_frame : 1
             * jobseekars_package_start_date : 2017-11-06
             * jobseekars_package_expire_date : 2017-12-06
             * jobseekars_package_status : 1
             * jobseekars_package_language_id : 2
             * jobseekars_package_language_name : Basic
             * jobseekars_package_language_language_code : en
             * jobseekars_package_language_jobseekars_package_id : 1
             * jobseekars_package_language_edit : 1
             * jobseekars_package_language_status : 1
             */

            private String jobseekars_package_id;
            private String jobseekars_package_name;
            private String jobseekars_package_prize;
            private String jobseekars_package_time_frame;
            private String jobseekars_package_start_date;
            private String jobseekars_package_expire_date;
            private String jobseekars_package_status;
            private String jobseekars_package_language_id;
            private String jobseekars_package_language_name;
            private String jobseekars_package_language_language_code;
            private String jobseekars_package_language_jobseekars_package_id;
            private String jobseekars_package_language_edit;
            private String jobseekars_package_language_status;

            public String getJobseekars_package_id() {
                return jobseekars_package_id;
            }

            public void setJobseekars_package_id(String jobseekars_package_id) {
                this.jobseekars_package_id = jobseekars_package_id;
            }

            public String getJobseekars_package_name() {
                return jobseekars_package_name;
            }

            public void setJobseekars_package_name(String jobseekars_package_name) {
                this.jobseekars_package_name = jobseekars_package_name;
            }

            public String getJobseekars_package_prize() {
                return jobseekars_package_prize;
            }

            public void setJobseekars_package_prize(String jobseekars_package_prize) {
                this.jobseekars_package_prize = jobseekars_package_prize;
            }

            public String getJobseekars_package_time_frame() {
                return jobseekars_package_time_frame;
            }

            public void setJobseekars_package_time_frame(String jobseekars_package_time_frame) {
                this.jobseekars_package_time_frame = jobseekars_package_time_frame;
            }

            public String getJobseekars_package_start_date() {
                return jobseekars_package_start_date;
            }

            public void setJobseekars_package_start_date(String jobseekars_package_start_date) {
                this.jobseekars_package_start_date = jobseekars_package_start_date;
            }

            public String getJobseekars_package_expire_date() {
                return jobseekars_package_expire_date;
            }

            public void setJobseekars_package_expire_date(String jobseekars_package_expire_date) {
                this.jobseekars_package_expire_date = jobseekars_package_expire_date;
            }

            public String getJobseekars_package_status() {
                return jobseekars_package_status;
            }

            public void setJobseekars_package_status(String jobseekars_package_status) {
                this.jobseekars_package_status = jobseekars_package_status;
            }

            public String getJobseekars_package_language_id() {
                return jobseekars_package_language_id;
            }

            public void setJobseekars_package_language_id(String jobseekars_package_language_id) {
                this.jobseekars_package_language_id = jobseekars_package_language_id;
            }

            public String getJobseekars_package_language_name() {
                return jobseekars_package_language_name;
            }

            public void setJobseekars_package_language_name(String jobseekars_package_language_name) {
                this.jobseekars_package_language_name = jobseekars_package_language_name;
            }

            public String getJobseekars_package_language_language_code() {
                return jobseekars_package_language_language_code;
            }

            public void setJobseekars_package_language_language_code(String jobseekars_package_language_language_code) {
                this.jobseekars_package_language_language_code = jobseekars_package_language_language_code;
            }

            public String getJobseekars_package_language_jobseekars_package_id() {
                return jobseekars_package_language_jobseekars_package_id;
            }

            public void setJobseekars_package_language_jobseekars_package_id(String jobseekars_package_language_jobseekars_package_id) {
                this.jobseekars_package_language_jobseekars_package_id = jobseekars_package_language_jobseekars_package_id;
            }

            public String getJobseekars_package_language_edit() {
                return jobseekars_package_language_edit;
            }

            public void setJobseekars_package_language_edit(String jobseekars_package_language_edit) {
                this.jobseekars_package_language_edit = jobseekars_package_language_edit;
            }

            public String getJobseekars_package_language_status() {
                return jobseekars_package_language_status;
            }

            public void setJobseekars_package_language_status(String jobseekars_package_language_status) {
                this.jobseekars_package_language_status = jobseekars_package_language_status;
            }
        }
    }
}
