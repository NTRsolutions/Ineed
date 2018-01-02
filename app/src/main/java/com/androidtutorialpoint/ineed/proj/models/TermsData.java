package com.androidtutorialpoint.ineed.proj.models;

/**
 * Created by rakhi on 12/27/2017.
 */

public class TermsData {

    /**
     * status : true
     * response : {"term_list":{"cms_language_title":"term","cms_language_content":"In mathematics, a constant term is a term in an algebraic expression that has a value that is constantor cannot change, because it does not contain any modifiable variables. For example, in the quadratic polynomial. the 3 is a constant term.  "}}
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
         * term_list : {"cms_language_title":"term","cms_language_content":"In mathematics, a constant term is a term in an algebraic expression that has a value that is constantor cannot change, because it does not contain any modifiable variables. For example, in the quadratic polynomial. the 3 is a constant term.  "}
         */

        private TermListBean term_list;

        public TermListBean getTerm_list() {
            return term_list;
        }

        public void setTerm_list(TermListBean term_list) {
            this.term_list = term_list;
        }

        public static class TermListBean {
            /**
             * cms_language_title : term
             * cms_language_content : In mathematics, a constant term is a term in an algebraic expression that has a value that is constantor cannot change, because it does not contain any modifiable variables. For example, in the quadratic polynomial. the 3 is a constant term.
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
