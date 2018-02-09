package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by Rakhi.
 * Contact Number : +91 9958187463
 */
public class JobseekerDashBoardModel {

    /**
     * status : true
     * jobseeker_dashboard : {"user_viewed":4,"user_package_id":"Basic","user_price":"10","user_stardate":"2018-01-30","user_package_expire_date":"2018-03-30"}
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
         * user_viewed : 4
         * user_package_id : Basic
         * user_price : 10
         * user_stardate : 2018-01-30
         * user_package_expire_date : 2018-03-30
         */

        private int user_viewed;
        private String user_package_id;
        private String user_price;
        private String user_stardate;
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

        public String getUser_price() {
            return user_price;
        }

        public void setUser_price(String user_price) {
            this.user_price = user_price;
        }

        public String getUser_stardate() {
            return user_stardate;
        }

        public void setUser_stardate(String user_stardate) {
            this.user_stardate = user_stardate;
        }

        public String getUser_package_expire_date() {
            return user_package_expire_date;
        }

        public void setUser_package_expire_date(String user_package_expire_date) {
            this.user_package_expire_date = user_package_expire_date;
        }
    }
}
