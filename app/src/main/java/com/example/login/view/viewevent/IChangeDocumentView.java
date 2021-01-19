package com.example.login.view.viewevent;

import com.example.login.model.APIError;
import com.example.login.model.PersonGroupChange.PersonGroupChangeDocInfo;

import java.util.List;

public interface IChangeDocumentView {
    void onSuccessChangeDoc();
    void onSuccessFormList(List<String> listForm);
    void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos);
    void onSuccessUpLoad(List<Object> object);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}