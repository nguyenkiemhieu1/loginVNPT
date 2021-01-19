package com.example.login.view;

import com.example.login.model.APIError;
import com.example.login.model.LoginInfo;
import com.example.login.model.PersonGroupChange.PersonGroupChangeDocInfo;
import com.example.login.model.respone.JobPositionInfo;
import com.example.login.model.respone.PersonSendNotifyInfo;
import com.example.login.model.respone.UnitInfo;
import com.example.login.presenter.ChangeDocumentDao.IChangeDocumentDao;
import com.example.login.presenter.IExceptionErrorView;
import com.example.login.presenter.loginView.ILoginView;
import com.example.login.view.viewevent.IChangeDocumentView;
import com.example.login.view.viewevent.IFilterPersonView;
import com.example.login.view.viewevent.ISelectPersonView;

import java.util.List;

public class SelectPersonActivityNewConvert extends BaseActivity implements IChangeDocumentView, ISelectPersonView, ILoginView, IFilterPersonView , IExceptionErrorView {
    @Override
    public void onSuccessChangeDoc() {

    }

    @Override
    public void onSuccessFormList(List<String> listForm) {

    }

    @Override
    public void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos) {

    }

    @Override
    public void onSuccessUpLoad(List<Object> object) {

    }

    @Override
    public void onSuccess(Object listPerson) {

    }

    @Override
    public void onSuccessJobPossitions(List<JobPositionInfo> jobPositionInfos) {

    }

    @Override
    public void onSuccessUnits(List<UnitInfo> unitInfos) {

    }

    @Override
    public void onError(APIError apiError) {

    }

    @Override
    public void onSuccessLienThong(List<PersonSendNotifyInfo> lienThongInfos) {

    }

    @Override
    public void onSuccessPersonOrUnit(Object object) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {

    }

    @Override
    public void onErrorLogin(APIError apiError) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }
}
