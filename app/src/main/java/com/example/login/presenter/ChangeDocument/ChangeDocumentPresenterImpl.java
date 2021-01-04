package com.example.login.presenter.ChangeDocument;

import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.model.APIError;
import com.example.login.model.TypeChangeDocRequest;
import com.example.login.model.TypeChangeDocument;
import com.example.login.presenter.ChangeDocumentDao.ChangeDocumentDao;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.TypeChangeDocumentRespone;

public class ChangeDocumentPresenterImpl implements IChangeDocumentPresenter, ICallFinishedListener {
  public IGetTypeChangeDocumentView iGetTypeChangeDocumentView;
    public ChangeDocumentDao changeDocumentDao;




    public ChangeDocumentPresenterImpl(IGetTypeChangeDocumentView getTypeChangeDocumentView) {
        this.iGetTypeChangeDocumentView =  getTypeChangeDocumentView;
        this.changeDocumentDao = new ChangeDocumentDao();
    }


    @Override
    public void getTypeChangeDocument(TypeChangeDocRequest typeChangeDocumentRequest) {
        if (iGetTypeChangeDocumentView != null) {
            iGetTypeChangeDocumentView.showProgress();
            changeDocumentDao.onSendGetTypeChangeDocumentDao(typeChangeDocumentRequest, this);
        }

    }

    @Override
    public void getTypeChangeDocumentViewFiles(TypeChangeDocRequest typeChangeDocumentRequest) {

        if (iGetTypeChangeDocumentView != null) {
            iGetTypeChangeDocumentView.showProgress();
            changeDocumentDao.onSendGetTypeChangeDocumentViewFilesDao(typeChangeDocumentRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof TypeChangeDocumentRespone) {
            iGetTypeChangeDocumentView.hideProgress();
            TypeChangeDocumentRespone typeChangeDocumentRespone = (TypeChangeDocumentRespone) object;
            if (typeChangeDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                iGetTypeChangeDocumentView.onSuccessTypeChange(ConvertUtils.fromJSONList(typeChangeDocumentRespone.getData(), TypeChangeDocument.class));
            } else {
                iGetTypeChangeDocumentView.onErrorTypeChange(new APIError(0, "getTypeChange"));
            }
        }

    }

    @Override
    public void onCallError(Object object) {
        if (iGetTypeChangeDocumentView != null) {
            iGetTypeChangeDocumentView.hideProgress();
            iGetTypeChangeDocumentView.onErrorTypeChange(new APIError(0, "getTypeChange"));
        }

    }
}
