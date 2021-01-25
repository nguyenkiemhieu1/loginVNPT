package com.example.login.presenter.ChangeDocumentDao;



import com.example.login.model.ChangeDocumentDirectRequest;
import com.example.login.model.ChangeDocumentNotifyRequest;
import com.example.login.model.ChangeDocumentReceiveRequest;
import com.example.login.model.ChangeDocumentSendRequest;
import com.example.login.model.ChangeProcessRequest;
import com.example.login.model.PersonOrUnitExpectedResponse;
import com.example.login.model.TypeChangeDocument.TypeChangeDocRequest;
import com.example.login.presenter.ChangeDocument.ChangeDocumentRespone;
import com.example.login.presenter.ServiceUrl;
import com.example.login.presenter.TypeChangeDocumentRespone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IChangeDocumentService {
    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_URL)
    Call<TypeChangeDocumentRespone> getTypeChange(@Body TypeChangeDocRequest typeChangeDocumentRequest);
    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_VIEW_FILES_URL)
    Call<TypeChangeDocumentRespone> getTypeChangeViewFiles(@Body TypeChangeDocRequest typeChangeDocumentRequest);
    @POST(ServiceUrl.CHANGE_DOC_PROCESS_URL)
    Call<ChangeDocumentRespone> changeProcess(@Body ChangeProcessRequest changeProcessRequest);
    @POST(ServiceUrl.SEND_PROCESS_DOCUMENT_SAME_URL)
    Call<ChangeDocumentRespone> sendProcessDocumentSame(@Body ChangeProcessRequest changeProcessRequest);
    @POST(ServiceUrl.CHANGE_DOC_NOTIFY_XEMDB_URL)
    Call<ChangeDocumentRespone> changeNotify(@Body ChangeDocumentNotifyRequest changeDocumentNotifyRequest);

    @POST(ServiceUrl.CHANGE_DOC_DIRECT_URL)
    Call<ChangeDocumentRespone> changeDirect(@Body ChangeDocumentDirectRequest changeDocumentDirectRequest);
    @POST(ServiceUrl.ISSUING_DOCUMENTS_COME_SAME_URL)
    Call<ChangeDocumentRespone> issuingDocumentComeSame(@Body ChangeDocumentDirectRequest changeDocumentDirectRequest);
    @POST(ServiceUrl.CHANGE_DOC_SEND_URL)
    Call<ChangeDocumentRespone> changeSend(@Body ChangeDocumentSendRequest changeDocumentSendRequest);
    @POST(ServiceUrl.CHANGE_DOC_RECEIVE_URL)
    Call<ChangeDocumentRespone> changeReceive(@Body ChangeDocumentReceiveRequest changeDocumentReceiveRequest);
    @POST(ServiceUrl.SEND_DOCUMENT_COME_SAME_URL)
    Call<ChangeDocumentRespone> sendDocumentComeSame(@Body ChangeDocumentReceiveRequest changeDocumentReceiveRequest);

    @GET(ServiceUrl.GET_PERSONS_OR_UNIT_EXPECTED_URL)
    Call<PersonOrUnitExpectedResponse> getPersonOrUnitExpected(@Path("id") int docId);
}
