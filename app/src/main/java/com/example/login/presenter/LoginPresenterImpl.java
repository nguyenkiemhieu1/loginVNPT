package com.example.login.presenter;

import com.example.login.R;
import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.model.APIError;
import com.example.login.model.LoginInfo;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginRespone;
import com.example.login.presenter.loginDao.LoginDao;
import com.example.login.presenter.loginView.ILoginView;

import  static com.example.login.configuration.Application.resources;

public class LoginPresenterImpl implements ILoginPresenter, ICallFinishedListener {
    public ILoginView loginView;
    public LoginDao loginDao;

    public LoginPresenterImpl(ILoginView loginView) {
        this.loginView = loginView;
        this.loginDao = new LoginDao();
    }

    @Override
    public void getUserLoginPresenter(LoginRequest loginRequest) {
        if (loginView != null) {
            loginDao.onSendLoginDao(loginRequest, this);
        }
    }

    @Override
    public void loginPresenter(LoginRequest loginRequest) {
        if (loginView != null) {
            loginView.showProgress();
            loginDao.onSendLoginDao(loginRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (loginView != null) {
            loginView.hideProgress();
        }
        LoginRespone loginRespone = (LoginRespone) object;
        if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
            loginView.onSuccessLogin(loginInfo);
        } else {
            loginView.onErrorLogin(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
        }
    }

    @Override
    public void onCallError(Object object) {
        if(loginView!=null){
            loginView.onErrorLogin((APIError) object);
            loginView.hideProgress();
        }

    }

}
