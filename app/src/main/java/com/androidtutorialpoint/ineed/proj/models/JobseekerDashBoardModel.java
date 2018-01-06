package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class JobseekerDashBoardModel {
    /**
     * status : true
     * jobseeker_dashboard : {"user_viewed":1,"user_package_id":"Basic","user_package_expire_date":"2018-02-05"}
     */

    private boolean status;
    private JobseekerDashboardBean jobseeker_dashboard;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public JobseekerDashboardBean getJobseeker_dashboard() {
        return jobseeker_dashboard;
    }

    public void setJobseeker_dashboard(JobseekerDashboardBean jobseeker_dashboard) {
        this.jobseeker_dashboard = jobseeker_dashboard;
    }

    public static class JobseekerDashboardBean {
        /**
         * user_viewed : 1
         * user_package_id : Basic
         * user_package_expire_date : 2018-02-05
         */

        private int user_viewed;

        private String user_package_id;
        private String user_package_expire_date;

        public int getUser_viewed() {
            return user_viewed;
        }

        public void setUser_viewed(int user_viewed) {
            this.user_viewed = user_viewed;
        }

        public String getUser_package_id() {
            return user_package_id;
        }

        public void setUser_package_id(String user_package_id) {
            this.user_package_id = user_package_id;
        }

        public String getUser_package_expire_date() {
            return user_package_expire_date;
        }

        public void setUser_package_expire_date(String user_package_expire_date) {
            this.user_package_expire_date = user_package_expire_date;
        }
    }
}
