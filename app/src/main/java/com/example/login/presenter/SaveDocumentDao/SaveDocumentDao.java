package com.example.login.presenter.SaveDocumentDao;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.SaveDocument.DocumentSavedRequest;
import com.example.login.model.SaveDocument.FinishDocumentSaveRespone;
import com.example.login.model.SaveDocument.GetListSaveDocumentResponse;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.IDocumentSavedService;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class SaveDocumentDao extends BaseDao implements ISaveDocumentDao {
    IDocumentSavedService iDocumentSavedService;
    @Override
    public void onGetSaveDocumentDao(ICallFinishedListener iCallFinishedListener) {
        iDocumentSavedService = BaseService.createService(IDocumentSavedService.class);
        Call<GetListSaveDocumentResponse> call = iDocumentSavedService.getListDocumentSaved();
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));

    }

    @Override
    public void onPostSaveDocumentDao(DocumentSavedRequest documentSavedRequest, ICallFinishedListener iCallFinishedListener) {

        iDocumentSavedService = BaseService.createService(IDocumentSavedService.class);
        Call<FinishDocumentSaveRespone> call = iDocumentSavedService.postFinishDocumentSaved(documentSavedRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
