package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.presenter.HandleSyncService;

public interface IDocumentWaitingDao {

    void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords);
}
