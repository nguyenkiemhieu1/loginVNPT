package com.example.login.presenter;

public class ServiceUrl {
    public static final String LOGIN_V2_URL = "api/login/v2/";
    public static final String GET_DOC_WAIT_URL = "api/document/getlistwaitingdocument/";
    public static final String GET_DETAIL_DOCUMENT_WAITING_URL = "api/document/getdetaildocument/{id}/";
    public static final String CHECK_FINISH_DOC_URL = "api/document/checkfinish/{id}/";
    public static final String GET_LIST_TYPE_CHANGE_DOC_URL = "api/document/getapprovedvalue/";
    public static final String MARK_DOC_URL = "api/document/signeddocument/{id}/";
    public static final String GET_LIST_TYPE_CHANGE_DOC_VIEW_FILES_URL = "api/document/getapprovedvaluedetailfile/";
    public static final String FINISH_DOC_URL_V2 = "api/document/finish/v2/";

    public static final String GET_DOCUMENT_SAVED = "api/brief/getlistbrief/";
    public static final String EXCEPTION_URL = "api/createexception/";
    public static final String POST_DOCUMENT_SAVED = "api/brief/savebrief/";
}
