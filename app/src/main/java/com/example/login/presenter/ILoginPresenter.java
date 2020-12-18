package com.example.login.presenter;

import com.example.login.model.LoginRequest;

public interface ILoginPresenter {
    void getUserLoginPresenter(LoginRequest loginRequest);

    void loginPresenter(LoginRequest loginRequest);
}
