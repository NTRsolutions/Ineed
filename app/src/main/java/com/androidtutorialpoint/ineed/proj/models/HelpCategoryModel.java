package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class HelpCategoryModel {

    /**
     * status : true
     * response : {"helpcat_data":[{"helpcat_id":"2","helpcat_name":"Job FAQs","helpcat_icon":"job.png","helpcat_language_id":"4","helpcat_language_rid":"","helpcat_status":"1","helpcat_language_helpcat_id":"2","helpcat_language_name":"Job FAQs","helpcat_language_language_code":"en","helpcat_language_edit":"1","helpcat_language_status":"1"},{"helpcat_id":"4","helpcat_name":"Fraud & Safety","helpcat_icon":"icons8-lock-50.png","helpcat_language_id":"8","helpcat_language_rid":"","helpcat_status":"1","helpcat_language_helpcat_id":"4","helpcat_language_name":"Fraud & Safety","helpcat_language_language_code":"en","helpcat_language_edit":"1","helpcat_language_status":"1"},{"helpcat_id":"5","helpcat_name":"My Account & Ads","helpcat_icon":"user.png","helpcat_language_id":"10","helpcat_language_rid":"","helpcat_status":"0","helpcat_language_helpcat_id":"5","helpcat_language_name":"My Account & Ads","helpcat_language_language_code":"en","helpcat_language_edit":"1","helpcat_language_status":"1"},{"helpcat_id":"6","helpcat_name":"Login FAQs","helpcat_icon":"home.png","helpcat_language_id":"13","helpcat_language_rid":"","helpcat_status":"1","helpcat_language_helpcat_id":"6","helpcat_language_name":"Login FAQs","helpcat_language_language_code":"en","helpcat_language_edit":"1","helpcat_language_status":"1"}]}
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
        private List<HelpcatDataBean> helpcat_data;

        public List<HelpcatDataBean> getHelpcat_data() {
            return helpcat_data;
        }

        public void setHelpcat_data(List<HelpcatDataBean> helpcat_data) {
            this.helpcat_data = helpcat_data;
        }

        public static class HelpcatDataBean {
            /**
             * helpcat_id : 2
             * helpcat_name : Job FAQs
             * helpcat_icon : job.png
             * helpcat_language_id : 4
             * helpcat_language_rid :
             * helpcat_status : 1
             * helpcat_language_helpcat_id : 2
             * helpcat_language_name : Job FAQs
             * helpcat_language_language_code : en
             * helpcat_language_edit : 1
             * helpcat_language_status : 1
             */

            private String helpcat_id;
            private String helpcat_name;
            private String helpcat_icon;
            private String helpcat_language_id;
            private String helpcat_language_rid;
            private String helpcat_status;
            private String helpcat_language_helpcat_id;
            private String helpcat_language_name;
            private String helpcat_language_language_code;
            private String helpcat_language_edit;
            private String helpcat_language_status;

            public String getHelpcat_id() {
                return helpcat_id;
            }

            public void setHelpcat_id(String helpcat_id) {
                this.helpcat_id = helpcat_id;
            }

            public String getHelpcat_name() {
                return helpcat_name;
            }

            public void setHelpcat_name(String helpcat_name) {
                this.helpcat_name = helpcat_name;
            }

            public String getHelpcat_icon() {
                return helpcat_icon;
            }

            public void setHelpcat_icon(String helpcat_icon) {
                this.helpcat_icon = helpcat_icon;
            }

            public String getHelpcat_language_id() {
                return helpcat_language_id;
            }

            public void setHelpcat_language_id(String helpcat_language_id) {
                this.helpcat_language_id = helpcat_language_id;
            }

            public String getHelpcat_language_rid() {
                return helpcat_language_rid;
            }

            public void setHelpcat_language_rid(String helpcat_language_rid) {
                this.helpcat_language_rid = helpcat_language_rid;
            }

            public String getHelpcat_status() {
                return helpcat_status;
            }

            public void setHelpcat_status(String helpcat_status) {
                this.helpcat_status = helpcat_status;
            }

            public String getHelpcat_language_helpcat_id() {
                return helpcat_language_helpcat_id;
            }

            public void setHelpcat_language_helpcat_id(String helpcat_language_helpcat_id) {
                this.helpcat_language_helpcat_id = helpcat_language_helpcat_id;
            }

            public String getHelpcat_language_name() {
                return helpcat_language_name;
            }

            public void setHelpcat_language_name(String helpcat_language_name) {
                this.helpcat_language_name = helpcat_language_name;
            }

            public String getHelpcat_language_language_code() {
                return helpcat_language_language_code;
            }

            public void setHelpcat_language_language_code(String helpcat_language_language_code) {
                this.helpcat_language_language_code = helpcat_language_language_code;
            }

            public String getHelpcat_language_edit() {
                return helpcat_language_edit;
            }

            public void setHelpcat_language_edit(String helpcat_language_edit) {
                this.helpcat_language_edit = helpcat_language_edit;
            }

            public String getHelpcat_language_status() {
                return helpcat_language_status;
            }

            public void setHelpcat_language_status(String helpcat_language_status) {
                this.helpcat_language_status = helpcat_language_status;
            }
        }
    }
}
