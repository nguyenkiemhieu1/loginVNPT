package com.example.login.presenter.ChangeDocument;

import com.example.login.model.APIError;
import com.example.login.model.TypeChangeDocument;

import java.util.List;

public interface IGetTypeChangeDocumentView {
    void onSuccessTypeChange(List<TypeChangeDocument> typeChangeDocumentList);
    void onErrorTypeChange(APIError apiError);
    void showProgress();
    void hideProgress();
}