package com.example.login.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.login.R;
import com.example.login.common.ConnectionDetector;
import com.example.login.configuration.AlertDialogManager;
import com.example.login.configuration.Application;
import com.example.login.configuration.ApplicationSharedPreferences;
import com.example.login.model.APIError;
import com.example.login.model.LoginInfo;
import com.example.login.model.SaveDocument.DocumentSavedRequest;
import com.example.login.presenter.ExceptionErrorPresenter.ExceptionErrorPresenterImpl;
import com.example.login.presenter.ExceptionErrorPresenter.IExceptionErrorPresenter;
import com.example.login.presenter.IExceptionErrorView;
import com.example.login.presenter.ILoginPresenter;
import com.example.login.presenter.LoginPresenterImpl;
import com.example.login.presenter.SaveDocument.ISaveDocumentPresenter;
import com.example.login.presenter.SaveDocument.ISaveDocumentView;
import com.example.login.presenter.SaveDocument.SaveDocumentPresenterImpl;
import com.example.login.presenter.loginView.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaveDocumentActivity extends BaseActivity implements ISaveDocumentView,ILoginView, IExceptionErrorView {
    public static final String TAG = "SaveDocumentActivity";

    String event;
    private String id;
    private int docId;
    private ProgressDialog progressDialog;

    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_save_finish)
    Button btnSaveFinish;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.layout_document)
    LinearLayout viewGround;
    @BindView(R.id.layoutAction)
    LinearLayout layoutAction;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    private Toolbar toolbar;
    private ImageView btnBack;

    private ISaveDocumentPresenter iSaveDocumentPresenter = new SaveDocumentPresenterImpl(this);
    private ILoginPresenter iLoginPresenter  = new LoginPresenterImpl(this);

    private ConnectionDetector connectionDetector;
    private ApplicationSharedPreferences appPrefs;

    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_document);


        ButterKnife.bind(this);
        init();
        getListDocumentSaved();
    }
    @OnClick({R.id.btn_save, R.id.btn_save_finish, R.id.btn_cancel})
    public void finishDocumentSave(View view){

        if (connectionDetector.isConnectingToInternet()) {
            switch (view.getId()) {
                case R.id.btn_save:
//                    saveDocument(0);
                    break;
                case R.id.btn_save_finish:
//                    saveDocument(1);
                    break;
                case R.id.btn_cancel:
                    onBackPressed();
                    break;
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }
//    private void saveDocument(int isFinish) {
//        if (isFinish == 0) {
//            event = "SAVE_EVENT";
//        } else event = "FINISH_EVENT";
//        if (connectionDetector.isConnectingToInternet()) {
//            if (docId != 0) {
//                if ((id = getIdSelectDocument()) != null) {
//                    if (isFinish == 0) {
//                        saveDocumentPresenter.onFinishDocumentSaved(new DocumentSavedRequest(id, docId, isFinish, null));
//                    } else {
//                        if (Application.getApp().getAppPrefs().getAccountLogin().isCommentFinish()) {
//                            DialogComentFinish dialogComentFinish = new DialogComentFinish(this);
//                            dialogComentFinish.show();
//                        } else
//                            saveDocumentPresenter.onFinishDocumentSaved(new DocumentSavedRequest(id, docId, isFinish, null));
//
//                    }
//                } else {
//                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_SAVE), true, AlertDialogManager.INFO);
//                }
//            } else {
//                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.str_ERROR_DIALOG), true, AlertDialogManager.INFO);
//            }
//
//        } else {
//            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
//        }
//    }

    public void getListDocumentSaved() {
        if (connectionDetector.isConnectingToInternet()) {
            iSaveDocumentPresenter.onGetListDocumentSaved();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }
    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarSaveDocument);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void init(){
        appPrefs = Application.getApp().getAppPrefs();
        connectionDetector = new ConnectionDetector(this);
        setupToolbar();
    }

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }

    @Override
    public void onSuccess(Object listDocumentSaveD) {

    }

    @Override
    public void onSuccessPost() {

    }

    @Override
    public void onError(APIError apiError) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {

    }

    @Override
    public void onErrorLogin(APIError apiError) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
