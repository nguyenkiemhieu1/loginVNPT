package com.example.login.presenter.DetailDocumentWaiting;

import com.example.login.model.APIError;

public interface IDetailDocumentWaitingView {
    void onSuccess(Object object);
    void onComment();
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
    void onMark();
    void onFinish();
    void onCheckFinish(boolean isFinish);
}