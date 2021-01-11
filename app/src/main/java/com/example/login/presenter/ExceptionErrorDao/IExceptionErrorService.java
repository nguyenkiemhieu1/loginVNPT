package com.example.login.presenter.ExceptionErrorDao;

import com.example.login.model.ExceptionResponse;
import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ServiceUrl;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface IExceptionErrorService {
    @POST(ServiceUrl.EXCEPTION_URL)
    Call<ExceptionResponse> exceptionError(@HeaderMap Map<String, String> headers, @Body ExceptionRequest exceptionRequest);
}