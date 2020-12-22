package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.model.DocumentWaiting.DocumentWaitingRespone;
import com.example.login.model.DocumentWaiting.IDocumentWaitingService;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.HandleSyncService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class DocumentWaitingDao extends BaseDao implements  IDocumentWaitingDao {
    private IDocumentWaitingService documentWaitingService;
    @Override
    public void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords) {

        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DocumentWaitingRespone> call = documentWaitingService.getAll(documentWaitingRequest);
        call(call, handleGetRecords);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
