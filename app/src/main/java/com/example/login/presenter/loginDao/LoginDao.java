package com.example.login.presenter.loginDao;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginRespone;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.ILoginService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class LoginDao extends BaseDao implements ILoginDao {
    private ILoginService loginService;

    @Override
    public void onSendLoginDao(LoginRequest loginRequest, ICallFinishedListener iCallFinishedListener) {
        loginService = BaseService.createService(ILoginService.class);
        Call<LoginRespone> call = loginService.getUserLogin(loginRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
