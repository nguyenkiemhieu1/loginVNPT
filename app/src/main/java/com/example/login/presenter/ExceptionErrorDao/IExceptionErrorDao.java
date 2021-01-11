package com.example.login.presenter.ExceptionErrorDao;

import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ICallFinishedListener;

public interface IExceptionErrorDao {
    void onSendExceptionErrorDao(ExceptionRequest exceptionRequest, ICallFinishedListener iCallFinishedListener);
}