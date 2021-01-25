package com.example.login.presenter.DocumentWaitingDao;


import com.example.login.model.APIError;
import com.example.login.model.DocumentWaiting.DocumentWaitingInfo;

import java.util.List;

public interface IDocumentWaitingView   {
    void onSuccessRecords(List<DocumentWaitingInfo> documentWaitingInfoList);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();

}
