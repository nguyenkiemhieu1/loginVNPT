package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.R;
import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.model.APIError;
import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.model.DocumentWaiting.DocumentWaitingRespone;
import com.example.login.model.DocumentWaitingInfo;
import com.example.login.model.FinishDocumentRespone;
import com.example.login.model.MarkDocumentRespone;
import com.example.login.presenter.CheckFinishDocumentRespone;
import com.example.login.presenter.DetailDocumentWaiting.DetailDocumentWaitingRespone;
import com.example.login.presenter.DetailDocumentWaiting.IDetailDocumentWaitingView;
import com.example.login.presenter.HandleSyncService;
import com.example.login.presenter.ICallFinishedListener;

import static com.example.login.configuration.Application.resources;

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
    public void checkFinish(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onCheckFinishDocument(id, this);
        }

    }

    @Override
    public void mark(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onMarkDocument(id, this);
        }

    }

    @Override
    public void finish(int id, String comment) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onFinish(id, comment, this);
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
        if (object instanceof DetailDocumentWaitingRespone) {
            detailDocumentWaitingView.hideProgress();
            DetailDocumentWaitingRespone detailDocumentWaitingRespone = (DetailDocumentWaitingRespone) object;
            if (detailDocumentWaitingRespone != null && detailDocumentWaitingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentWaitingView.onSuccess(detailDocumentWaitingRespone.getData());
            } else {
                detailDocumentWaitingView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }
        if (object instanceof MarkDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            MarkDocumentRespone markDocumentRespone = (MarkDocumentRespone) object;
            if (markDocumentRespone != null && markDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (markDocumentRespone.getData() != null && markDocumentRespone.getData().equals(Constants.MARKED_SUCCESS)) {
                    detailDocumentWaitingView.onMark();
                } else {
                    detailDocumentWaitingView.onError(new APIError(0, resources.getString(R.string.str_ERROR_TICK_DOCUMENT_DIALOG)));
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, resources.getString(R.string.str_ERROR_TICK_DOCUMENT_DIALOG)));
            }
        }
        if (object instanceof FinishDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            FinishDocumentRespone finishDocumentRespone = (FinishDocumentRespone) object;
            if (finishDocumentRespone != null && finishDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (finishDocumentRespone.getData() != null) {
                    detailDocumentWaitingView.onFinish();
                } else {
                    detailDocumentWaitingView.onError(new APIError(0, resources.getString(R.string.str_ERROR_END_DOCUMENT_DIALOG)));
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, resources.getString(R.string.str_ERROR_END_DOCUMENT_DIALOG)));
            }
        }

        if (object instanceof CheckFinishDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            CheckFinishDocumentRespone checkFinishDocumentRespone = (CheckFinishDocumentRespone) object;
            if (checkFinishDocumentRespone != null && checkFinishDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (checkFinishDocumentRespone.getData() != null && checkFinishDocumentRespone.getData().equals(Constants.IS_FINISH)) {
                    detailDocumentWaitingView.onCheckFinish(true);
                } else {
                    detailDocumentWaitingView.onCheckFinish(false);
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, resources.getString(R.string.str_ERROR_DIALOG)));
            }
        }

    }

    @Override
    public void onCallError(Object object) {
        if (documentWaitingView != null) {
            documentWaitingView.hideProgress();
            documentWaitingView.onError((APIError) object);
        }
    }

}
