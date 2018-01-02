package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by ask on 12/7/2017.
 */

public class EmpPackage {

    /**
     * status : true
     * response : {"employer_data":[{"employers_package_id":"2","employers_package_name":"SUPER","employers_package_prize":"1000","employers_package_time_frame":"2","employers_package_credit":"100","employers_package_start_date":"2017-12-05","employers_package_expire_date":"2018-02-05","employers_package_status":"1","employers_package_language_id":"4","employers_package_language_name":"SUPER","employers_package_language_language_code":"en","employers_package_language_employers_package_id":"2","employers_package_language_edit":"1","employers_package_language_status":"1"},{"employers_package_id":"3","employers_package_name":"QUICK","employers_package_prize":"1500","employers_package_time_frame":"3","employers_package_credit":"150","employers_package_start_date":"2017-12-05","employers_package_expire_date":"2018-04-05","employers_package_status":"1","employers_package_language_id":"6","employers_package_language_name":"QUICK","employers_package_language_language_code":"en","employers_package_language_employers_package_id":"3","employers_package_language_edit":"1","employers_package_language_status":"1"},{"employers_package_id":"6","employers_package_name":"PRO","employers_package_prize":"145","employers_package_time_frame":"4","employers_package_credit":"120","employers_package_start_date":"2017-12-06","employers_package_expire_date":"2018-06-06","employers_package_status":"0","employers_package_language_id":"16","employers_package_language_name":"PRO","employers_package_language_language_code":"en","employers_package_language_employers_package_id":"6","employers_package_language_edit":"1","employers_package_language_status":"0"},{"employers_package_id":"7","employers_package_name":"test","employers_package_prize":"150","employers_package_time_frame":"3","employers_package_credit":"120","employers_package_start_date":"2017-12-05","employers_package_expire_date":"2018-04-05","employers_package_status":"0","employers_package_language_id":"18","employers_package_language_name":"test","employers_package_language_language_code":"en","employers_package_language_employers_package_id":"7","employers_package_language_edit":"1","employers_package_language_status":"0"}]}
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
        private List<EmployerDataBean> employer_data;

        public List<EmployerDataBean> getEmployer_data() {
            return employer_data;
        }

        public void setEmployer_data(List<EmployerDataBean> employer_data) {
            this.employer_data = employer_data;
        }

        public static class EmployerDataBean {
            /**
             * employers_package_id : 2
             * employers_package_name : SUPER
             * employers_package_prize : 1000
             * employers_package_time_frame : 2
             * employers_package_credit : 100
             * employers_package_start_date : 2017-12-05
             * employers_package_expire_date : 2018-02-05
             * employers_package_status : 1
             * employers_package_language_id : 4
             * employers_package_language_name : SUPER
             * employers_package_language_language_code : en
             * employers_package_language_employers_package_id : 2
             * employers_package_language_edit : 1
             * employers_package_language_status : 1
             */

            private String employers_package_id;
            private String employers_package_name;
            private String employers_package_prize;
            private String employers_package_time_frame;
            private String employers_package_credit;
            private String employers_package_start_date;
            private String employers_package_expire_date;
            private String employers_package_status;
            private String employers_package_language_id;
            private String employers_package_language_name;
            private String employers_package_language_language_code;
            private String employers_package_language_employers_package_id;
            private String employers_package_language_edit;
            private String employers_package_language_status;

            public String getEmployers_package_id() {
                return employers_package_id;
            }

            public void setEmployers_package_id(String employers_package_id) {
                this.employers_package_id = employers_package_id;
            }

            public String getEmployers_package_name() {
                return employers_package_name;
            }

            public void setEmployers_package_name(String employers_package_name) {
                this.employers_package_name = employers_package_name;
            }

            public String getEmployers_package_prize() {
                return employers_package_prize;
            }

            public void setEmployers_package_prize(String employers_package_prize) {
                this.employers_package_prize = employers_package_prize;
            }

            public String getEmployers_package_time_frame() {
                return employers_package_time_frame;
            }

            public void setEmployers_package_time_frame(String employers_package_time_frame) {
                this.employers_package_time_frame = employers_package_time_frame;
            }

            public String getEmployers_package_credit() {
                return employers_package_credit;
            }

            public void setEmployers_package_credit(String employers_package_credit) {
                this.employers_package_credit = employers_package_credit;
            }

            public String getEmployers_package_start_date() {
                return employers_package_start_date;
            }

            public void setEmployers_package_start_date(String employers_package_start_date) {
                this.employers_package_start_date = employers_package_start_date;
            }

            public String getEmployers_package_expire_date() {
                return employers_package_expire_date;
            }

            public void setEmployers_package_expire_date(String employers_package_expire_date) {
                this.employers_package_expire_date = employers_package_expire_date;
            }

            public String getEmployers_package_status() {
                return employers_package_status;
            }

            public void setEmployers_package_status(String employers_package_status) {
                this.employers_package_status = employers_package_status;
            }

            public String getEmployers_package_language_id() {
                return employers_package_language_id;
            }

            public void setEmployers_package_language_id(String employers_package_language_id) {
                this.employers_package_language_id = employers_package_language_id;
            }

            public String getEmployers_package_language_name() {
                return employers_package_language_name;
            }

            public void setEmployers_package_language_name(String employers_package_language_name) {
                this.employers_package_language_name = employers_package_language_name;
            }

            public String getEmployers_package_language_language_code() {
                return employers_package_language_language_code;
            }

            public void setEmployers_package_language_language_code(String employers_package_language_language_code) {
                this.employers_package_language_language_code = employers_package_language_language_code;
            }

            public String getEmployers_package_language_employers_package_id() {
                return employers_package_language_employers_package_id;
            }

            public void setEmployers_package_language_employers_package_id(String employers_package_language_employers_package_id) {
                this.employers_package_language_employers_package_id = employers_package_language_employers_package_id;
            }

            public String getEmployers_package_language_edit() {
                return employers_package_language_edit;
            }

            public void setEmployers_package_language_edit(String employers_package_language_edit) {
                this.employers_package_language_edit = employers_package_language_edit;
            }

            public String getEmployers_package_language_status() {
                return employers_package_language_status;
            }

            public void setEmployers_package_language_status(String employers_package_language_status) {
                this.employers_package_language_status = employers_package_language_status;
            }
        }
    }
}
