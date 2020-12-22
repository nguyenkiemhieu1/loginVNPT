package com.example.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.example.login.adapter.DocumentWaitingAdapter;
import com.example.login.common.ConnectionDetector;
import com.example.login.common.Constants;
import com.example.login.configuration.AlertDialogManager;
import com.example.login.configuration.Application;
import com.example.login.configuration.ApplicationSharedPreferences;
import com.example.login.model.APIError;
import com.example.login.model.DocumentWaiting.DocumentWaitingRequest;
import com.example.login.model.DocumentWaitingInfo;
import com.example.login.model.LoginInfo;
import com.example.login.presenter.DocumentWaitingDao.DocumentWaitingPresenterImpl;
import com.example.login.presenter.DocumentWaitingDao.IDocumentWaitingPresenter;
import com.example.login.presenter.DocumentWaitingDao.IDocumentWaitingView;
import com.example.login.presenter.ILoginPresenter;
import com.example.login.presenter.InitEvent;
import com.example.login.presenter.KhoVanBanEvent;
import com.example.login.presenter.LoginPresenterImpl;
import com.example.login.presenter.loginView.ILoginView;

import org.greenrobot.eventbus.EventBus;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentWaitingActivity extends AppCompatActivity implements IDocumentWaitingView, ILoginView {
    @BindView(R.id.listView)
    private ListView listView;
    private int page = 1;
    private int totalPage;
    private String keyword = "";
    private String mParam1;
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private DocumentWaitingAdapter documentWaitingAdapter;
    private LoginInfo loginInfo;
    private Timer timer;

    private ApplicationSharedPreferences appPrefs;
    private List<DocumentWaitingInfo> documentWaitingInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_waiting_layout);
        init();

        EventBus.getDefault().register(this);
    }

    private void init() {
        timer = new Timer();

        appPrefs = Application.getApp().getAppPrefs();
        loginInfo = appPrefs.getAccountLogin();
        connectionDetector = new ConnectionDetector(getApplicationContext());
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            EventBus.getDefault().postSticky(new KhoVanBanEvent(mParam1));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page)
                    , String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));

        } else {
            AlertDialogManager.showAlertDialog(getApplicationContext(), getApplicationContext().getString(R.string.NETWORK_TITLE_ERROR), getApplicationContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }


    }
    private void prepareNewdata(){
        documentWaitingAdapter = new DocumentWaitingAdapter(getApplicationContext(), documentWaitingInfoList);
        documentWaitingAdapter.setLoadMoreListener(new DocumentWaitingAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (documentWaitingInfoList.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }

                    }
                });
            }
        });
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getApplicationContext(), getApplicationContext().getString(R.string.NETWORK_TITLE_ERROR), getApplicationContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }
    private void selectAllListOrType() {
        if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
            for (int i = 0; i < documentWaitingInfoList.size(); i++) {
                documentWaitingInfoList.get(i).setSelect(true);
//                documentWaitingAdapter.notifyItemChanged(i);
                documentWaitingAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onSuccessRecords(List<DocumentWaitingInfo> documentWaitingInfoList) {

    }

    @Override   
    public void onError(APIError apiError) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {

        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
//            checkButton(loginInfo);
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page)
                    , String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword, mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getApplicationContext(), getApplicationContext().getString(R.string.NETWORK_TITLE_ERROR), getApplicationContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
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
