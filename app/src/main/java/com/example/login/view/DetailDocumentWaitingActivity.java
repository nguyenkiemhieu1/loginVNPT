package com.example.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.common.ConnectionDetector;
import com.example.login.common.Constants;
import com.example.login.common.DialogCommentFinish;
import com.example.login.common.ReloadDocWaitingtEvent;
import com.example.login.configuration.AlertDialogManager;
import com.example.login.configuration.Application;
import com.example.login.configuration.ApplicationSharedPreferences;
import com.example.login.model.APIError;
import com.example.login.model.DetailDocumentWaiting.DetailDocumentInfo;
import com.example.login.model.DetailDocumentWaiting.DetailDocumentWaiting;
import com.example.login.model.DocumentWaitingInfo;
import com.example.login.model.FeedBackEvent;
import com.example.login.model.FinishEvent;
import com.example.login.model.ListPersonPreEvent;
import com.example.login.model.LoginInfo;
import com.example.login.model.SaveDocument.SaveDocumentEvent;
import com.example.login.model.SaveFinishDocumentEvent;
import com.example.login.model.TypeChangeDocRequest;
import com.example.login.model.TypeChangeDocument;
import com.example.login.model.TypeChangeDocumentRequest;
import com.example.login.presenter.ChangeDocument.ChangeDocumentPresenterImpl;
import com.example.login.presenter.ChangeDocument.IChangeDocumentPresenter;
import com.example.login.presenter.ChangeDocument.IGetTypeChangeDocumentView;
import com.example.login.presenter.DetailDocumentWaiting.IDetailDocumentWaitingView;
import com.example.login.presenter.DocumentWaitingDao.DocumentWaitingPresenterImpl;
import com.example.login.presenter.DocumentWaitingDao.IDocumentWaitingPresenter;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ILoginPresenter;
import com.example.login.presenter.ItemClickGetTypeChangeButton;
import com.example.login.presenter.LoginPresenterImpl;
import com.example.login.presenter.loginView.ILoginView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailDocumentWaitingActivity extends BaseActivity implements ILoginView, IDetailDocumentWaitingView, IGetTypeChangeDocumentView, ItemClickGetTypeChangeButton {
    @BindView(R.id.tv_trichyeu)
    TextView tv_trichyeu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ngayden)
    TextView tv_ngayden;
    @BindView(R.id.tv_ngayden_label)
    TextView tv_ngayden_label;
    @BindView(R.id.tv_kh)
    TextView tvKH;
    @BindView(R.id.tv_so_ki_hieu_label)
    TextView tvKH_label;
    @BindView(R.id.tv_cqbh)
    TextView tvCQBH;
    @BindView(R.id.tv_cqbh_label)
    TextView tvCQBH_label;
    @BindView(R.id.tv_ngayvb)
    TextView tvNgayVB;
    @BindView(R.id.tv_ngayvb_label)
    TextView tvNgayVB_label;
    @BindView(R.id.tv_hinhthucvb)
    TextView tv_hinhthucvb;
    @BindView(R.id.tv_hinhthucvb_label)
    TextView tv_hinhthucvb_label;
    @BindView(R.id.tv_sovb)
    TextView tv_sovb;
    @BindView(R.id.tv_sovb_label)
    TextView tv_sovb_label;
    @BindView(R.id.tv_soden)
    TextView tv_soden;
    @BindView(R.id.tv_soden_label)
    TextView tv_soden_label;
    @BindView(R.id.tv_do_khan)
    TextView tvDoKhan;
    @BindView(R.id.tv_do_khan_label)
    TextView tvDoKhan_label;
    @BindView(R.id.tv_hanxuly)
    TextView tv_hanxuly;
    @BindView(R.id.tv_hanxuly_label)
    TextView tv_hanxuly_label;
    @BindView(R.id.tv_sotrang)
    TextView tv_sotrang;
    @BindView(R.id.tv_sotrang_label)
    TextView tv_sotrang_label;
    @BindView(R.id.tv_hinhthucgui)
    TextView tv_hinhthucgui;
    @BindView(R.id.tv_hinhthucgui_label)
    TextView tv_hinhthucgui_label;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tv_filedinhkem_label;
    @BindView(R.id.tv_vblienquan)
    TextView tv_vblienquan;
    @BindView(R.id.layout_file_attach)
    RecyclerView layout_file_attach;
    @BindView(R.id.layout_file_related)
    LinearLayout layout_file_related;
    @BindView(R.id.layout_related_docs)
    LinearLayout layout_related_docs;
    @BindView(R.id.layout_log)
    LinearLayout layout_log;
    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.layoutAction)
    LinearLayout layoutAction;
    @BindView(R.id.layoutVanBan)
    LinearLayout layoutVanBan;
    @BindView(R.id.btnMove)
    Button btnMove;
    @BindView(R.id.btnMark)
    Button btnMark;
    @BindView(R.id.btn_send_comment)
    Button btn_send_comment;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.btnHistory)
    Button btnHistory;
    @BindView(R.id.btnChuyenXuLy)
    Button btnChuyenXuLy;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;

    @BindView(R.id.btnSave)
    Button btnSave;
    private DocumentWaitingInfo newItem = null;
    private Button buttonAnchor;

    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSend;
    private boolean marked;
    private int idDoc;
    private ConnectionDetector connectionDetector;

    DetailDocumentWaiting detailDocumentWaiting;


    private ApplicationSharedPreferences appPrefs;
    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    DetailDocumentInfo detailDocumentInfo;
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this);


    //    tìm hiểu về move, mark, history, finish
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_document_waiting_layout);
        ButterKnife.bind(this);
        init();
        getDetail();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
        connectionDetector = new ConnectionDetector(this);

        if (!appPrefs.getAccountLogin().isBriefDisplay()) {
            btnSave.setVisibility(View.GONE);
        }
        try {
            newItem = EventBus.getDefault().getStickyEvent(DocumentWaitingInfo.class);
            if (newItem != null) {
                layoutAction.setVisibility(View.VISIBLE);
                //ẩn hiện nút chuyển
                if (newItem.getIsChuTri() != null && newItem.getIsChuTri().equals(Constants.SEND_RULE)) {
                    btnMove.setVisibility(View.VISIBLE);
                } else {
                    btnMove.setVisibility(View.GONE);
                }

                //button đánh dấu văn bản
                if (newItem.getIsCheck() != null) {
                    btnMark.setVisibility(View.VISIBLE);
                    if (!newItem.getIsCheck().equals(Constants.NOT_MARKED)) {
                        marked = true;
                        btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
                    } else {
                        marked = false;
                        btnMark.setText(getResources().getString(R.string.str_danhgiau));
                    }
                } else {
                    btnMark.setVisibility(View.GONE);
                }
                // ẩn hiện nút chuyển xử lý
                if (newItem.getIsChuyenTiep() != null && newItem.getIsChuyenTiep().equals(Constants.COMMENT_RULE)) {
                    btnChuyenXuLy.setVisibility(View.VISIBLE);
                } else {
                    btnChuyenXuLy.setVisibility(View.GONE);
                }
            } else {
                layoutAction.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
    }

    private void checkFinish(int id) {
        documentWaitingPresenter.checkFinish(id);
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
                checkFinish(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSend = (ImageView) toolbar.findViewById(R.id.btnSend);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillDetail(DetailDocumentWaiting detailDocumentWaiting) {

        if (detailDocumentWaiting.getTrichYeu() != null) {
            tvTitle.setText(detailDocumentWaiting.getTrichYeu());
        }
        if (detailDocumentWaiting.getDonViBanHanh() != null && !detailDocumentWaiting.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentWaiting.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoKiHieu() != null && !detailDocumentWaiting.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentWaiting.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getNgayDenDi() != null && !detailDocumentWaiting.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentWaiting.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getNgayVanBan() != null && !detailDocumentWaiting.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentWaiting.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHinhThucVanBan() != null && !detailDocumentWaiting.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentWaiting.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoVanBan() != null && !detailDocumentWaiting.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentWaiting.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentWaiting.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getDoUuTien() != null && !detailDocumentWaiting.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentWaiting.getDoUuTien());
            if (detailDocumentWaiting.getDoUuTien().equals(getResources().getString(R.string.str_thuong))) {
                tvDoKhan.setTextColor(getResources().getColor(R.color.md_light_green_status));
            } else {
                tvDoKhan.setTextColor(getResources().getColor(R.color.colorTextRed));
            }
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHanGiaiQuyet() != null && !detailDocumentWaiting.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentWaiting.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentWaiting.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHinhThucGui() != null && !detailDocumentWaiting.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentWaiting.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }

    }


    @Override
    public void onSuccess(Object object) {
        if (object instanceof DetailDocumentWaiting) {
            EventBus.getDefault()
                    .postSticky(new ReloadDocWaitingtEvent(true));
            detailDocumentWaiting = (DetailDocumentWaiting) object;
            idDoc = detailDocumentWaiting.getId();
            fillDetail(detailDocumentWaiting);
        }

    }

    @Override
    public void onComment() {

    }

    @Override
    public void onError(APIError apiError) {
        if (!isFinishing()) {
            sendExceptionError(apiError);
            if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                if (connectionDetector.isConnectingToInternet()) {
                    loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
            }
        }

    }
    @Override
    public void onMark() {
        if (marked) {
            marked = false;
            btnMark.setText(getResources().getString(R.string.tv_danhdau));
            Toast.makeText(this, getString(R.string.NOT_MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        } else {
            marked = true;
            btnMark.setText(getResources().getString(R.string.str_huy_danhgiau));
            Toast.makeText(this, getString(R.string.MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        }
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));

    }

    @Override
    public void onFinish() {
        Toast.makeText(this, "Kết thúc văn bản thành công", Toast.LENGTH_LONG).show();
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
        finish();

    }

    @Override
    public void onCheckFinish(boolean isFinish) {
        if (isFinish) {
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnFinish.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.btnMove, R.id.btnMark, R.id.btnFinish, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMove:
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(this);
                applicationSharedPreferences.setPersonPre(new ListPersonPreEvent(null, null, null));
                buttonAnchor = btnMove;
                changeDocumentPresenter.getTypeChangeDocument(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));
                break;
            case R.id.btnMark:
                mark();
                break;
            case R.id.btnFinish:
                if (Application.getApp().getAppPrefs().getAccountLogin().isCommentFinish()) {
                    DialogCommentFinish dialogCommentFinish = new DialogCommentFinish(this);
                    dialogCommentFinish.show();

                } else {
                    finishDoc(null);

                }
                break;
//            case R.id.btnSave:
//                EventBus.getDefault().postSticky(new SaveDocumentEvent(idDoc));
//                startActivity(new Intent(this, SaveDocumentActivity.class));
//                break;

        }

    }

    private void finishDoc(String comment) {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.finish(Integer.parseInt(newItem.getId()), comment);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.mark(detailDocumentWaiting.getId());
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        getDetail();

    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DetailDocumentWaitingActivity.this, MainActivity.class));
        finish();

    }

    @Override
    public void onSuccessTypeChange(List<TypeChangeDocument> typeChangeDocumentList) {
        if (typeChangeDocumentList != null && typeChangeDocumentList.size() > 0) {
            EventBus.getDefault().postSticky(typeChangeDocumentRequest);
            List<String> lables = new ArrayList<String>();
            for (int i = 0; i < typeChangeDocumentList.size(); i++) {
                lables.add(typeChangeDocumentList.get(i).getName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weight_table_menu, R.id.textViewTableMenuItem, lables);
            final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
            listPopupWindow.setAdapter(adapter);

            listPopupWindow.setAnchorView(buttonAnchor);
            listPopupWindow.setWidth(550);
            listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
            listPopupWindow.setHorizontalOffset(-402);
            listPopupWindow.setVerticalOffset(-20);
            listPopupWindow.setModal(true);
            listPopupWindow.show();
        }

    }

    @Override
    public void onErrorTypeChange(APIError apiError) {

        sendExceptionError(apiError);
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage(), true, AlertDialogManager.ERROR);
    }

    @Override
    public void showProgress() {
        if (!isFinishing()) {
            showProgressDialog();

        }

    }

    @Override
    public void hideProgress() {
        if (!isFinishing()) {
            hideProgressDialog();
        }

    }

    private void sendExceptionError(APIError apiError) {
        ExceptionRequest exceptionRequest = new ExceptionRequest();
        LoginInfo eventbus = EventBus.getDefault().getStickyEvent(LoginInfo.class);
        if (eventbus != null) {
            exceptionRequest.setUserId(eventbus.getUsername());
        } else {
            exceptionRequest.setUserId("");
        }
        exceptionRequest.setDevice(appPrefs.getDeviceName());
        ExceptionCallAPIEvent error = EventBus.getDefault().getStickyEvent(ExceptionCallAPIEvent.class);
        if (error != null) {
            exceptionRequest.setFunction(error.getUrlAPI());
        } else {
            exceptionRequest.setFunction("");
        }
        exceptionRequest.setException(apiError.getMessage());

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FeedBackEvent event) {
        if (event.getFeedBack() != null)
            finishDoc(event.getFeedBack());
        EventBus.getDefault().removeStickyEvent(FeedBackEvent.class);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FinishEvent finishEvent) {
        if (finishEvent != null && finishEvent.getType() == 0 && finishEvent.isFinish()) {
            EventBus.getDefault().removeStickyEvent(FinishEvent.class);
            finish();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SaveFinishDocumentEvent finishEvent) {
        if (finishEvent != null && finishEvent.isFinish()) {
            Toast.makeText(this, getString(R.string.FINISH_SUCCESS), Toast.LENGTH_LONG).show();
            EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
            finish();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void ItemClickGetTypeChangeButtonDocument(Button button) {
        buttonAnchor = button;
        changeDocumentPresenter.getTypeChangeDocumentViewFiles(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));


    }
}
