package com.example.login.presenter.loginView;

import com.example.login.model.APIError;
import com.example.login.model.LoginInfo;

public interface ILoginView {
    void onSuccessLogin(LoginInfo loginInfo);
    void onErrorLogin(APIError apiError);
    void showProgress();
    void hideProgress();
}
