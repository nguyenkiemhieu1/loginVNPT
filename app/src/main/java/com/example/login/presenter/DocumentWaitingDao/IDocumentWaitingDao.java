package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.presenter.HandleSyncService;
import com.example.login.presenter.ICallFinishedListener;

public interface IDocumentWaitingDao {
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);

    void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords);
}
