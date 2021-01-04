package com.example.login.model.DocumentWaiting;

import com.example.login.model.CommentRequest;
import com.example.login.model.FinishDocumentRespone;
import com.example.login.model.MarkDocumentRespone;
import com.example.login.presenter.CheckFinishDocumentRespone;
import com.example.login.presenter.DetailDocumentWaiting.DetailDocumentWaitingRespone;
import com.example.login.presenter.DocumentWaitingDao.CountDocumentWaitingRespone;
import com.example.login.presenter.ServiceUrl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IDocumentWaitingService {

    @POST(ServiceUrl.GET_DOC_WAIT_URL)
    Call<DocumentWaitingRespone> getAll(@Body DocumentWaitingRequest documentWaitingRequest);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_WAITING_URL)
    Call<DetailDocumentWaitingRespone> getDetail(@Path("id") int docId);
    @GET(ServiceUrl.CHECK_FINISH_DOC_URL)
    Call<CheckFinishDocumentRespone> checkFinish(@Path("id") int docId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@Path("id") int docId);

    @POST(ServiceUrl.FINISH_DOC_URL_V2)
    Call<FinishDocumentRespone> finish(@Body CommentRequest commentRequest);
}
