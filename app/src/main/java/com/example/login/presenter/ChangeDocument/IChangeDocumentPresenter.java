package com.example.login.presenter.ChangeDocument;


import com.example.login.model.ChangeDocumentDirectRequest;
import com.example.login.model.ChangeDocumentNotifyRequest;
import com.example.login.model.ChangeDocumentReceiveRequest;
import com.example.login.model.ChangeDocumentSendRequest;
import com.example.login.model.ChangeProcessRequest;
import com.example.login.model.TypeChangeDocument.TypeChangeDocRequest;

public interface IChangeDocumentPresenter {
    void getTypeChangeDocument(TypeChangeDocRequest typeChangeDocumentRequest);
    void getTypeChangeDocumentViewFiles(TypeChangeDocRequest typeChangeDocumentRequest);
    void changeProcess(ChangeProcessRequest changeProcessRequest);
    void changeDocNotify(ChangeDocumentNotifyRequest changeDocumentNotifyRequest);
    void changeDirect(ChangeDocumentDirectRequest changeDocumentDirectRequest);
    void changeSend(ChangeDocumentSendRequest changeDocumentSendRequest);
    void changeReceive(ChangeDocumentReceiveRequest changeDocumentReceiveRequest);
    void getPersonOrUnitExpected(int docId);
}
