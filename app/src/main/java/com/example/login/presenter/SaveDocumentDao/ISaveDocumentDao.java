package com.example.login.presenter.SaveDocumentDao;

import com.example.login.model.SaveDocument.DocumentSavedRequest;
import com.example.login.presenter.ICallFinishedListener;

public interface ISaveDocumentDao {
    void onGetSaveDocumentDao(ICallFinishedListener iCallFinishedListener);
    void onPostSaveDocumentDao(DocumentSavedRequest documentSavedRequest, ICallFinishedListener iCallFinishedListener);
}
