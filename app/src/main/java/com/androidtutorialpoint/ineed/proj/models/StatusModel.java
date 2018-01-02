package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class StatusModel {


    /**
     * status : true
     * msg : Successfully sign up your account
     * user_data : {"user_id":"181","user_type":"2"}
     */

    private boolean status;
    private String msg;
    private UserDataBean user_data;

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

    public UserDataBean getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDataBean user_data) {
        this.user_data = user_data;
    }

    public static class UserDataBean {
        /**
         * user_id : 181
         * user_type : 2
         */

        private String user_id;
        private String user_type;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }
    }
}
