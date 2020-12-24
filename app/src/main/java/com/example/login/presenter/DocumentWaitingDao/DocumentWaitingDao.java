package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.model.DocumentWaiting.DocumentWaitingRespone;
import com.example.login.model.DocumentWaiting.IDocumentWaitingService;
import com.example.login.presenter.CheckFinishDocumentRespone;
import com.example.login.presenter.DetailDocumentWaiting.DetailDocumentWaitingRespone;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.HandleSyncService;
import com.example.login.presenter.ICallFinishedListener;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class DocumentWaitingDao extends BaseDao implements  IDocumentWaitingDao {
    private IDocumentWaitingService documentWaitingService;

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DetailDocumentWaitingRespone> call = documentWaitingService.getDetail(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }

    @Override
    public void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords) {

        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DocumentWaitingRespone> call = documentWaitingService.getAll(documentWaitingRequest);
        call(call, handleGetRecords);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    public void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckFinishDocumentRespone> call = documentWaitingService.checkFinish(docId);
        call(call, callFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

}
