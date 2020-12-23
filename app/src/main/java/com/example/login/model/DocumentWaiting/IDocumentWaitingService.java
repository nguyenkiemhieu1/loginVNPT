package com.example.login.model.DocumentWaiting;

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
}
