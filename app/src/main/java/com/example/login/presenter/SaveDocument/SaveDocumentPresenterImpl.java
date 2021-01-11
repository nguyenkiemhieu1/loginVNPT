package com.example.login.presenter.SaveDocument;

import com.example.login.model.APIError;
import com.example.login.model.SaveDocument.DocumentSavedRequest;
import com.example.login.model.SaveDocument.FinishDocumentSaveRespone;
import com.example.login.model.SaveDocument.GetListSaveDocumentResponse;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.SaveDocumentDao.SaveDocumentDao;

public class SaveDocumentPresenterImpl implements ISaveDocumentPresenter, ICallFinishedListener {
    SaveDocumentDao saveDocumentDao;
    ISaveDocumentView iSaveDocumentView;
    public SaveDocumentPresenterImpl(ISaveDocumentView iSaveDocumentView){
        this.iSaveDocumentView = iSaveDocumentView;
        saveDocumentDao = new SaveDocumentDao();
    }
    @Override
    public void onGetListDocumentSaved() {
        if(iSaveDocumentView != null){
            iSaveDocumentView.hideProgress();
            saveDocumentDao.onGetSaveDocumentDao(this);
        }

    }

    @Override
    public void onFinishDocumentSaved(DocumentSavedRequest documentSavedRequest) {
        if (iSaveDocumentView != null) {
            iSaveDocumentView.showProgress();
            saveDocumentDao.onPostSaveDocumentDao(documentSavedRequest, this);
        }

    }
    @Override
    public void onCallSuccess(Object object) {
        if (iSaveDocumentView!=null) {
            if (object instanceof GetListSaveDocumentResponse) {
                GetListSaveDocumentResponse list = (GetListSaveDocumentResponse) object;
                if (list != null) {
                    iSaveDocumentView.onSuccess(list);
                } else {
                    iSaveDocumentView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
                }
            }
            if (object instanceof FinishDocumentSaveRespone) {
                FinishDocumentSaveRespone finishDocumentSaveRespone=(FinishDocumentSaveRespone) object;
                iSaveDocumentView.hideProgress();
                iSaveDocumentView.onSuccessPost();
            }
        }

    }

    @Override
    public void onCallError(Object object) {
        if(object instanceof APIError) {
            iSaveDocumentView.onError((APIError) object);
        }
    }



}
