package com.example.login.presenter;

import com.example.login.model.SaveDocument.DocumentSavedRequest;
import com.example.login.model.SaveDocument.FinishDocumentSaveRespone;
import com.example.login.model.SaveDocument.GetListSaveDocumentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IDocumentSavedService {
    @GET(ServiceUrl.GET_DOCUMENT_SAVED)
    Call<GetListSaveDocumentResponse> getListDocumentSaved();
    @POST(ServiceUrl.POST_DOCUMENT_SAVED)
    Call<FinishDocumentSaveRespone> postFinishDocumentSaved(@Body DocumentSavedRequest documentSavedRequest);
}
