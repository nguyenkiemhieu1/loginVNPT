package com.example.login.presenter.ChangeDocumentDao;

import com.example.login.model.TypeChangeDocRequest;
import com.example.login.presenter.ServiceUrl;
import com.example.login.presenter.TypeChangeDocumentRespone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IChangeDocumentService {
    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_URL)
    Call<TypeChangeDocumentRespone> getTypeChange(@Body TypeChangeDocRequest typeChangeDocumentRequest);
    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_VIEW_FILES_URL)
    Call<TypeChangeDocumentRespone> getTypeChangeViewFiles(@Body TypeChangeDocRequest typeChangeDocumentRequest);
}
