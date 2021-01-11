package com.example.login.presenter.SaveDocument;

import com.example.login.model.APIError;

public interface ISaveDocumentView {
    void onSuccess(Object listDocumentSaveD);
    void onSuccessPost();

    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}