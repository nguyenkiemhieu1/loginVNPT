package com.example.login.view.viewevent;

import com.example.login.model.APIError;
import com.example.login.model.respone.PersonSendNotifyInfo;

import java.util.List;

public interface ISelectPersonView {
    void onSuccess(Object listPerson);
    void onError(APIError apiError);
    void onSuccessLienThong(List<PersonSendNotifyInfo> lienThongInfos);
    void onSuccessPersonOrUnit(Object object);
    void showProgress();
    void hideProgress();
}
