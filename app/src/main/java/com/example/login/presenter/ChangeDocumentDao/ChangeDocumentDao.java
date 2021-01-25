package com.example.login.presenter.ChangeDocumentDao;

import com.example.login.model.BaseDao;
import com.example.login.model.BaseService;
import com.example.login.model.ChangeDocumentDirectRequest;
import com.example.login.model.ChangeDocumentNotifyRequest;
import com.example.login.model.ChangeDocumentReceiveRequest;
import com.example.login.model.ChangeDocumentSendRequest;
import com.example.login.model.ChangeProcessRequest;
import com.example.login.model.PersonOrUnitExpectedResponse;
import com.example.login.model.TypeChangeDocument.TypeChangeDocRequest;
import com.example.login.presenter.ChangeDocument.ChangeDocumentRespone;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.TypeChangeDocumentRespone;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;

public class ChangeDocumentDao extends BaseDao implements IChangeDocumentDao {
    private IChangeDocumentService changeDocumentService;

    @Override
    public void onSendGetTypeChangeDocumentViewFilesDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<TypeChangeDocumentRespone> call = changeDocumentService.getTypeChangeViewFiles(typeChangeDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener) {

        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<TypeChangeDocumentRespone> call = changeDocumentService.getTypeChange(typeChangeDocumentRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
    @Override
    public void onChangeProcessDao(ChangeProcessRequest changeProcessRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call;
        if (changeProcessRequest.getDocId().contains("[")) {
            changeProcessRequest.setDocId(convertStringListToString(changeProcessRequest.getDocId()));
            call = changeDocumentService.sendProcessDocumentSame(changeProcessRequest);
        } else {
            call = changeDocumentService.changeProcess(changeProcessRequest);
        }
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
    private String convertStringListToString(String stringList) {
        String replace = stringList.replace("[", "");
        String replace1 = replace.replace("]", "");

        return replace1;
    }
    @Override
    public void onChangeDocNotifyDao(ChangeDocumentNotifyRequest changeDocumentNotifyRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeNotify(changeDocumentNotifyRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeSendDao(ChangeDocumentSendRequest changeDocumentSendRequest, ICallFinishedListener iCallFinishedListener) {

        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeSend(changeDocumentSendRequest);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeDirectDao(ChangeDocumentDirectRequest changeDocumentDirectRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = null;
        if (changeDocumentDirectRequest.getDocId().contains("[")) {
            changeDocumentDirectRequest.setDocId(convertStringListToString(changeDocumentDirectRequest.getDocId()));
            call = changeDocumentService.issuingDocumentComeSame(changeDocumentDirectRequest);
        } else {
            call = changeDocumentService.changeDirect(changeDocumentDirectRequest);
        }

        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }

    @Override
    public void onChangeReceiveDao(ChangeDocumentReceiveRequest changeDocumentReceiveRequest, ICallFinishedListener iCallFinishedListener) {

        changeDocumentService = BaseService.createService(IChangeDocumentService.class);

        Call<ChangeDocumentRespone> call;
        if (changeDocumentReceiveRequest.getDocId().contains("[")) {
            changeDocumentReceiveRequest.setDocId(convertStringListToString(changeDocumentReceiveRequest.getDocId()));
            call = changeDocumentService.sendDocumentComeSame(changeDocumentReceiveRequest);
        } else {
            call = changeDocumentService.changeReceive(changeDocumentReceiveRequest);
        }
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
    @Override
    public void onGetPersonOrUnitExpectedDao(int docId, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonOrUnitExpectedResponse> call = changeDocumentService.getPersonOrUnitExpected(docId);
        call(call, iCallFinishedListener);
        EventBus.getDefault().postSticky(new ExceptionCallAPIEvent(String.valueOf(call.request().url())));
    }
}
