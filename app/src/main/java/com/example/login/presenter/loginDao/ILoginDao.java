package com.example.login.presenter.loginDao;

import com.example.login.model.Login.LoginRequest;
import com.example.login.presenter.ICallFinishedListener;

public interface ILoginDao {
    default void onSendLoginDao(LoginRequest loginRequest, ICallFinishedListener iCallFinishedListener) {
    }
}
