package com.example.login.presenter.ChangeDocument;

import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.model.APIError;

import com.example.login.model.ChangeDocumentDirectRequest;
import com.example.login.model.ChangeDocumentNotifyRequest;
import com.example.login.model.ChangeDocumentReceiveRequest;
import com.example.login.model.ChangeDocumentSendRequest;
import com.example.login.model.ChangeProcessRequest;
import com.example.login.model.TypeChangeDocument.TypeChangeDocRequest;
import com.example.login.model.TypeChangeDocument.TypeChangeDocument;
import com.example.login.presenter.ChangeDocumentDao.ChangeDocumentDao;
import com.example.login.presenter.ICallFinishedListener;
import com.example.login.presenter.TypeChangeDocumentRespone;
import com.example.login.view.viewevent.IChangeDocumentView;
import com.example.login.view.viewevent.IFilterPersonView;
import com.example.login.view.viewevent.ISelectPersonView;

public class ChangeDocumentPresenterImpl implements IChangeDocumentPresenter, ICallFinishedListener {
  public IGetTypeChangeDocumentView iGetTypeChangeDocumentView;
    public ChangeDocumentDao changeDocumentDao;
    public ISelectPersonView selectPersonView;
    public IFilterPersonView filterPersonView;
    public IChangeDocumentView changeDocumentView;


    public ChangeDocumentPresenterImpl(ISelectPersonView selectPersonView, IFilterPersonView filterPersonView, IChangeDocumentView iChangeDocumentView) {
        this.selectPersonView = selectPersonView;
        this.filterPersonView = filterPersonView;
        this.changeDocumentDao = new ChangeDocumentDao();
        this.changeDocumentView = iChangeDocumentView;
    }

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
    public void changeProcess(ChangeProcessRequest changeProcessRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeProcessDao(changeProcessRequest, this);
        }

    }

    @Override
    public void changeDocNotify(ChangeDocumentNotifyRequest changeDocumentNotifyRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeDocNotifyDao(changeDocumentNotifyRequest, this);
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
    @Override
    public void changeDirect(ChangeDocumentDirectRequest changeDocumentDirectRequest) {

        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeDirectDao(changeDocumentDirectRequest, this);
        }
    }

    @Override
    public void changeSend(ChangeDocumentSendRequest changeDocumentSendRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeSendDao(changeDocumentSendRequest, this);
        }
    }

    @Override
    public void changeReceive(ChangeDocumentReceiveRequest changeDocumentReceiveRequest) {

        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeReceiveDao(changeDocumentReceiveRequest, this);
        }
    }

    @Override
    public void getPersonOrUnitExpected(int docId) {
        if (selectPersonView != null) {
            selectPersonView.showProgress();
            changeDocumentDao.onGetPersonOrUnitExpectedDao(docId,this);
        }

    }

}
