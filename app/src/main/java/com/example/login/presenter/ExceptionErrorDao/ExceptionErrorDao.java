package com.example.login.presenter.ExceptionErrorDao;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.ExceptionResponse;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.IExceptionErrorView;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class ExceptionErrorDao extends BaseDao implements IExceptionErrorDao {
    private IExceptionErrorService exceptionErrorService;
    @Override
    public void onSendExceptionErrorDao(ExceptionRequest exceptionRequest, ICallFinishedListener iCallFinishedListener) {

        exceptionErrorService = BaseService.createServiceException(IExceptionErrorService.class);
        Call<ExceptionResponse> call = exceptionErrorService.exceptionError(BaseService.createAuthenHeadersException(),exceptionRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
