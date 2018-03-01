package com.androidtutorialpoint.ineed.proj.webservices;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public interface ApiList {
    String BASE_URL="https://www.ineed.biz/webservice/";
    String IMG_BASE = "https://www.ineed.biz/upload/";
    String ABOUT=BASE_URL+"about";
    String SIGNUP=BASE_URL+"signup";
    String FORGETPASSWORD=BASE_URL+"forgetpassword";
    String LOGIN=BASE_URL+"login/";
    String GET_PACKAGE=BASE_URL+"get_package";
    String MAKE_PAYMENT =BASE_URL+"makepayment";
    String UPDATE_PASSWORD = BASE_URL+"change_pass";
    String TERM=BASE_URL+"term";
    String CONTACT_US =BASE_URL+"contact_us";
    String JOBSEEKER_DASHBOARD=BASE_URL+"jobseeker_dashboard";
    String JOBSEEKER_PROFILE = BASE_URL+"jobseeker_profile";
    String HELP_CATEGORY=BASE_URL+"helpcategory";
    String HELP=BASE_URL+"help";
    String COUNTRY=BASE_URL+"country";
    String SEARCH=BASE_URL+"search";
    String EMPLOYER_VIEWED =BASE_URL+"employer_viewed";
    String EMPLOYER_PROFILE  =BASE_URL+"employer_profile";
    String EMPLOYER_PROFILE_EDIT = BASE_URL+"employer_edit_profile";
    String JOBSEEKER_OBJECTIVE = BASE_URL+"jobseeker_edit_objective";
    String JOBSEEKER_PROFILE_PIC = BASE_URL+"jobseeker_image";
    String EMP_PROFILE_PIC = BASE_URL+"employer_image";
    String JOBSEEKER_SKILLS = BASE_URL+"jobseekers_skills";
    String JOBSEEKER_PERSONAL_EDIT= BASE_URL+"jobseeker_edit_personal";
    String JOBSEEKER_ADD_EDU = BASE_URL+"jobseeker_add_education";
    String JOBSEEKER_ADMIN_LIST = BASE_URL+"admin_list";
    String JOBSEEKER_ADD_WORK = BASE_URL+"jobseekers_add_work_experience";
    String JOBSEEKER_UPLOAD_RESUME = BASE_URL+"jobseeker_resume";
    String JOBSEEKER_WORK_EXP_DELETE = BASE_URL+"work_delete";
    String JOBSEEKER_EDU_EXP_DELETE = BASE_URL+"education_delete";
    String EMP_JOBSEEKER_VIEWED = BASE_URL+"viewdetails";
    String EMP_ALREADY_JOBSEEKER_VIEWED = BASE_URL+"allreadyviewed";
    String GET_HISTORY=BASE_URL+"paymenthistory";


    String FILTERS = BASE_URL+"filters";
}
