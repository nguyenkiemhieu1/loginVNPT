package com.example.login.presenter.ChangeDocumentDao;

import com.example.login.model.TypeChangeDocRequest;
import com.example.login.presenter.ICallFinishedListener;

public interface IChangeDocumentDao {
    void onSendGetTypeChangeDocumentViewFilesDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener);
    void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener);
}
