package com.example.login.presenter.ChangeDocumentDao;

import com.example.login.model.ChangeDocumentDirectRequest;
import com.example.login.model.ChangeDocumentNotifyRequest;
import com.example.login.model.ChangeDocumentReceiveRequest;
import com.example.login.model.ChangeDocumentSendRequest;
import com.example.login.model.ChangeProcessRequest;
import com.example.login.model.TypeChangeDocument.TypeChangeDocRequest;
import com.example.login.presenter.ICallFinishedListener;

public interface IChangeDocumentDao {
    void onGetPersonOrUnitExpectedDao(int docId, ICallFinishedListener iCallFinishedListener);
    void onChangeProcessDao(ChangeProcessRequest changeProcessRequest, ICallFinishedListener iCallFinishedListener);
    void onSendGetTypeChangeDocumentViewFilesDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener);
    void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener);
    void onChangeDocNotifyDao(ChangeDocumentNotifyRequest changeDocumentNotifyRequest, ICallFinishedListener iCallFinishedListener);
    void onChangeSendDao(ChangeDocumentSendRequest changeDocumentSendRequest, ICallFinishedListener iCallFinishedListener);
    void onChangeDirectDao(ChangeDocumentDirectRequest changeDocumentDirectRequest, ICallFinishedListener iCallFinishedListener);
    void onChangeReceiveDao(ChangeDocumentReceiveRequest changeDocumentReceiveRequest, ICallFinishedListener iCallFinishedListener);
}
