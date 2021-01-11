package com.example.login.presenter.SaveDocument;

import com.example.login.model.SaveDocument.DocumentSavedRequest;

public interface ISaveDocumentPresenter  {
    public void onGetListDocumentSaved();
    public void onFinishDocumentSaved(DocumentSavedRequest documentSavedRequest);

}
