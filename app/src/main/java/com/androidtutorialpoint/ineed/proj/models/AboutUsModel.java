package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AboutUsModel {
    /**
     * status : true
     * response : {"about_list":{"cms_language_title":"About us","cms_language_content":"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                                                      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                         Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                             Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                                 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.  "}}
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
        /**
         * about_list : {"cms_language_title":"About us","cms_language_content":"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                                                      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                         Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                             Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                                 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.  "}
         */

        private AboutListBean about_list;

        public AboutListBean getAbout_list() {
            return about_list;
        }

        public void setAbout_list(AboutListBean about_list) {
            this.about_list = about_list;
        }

        public static class AboutListBean {
            /**
             * cms_language_title : About us
             * cms_language_content : Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                                                      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                         Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                             Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.                                                 Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus maximus ultrices dui quis placerat. Maecenas nec diam non ipsum viverra vulputate ut sed risus. Praesent luctus nisi quam, et malesuada nisl convallis non. Aenean efficitur arcu ac volutpat ultricies. Nulla pulvinar magna ut orci ultrices euismod. Nam non volutpat augue. Nulla fringilla nisi ex, dapibus auctor est egestas ac. Etiam convallis ac mauris sodales eleifend.
             */

            private String cms_language_title;
            private String cms_language_content;

            public String getCms_language_title() {
                return cms_language_title;
            }

            public void setCms_language_title(String cms_language_title) {
                this.cms_language_title = cms_language_title;
            }

            public String getCms_language_content() {
                return cms_language_content;
            }

            public void setCms_language_content(String cms_language_content) {
                this.cms_language_content = cms_language_content;
            }
        }
    }
}
