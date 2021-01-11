package com.example.login.presenter.ExceptionErrorPresenter;

import com.example.login.R;
import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.model.APIError;
import com.example.login.model.ChiDaoRespone;
import com.example.login.presenter.ExceptionErrorDao.ExceptionErrorDao;
import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.IExceptionErrorView;

import static com.example.login.configuration.Application.resources;

public class ExceptionErrorPresenterImpl  implements  IExceptionErrorPresenter, ICallFinishedListener {
    public IExceptionErrorView exceptionErrorView;
    public ExceptionErrorDao exceptionErrorDao;

    public ExceptionErrorPresenterImpl(IExceptionErrorView iExceptionErrorView){
        this.exceptionErrorView   = iExceptionErrorView;
        exceptionErrorDao = new ExceptionErrorDao();

    }

    @Override
    public void sendExceptionError(ExceptionRequest exceptionRequest) {
        if (exceptionErrorView != null) {
            exceptionErrorDao.onSendExceptionErrorDao(exceptionRequest, this);
        }


    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ChiDaoRespone) {
            ChiDaoRespone chiDaoRespone = (ChiDaoRespone) object;
            if (chiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                exceptionErrorView.onSuccessException(ConvertUtils.fromJSONList(chiDaoRespone.getData(), Object.class));
            } else {
                exceptionErrorView.onExceptionError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }

    }

    @Override
    public void onCallError(Object object) {
        exceptionErrorView.onExceptionError((APIError) object);

    }
}
