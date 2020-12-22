package com.example.login.presenter;

import com.example.login.model.APIError;

public interface IExceptionErrorView {
    void onSuccessException(Object object);
    void onExceptionError(APIError apiError);
}