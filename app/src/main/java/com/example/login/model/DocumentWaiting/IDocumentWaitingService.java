package com.example.login.model.DocumentWaiting;

import com.example.login.presenter.DocumentWaitingDao.CountDocumentWaitingRespone;
import com.example.login.presenter.ServiceUrl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IDocumentWaitingService {

    @POST(ServiceUrl.GET_DOC_WAIT_URL)
    Call<DocumentWaitingRespone> getAll(@Body DocumentWaitingRequest documentWaitingRequest);
}
