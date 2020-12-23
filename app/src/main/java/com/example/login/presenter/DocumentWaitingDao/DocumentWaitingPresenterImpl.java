package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.model.APIError;
import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.model.DocumentWaiting.DocumentWaitingRespone;
import com.example.login.model.DocumentWaitingInfo;
import com.example.login.presenter.DetailDocumentWaiting.IDetailDocumentWaitingView;
import com.example.login.presenter.HandleSyncService;
import com.example.login.presenter.ICallFinishedListener;

public class DocumentWaitingPresenterImpl implements IDocumentWaitingPresenter, ICallFinishedListener, HandleSyncService.HandleGetRecords {
    public DocumentWaitingDao documentWaitingDao;
    public IDocumentWaitingView documentWaitingView;
    public IDetailDocumentWaitingView detailDocumentWaitingView;

    public DocumentWaitingPresenterImpl(IDocumentWaitingView documentWaitingView) {
        this.documentWaitingView = documentWaitingView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }
    public DocumentWaitingPresenterImpl(IDetailDocumentWaitingView detailDocumentWaitingView) {
        this.detailDocumentWaitingView = detailDocumentWaitingView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }

    @Override
    public void getRecords(DocumentWaitingRequest documentWaitingRequest) {if (documentWaitingView != null) {
        documentWaitingView.showProgress();
        documentWaitingDao.onRecordsDocumentWaitingDao(documentWaitingRequest, this);
    }

    }

    @Override
    public void getDetail(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onGetDetail(id, this);
        }

    }



    @Override
    public void onSuccessGetRecords(Object object) {
        documentWaitingView.hideProgress();
        DocumentWaitingRespone documentWaitingRespone = (DocumentWaitingRespone) object;
        if (documentWaitingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            documentWaitingView.onSuccessRecords(ConvertUtils.fromJSONList(documentWaitingRespone.getData(), DocumentWaitingInfo.class));
        } else {
            documentWaitingView.onError(new APIError(0, ""));
        }

    }

    @Override
    public void onErrorGetRecords(Object object) {
        if (documentWaitingView != null) {
            documentWaitingView.hideProgress();
            documentWaitingView.onError((APIError) object);
        }

    }

    @Override
    public void onCallSuccess(Object object) {

    }

    @Override
    public void onCallError(Object object) {
        if (documentWaitingView != null) {
            documentWaitingView.hideProgress();
            documentWaitingView.onError((APIError) object);
        }
    }
}
