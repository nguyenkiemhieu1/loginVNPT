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

    public static final String CHANGE_DOC_PROCESS_URL = "api/document/forwarddocument/";
    public static final String SEND_PROCESS_DOCUMENT_SAME_URL = "api/document/forwarddocumentcoeval/";
    public static final String CHANGE_DOC_NOTIFY_XEMDB_URL = "api/document/tranfernotify/";
    public static final String CHANGE_DOC_DIRECT_URL = "api/incommingdocument/promulgatedocument/";
    public static final String ISSUING_DOCUMENTS_COME_SAME_URL = "api/incommingdocument/promulgatedocumentcoeval/";
    public static final String CHANGE_DOC_SEND_URL = "api/outgoingdocument/tranferdocument/";
    public static final String CHANGE_DOC_RECEIVE_URL = "api/incommingdocument/tranferdocument/";
    public static final String SEND_DOCUMENT_COME_SAME_URL = "api/incommingdocument/tranferdocumentcoeval/";
    public static final String GET_PERSONS_OR_UNIT_EXPECTED_URL = "api/incommingdocument/getlistexpected/{id}/";
}
