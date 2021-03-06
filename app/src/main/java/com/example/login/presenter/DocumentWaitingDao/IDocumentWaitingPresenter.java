package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;

public interface IDocumentWaitingPresenter {
    void getRecords(DocumentWaitingRequest documentWaitingRequest);
    void getDetail(int id);
    void checkFinish(int id);
    void mark(int id);
    void finish(int id,String comment);

}
