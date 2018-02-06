package com.androidtutorialpoint.ineed.proj.models;

import java.util.List;

/**
 * Created by rakhi on 2/6/2018.
 */

public class SkillsModel {

    /**
     * user_id : 206
     * skiils_list : [{"skills":"php","yesr":"2"},{"skills":"HTML","yesr":"3"},{"skills":"css","yesr":"2"},{"skills":"HTML2","yesr":"2"},{"skills":"HTML5","yesr":"4"},{"skills":"core php","yesr":"2"}]
     */

    private int user_id;
    private List<SkiilsListBean> skiils_list;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<SkiilsListBean> getSkiils_list() {
        return skiils_list;
    }

    public void setSkiils_list(List<SkiilsListBean> skiils_list) {
        this.skiils_list = skiils_list;
    }

    public static class SkiilsListBean {
        /**
         * skills : php
         * yesr : 2
         */

        private String skills;
        private String yesr;

        public String getSkills() {
            return skills;
        }

        public void setSkills(String skills) {
            this.skills = skills;
        }

        public String getYesr() {
            return yesr;
        }

        public void setYesr(String yesr) {
            this.yesr = yesr;
        }
    }
}
