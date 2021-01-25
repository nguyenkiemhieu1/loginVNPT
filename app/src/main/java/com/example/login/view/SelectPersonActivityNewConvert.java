package com.example.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login.R;
import com.example.login.adapter.SelectProcessAdapter;
import com.example.login.adapter.SelectProcessV2Adapter;
import com.example.login.common.ConnectionDetector;
import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.configuration.AlertDialogManager;
import com.example.login.configuration.Application;
import com.example.login.configuration.ApplicationSharedPreferences;
import com.example.login.model.APIError;
import com.example.login.model.ChangeDocumentDirectRequest;
import com.example.login.model.ChangeDocumentNotifyRequest;
import com.example.login.model.ChangeDocumentReceiveRequest;
import com.example.login.model.ChangeDocumentSendRequest;
import com.example.login.model.ChangeProcessRequest;
import com.example.login.model.ListPersonEvent;
import com.example.login.model.Login.LoginInfo;
import com.example.login.model.Person;
import com.example.login.model.PersonGroupChange.PersonGroupChangeDocInfo;
import com.example.login.model.PersonOrUnitExpectedInfo;
import com.example.login.model.PersonProcessInfo;
import com.example.login.model.TypeChangeDocument.TypeChangeDocument;
import com.example.login.model.TypeChangeDocument.TypeChangeDocumentRequest;
import com.example.login.model.TypeChangeEvent;
import com.example.login.model.TypePersonEvent;
import com.example.login.model.respone.JobPositionInfo;
import com.example.login.model.respone.PersonSendNotifyInfo;
import com.example.login.model.respone.UnitInfo;
import com.example.login.presenter.ChangeDocument.ChangeDocumentPresenterImpl;
import com.example.login.presenter.ChangeDocument.IChangeDocumentPresenter;
import com.example.login.presenter.ExceptionErrorPresenter.ExceptionErrorPresenterImpl;
import com.example.login.presenter.ExceptionErrorPresenter.IExceptionErrorPresenter;
import com.example.login.presenter.IExceptionErrorView;
import com.example.login.presenter.ILoginPresenter;
import com.example.login.presenter.LoginPresenterImpl;
import com.example.login.presenter.loginView.ILoginView;
import com.example.login.view.event.PersonSendNotifyCheck;
import com.example.login.view.event.PersonSendNotifyCheckEvent;
import com.example.login.view.event.PersonSendNotifyEvent;
import com.example.login.view.viewevent.IChangeDocumentView;
import com.example.login.view.viewevent.IFilterPersonView;
import com.example.login.view.viewevent.ISelectPersonView;
import com.example.treeview_lib.TreeNode;
import com.example.treeview_lib.TreeView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPersonActivityNewConvert extends BaseActivity implements IChangeDocumentView, ISelectPersonView, ILoginView, IFilterPersonView , IExceptionErrorView {
    @BindView(R.id.layout_donvi_lt)
    LinearLayout layout_donvi_lt;
    @BindView(R.id.layout_send_lienthong)
    LinearLayout layout_send_lienthong;
    @BindView(R.id.btnMorongXL)
    TextView btnMorongXL;
    @BindView(R.id.btnThuGonXL)
    LinearLayout btnThuGonXL;
    @BindView(R.id.btnMorongLT)
    TextView btnMorongLT;
    @BindView(R.id.btnThuGonLT)
    TextView btnThuGonLT;
    @BindView(R.id.tv_nodata_process_send)
    TextView tvNodataProcessSend;
    @BindView(R.id.recycler_view_process)
    RecyclerView recyclerviewProcess;
    @BindView(R.id.layout_process_send)
    LinearLayout layoutProcessSend;
    @BindView(R.id.layout_donvi_xemdb)
    LinearLayout layoutDonviXemdb;
    @BindView(R.id.layout_send_xemdb)
    LinearLayout layoutSendXemdb;
    @BindView(R.id.btnSelectDV)
    Button btnSelectDV;
    @BindView(R.id.btnSelectCN)
    Button btnSelectCN;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSelect;
    private TextView tvTitle;
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this, this, this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private String jobSelected = "";
    private String unitSelected = "";
    @BindView(R.id.layout_person)
    LinearLayout layout_person;
    @BindView(R.id.layout_process)
    LinearLayout layout_process;
    @BindView(R.id.layout_send_notify)
    LinearLayout layout_send_notify;
    @BindView(R.id.layout_donvi)
    LinearLayout layout_donvi;
    @BindView(R.id.tv_hoten)
    TextView tv_hoten;
    @BindView(R.id.tv_load_data)
    TextView tvLoadData;
    @BindView(R.id.tv_xuly_chinh)
    TextView tv_xuly_chinh;
    @BindView(R.id.tv_dong_xuly)
    TextView tv_dong_xuly;
    @BindView(R.id.tv_hoten_donvi)
    TextView tv_hoten_donvi;
    @BindView(R.id.tv_xuly_chinh_donvi)
    TextView tv_xuly_chinh_donvi;
    @BindView(R.id.layoutFilter)
    LinearLayout layoutFilter;
    @BindView(R.id.sPosition)
    Spinner sPosition;
    @BindView(R.id.sUnit)
    Spinner sUnit;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    @BindView(R.id.layoutDisplay1)
    LinearLayout layoutDisplay1;
    RecyclerView.LayoutManager layoutManager;
    private boolean isExpandLienThong;
    private boolean isExpandProcess;
    private boolean isLienThong;
    private String type;

    private ViewGroup viewGroup;
    private ViewGroup viewGroupLienThong;
    private TreeNode root;
    private  TreeView treeView;
    private TreeNode rootLienThong;
    private  TreeView treeViewLienThong;
    private TypePersonEvent typePersonEvent;
    public static Activity activityThis;
    private boolean getLTAction = false;
    private List<PersonProcessInfo> processPersonInfoList = new ArrayList<>();
    private List<PersonOrUnitExpectedInfo> personOrUnitExpectedList = new ArrayList<>();
    private ApplicationSharedPreferences appPrefs;
    private SelectProcessAdapter selectProcessAdapter;
    private int DocId = 0;
    private IExceptionErrorPresenter iExceptionErrorPresenter = new ExceptionErrorPresenterImpl(this);
    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private TypeChangeDocument typeChangeDocument;
    private TypeChangeEvent typeChangeEvent;
    private SelectProcessV2Adapter selectProcessV2Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person_new_convert);
        ButterKnife.bind(this);
        activityThis = this;
        init();

    }



    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    private void init() {
        if(getIntent() != null){
            DocId = getIntent().getIntExtra("DocId", 0);
        }
        appPrefs = Application.getApp().getAppPrefs();
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
        typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
        root = TreeNode.root();
        rootLienThong = TreeNode.root();
        viewGroup = (LinearLayout) findViewById(R.id.layout_person);
        viewGroupLienThong = (LinearLayout) findViewById(R.id.layout_donvi_lt);
        isExpandProcess = true;
        isExpandLienThong = false;
        isLienThong = false;
        layout_person.setVisibility(View.VISIBLE);
        layout_donvi_lt.setVisibility(View.GONE);
        btnThuGonLT.setVisibility(View.VISIBLE);
        btnMorongLT.setVisibility(View.GONE);
        btnThuGonXL.setVisibility(View.VISIBLE);
        btnMorongXL.setVisibility(View.GONE);
        setupToolbar();
        connectionDetector = new ConnectionDetector(this);
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
        layoutDisplay1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
//        txtName.addTextChangedListener(
//                new TextWatcher() {
//                    private Timer timer = new Timer();
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    @Override
//                    public void afterTextChanged(final Editable s) {
//                        timer.cancel();
//                        timer = new Timer();
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                // TODO: do what you need here (refresh list)
//                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        View view = getCurrentFocus();
//                                        if (view != null) {
//                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                                        }
//                                        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
//                                            changeDocumentPresenter.getPersonSend(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
//                                        } else {
//                                            tvLoadData.setVisibility(View.VISIBLE);
//                                            changeDocumentPresenter.getPersonSendProcess(new SearchPersonRequest(unitSelected, txtName.getText().toString().trim(), jobSelected));
//                                        }
//                                    }
//                                });
//                            }
//                        }, Constants.DELAY_TIME_SEARCH);
//                    }
//                }
//        );
    }

    private  void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbarSelectPerson);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);

        setTitle(getString(R.string.SELECT_PERSON));
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSelect = (ImageView) toolbar.findViewById(R.id.btnSelect);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                EventBus.getDefault().removeStickyEvent(TypePersonEvent.class);
                onBackPressed();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPersonSendocument();
            }
        });

    }
    private void dataPersonSendocument(){
        boolean isSelected = true;
        boolean isSelectedXLC = false;
        boolean isSelectedLT = true;
        boolean isSelectedXLCLT = false;
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent != null) {
            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
        }
        List<Person> personList;
        if (typePersonEvent != null) {
            if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_SEND)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_NOTIFY)
                    || typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)
                    || typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
                switch (typePersonEvent.getType()) {
                    case Constants.TYPE_PERSON_SEND:
                        personList = send_notify_person();
                        if (personList != null && personList.size() > 0) {
                            for (Person person : personList) {
                                if (person.isXlc()) {
                                    isSelectedXLC = true;
                                    break;
                                }
                            }
                            listPersonEvent.setPersonSendList(personList);
                        } else {
                            isSelected = false;
                        }
                        break;
                    case Constants.TYPE_PERSON_NOTIFY:
                        personList = listPersonEvent.getPersonNotifyList();
                        personList = send_notify_person();
                        if (personList != null && personList.size() > 0) {
                            for (Person person : personList) {
                                if (person.isXlc()) {
                                    isSelectedXLC = true;
                                    break;
                                }
                            }
                            listPersonEvent.setPersonNotifyList(personList);
                        } else {
                            isSelected = false;
                        }
                        break;
                    case Constants.TYPE_SEND_PERSON_PROCESS:
                        personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                        if (personList != null && personList.size() > 0) {
                            isSelectedXLC = true;
                            listPersonEvent.setPersonProcessList(personList);
                        } else {
                            isSelected = false;
                        }
                        if (isLienThong) {
                            personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                            if (personList != null && personList.size() > 0) {
                                isSelectedXLCLT = true;
                                listPersonEvent.setPersonLienThongList(personList);
                            } else {
                                isSelectedLT = false;
                            }
                        }
                        break;
                    case Constants.TYPE_PERSON_DIRECT:
                        personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                        if (personList != null && personList.size() > 0) {
                            isSelectedXLC = true;
                            listPersonEvent.setPersonDirectList(personList);
                        } else {
                            isSelected = false;
                        }
                        if (isLienThong) {
                            personList = DeleteItemDuplicate(sendDataPersonSelected(treeViewLienThong));
                            if (personList != null && personList.size() > 0) {
                                isSelectedXLCLT = true;
                                listPersonEvent.setPersonLienThongList(personList);
                            } else {
                                isSelectedLT = false;
                            }
                        }
                        break;
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            } else {
                if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                    personList = DeleteItemDuplicate(sendDataPersonSelected(treeView));
                    if (personList != null && personList.size() > 0) {
                        for (Person person : personList) {
                            if (person.isXem()) {
                                isSelected = true;
                                break;
                            }
                        }
                        listPersonEvent.setPersonNotifyList(personList);
                    } else {
                        isSelected = false;
                        isSelectedLT = false;
                    }
                    isSelectedXLC = true;
                } else {
                    if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_PROCESS)) {
                        List<Person> listPersonProgress = new ArrayList<>();
                        if (processPersonInfoList.size() > 0) {
                            for (PersonProcessInfo processInfo : processPersonInfoList) {
                                if (processInfo.isXlc() || processInfo.isDxl()) {

                                    Person person = new Person(processInfo.getUserId(), processInfo.getFullName(),
                                            processInfo.getSecondUnitName(), null, processInfo.isXlc(), processInfo.isDxl(), false, processInfo.getUnitId());
                                    listPersonProgress.add(person);
                                    if (processInfo.isXlc()) {
                                        isSelectedXLC = true;
                                    }
                                }
                            }
                            listPersonEvent.setPersonProcessList(listPersonProgress);
                        } else {
                            isSelected = false;
                            isSelectedLT = false;
                        }


                    } else {
                        personList = listPersonEvent.getPersonProcessList();
                        if (personList != null && personList.size() > 0) {
                            for (Person person : personList) {
                                if (person.isXlc()) {
                                    isSelectedXLC = true;
                                    break;
                                }
                            }
                            listPersonEvent.setPersonProcessList(personList);
                        } else {
                            isSelected = false;
                            isSelectedLT = false;
                        }
                    }
                }
                EventBus.getDefault().postSticky(listPersonEvent);
            }
        }
        listPersonEvent.setPersonGroupList(new ArrayList<Person>());
        EventBus.getDefault().postSticky(listPersonEvent);
        if (isSelected) {
            if (isSelectedXLC || isSelectedXLCLT) {
                type = null;
                if (typeChangeEvent.getTypeChangeDocumentList() != null) {
                    if (typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable() != null && typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable().equals("false")) {//nếu !=null và comment = false thì chuyển văn bản luôn
                        send();
                    } else {
                        startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                    }
                } else {
                    startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                }

            } else {
                AlertDialogManager.showAlertDialog(SelectPersonActivityNewConvert.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
            }
        } else {
            if (isSelectedLT) {
                if (isSelectedXLC || isSelectedXLCLT) {
                    type = null;
                    if (typeChangeEvent.getTypeChangeDocumentList() != null) {
                        int typeSenDoccument = typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getType();
                        if (typeSenDoccument == 1 || typeSenDoccument == 2 || typeSenDoccument == 3 || typeSenDoccument == 4) {
                            if (typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable() != null && typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable().equals("false")) {//nếu 1=null và comment=false thì chuyển văn bản luôn
                                send();
                            } else {
                                startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));

                            }
                        } else {
                            startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                        }
                    } else {
                        startActivity(new Intent(SelectPersonActivityNewConvert.this, NewSendDocumentActivity.class));
                    }
                } else {
                    AlertDialogManager.showAlertDialog(SelectPersonActivityNewConvert.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                }
            } else {
                AlertDialogManager.showAlertDialog(SelectPersonActivityNewConvert.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
            }
        }

    }
    private void send() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
            List<Person> personProcessList = listPersonEvent.getPersonProcessList();
            List<Person> personLTList = listPersonEvent.getPersonLienThongList();
            String xlc = "";
            String dxl = "";
            String xem = "";
            if ((personProcessList != null && personProcessList.size() > 0) ||
                    personLTList != null && personLTList.size() > 0) {
                int index = -1;
                if (personProcessList != null && personProcessList.size() > 0) {
                    for (int i = 0; i < personProcessList.size(); i++) {
                        if (personProcessList.get(i).isXlc()) {
                            index = i;
                            xlc += personProcessList.get(i).getId() + ",";
                        }
                        if (personProcessList.get(i).isDxl()) {
                            dxl += personProcessList.get(i).getId() + ",";
                        }
                        if (personProcessList.get(i).isXem()) {
                            xem += personProcessList.get(i).getId() + ",";
                        }
                    }
                }
                String xlc_lt = "";
                String dxl_lt = "";
                String xem_lt = "";
                if (personLTList != null && personLTList.size() > 0) {
                    for (Person person : personLTList) {
                        if (person.isXlc()) {
                            xlc_lt += person.getId() + ",";
                        }
                        if (person.isDxl()) {
                            dxl_lt += person.getId() + ",";
                        }
                        if (person.isXem()) {
                            xem_lt += person.getId() + ",";
                        }
                    }
                }
                index = 0;//Không bắt validate người nhận XLC
                if (index > -1) {
                    ChangeProcessRequest changeProcessRequest = new ChangeProcessRequest();
                    TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                    changeProcessRequest.setDocId(typeChangeDocumentRequest.getDocId());
                    changeProcessRequest.setPrimaryProcess(!xlc.equals("") ? xlc.substring(0, xlc.length() - 1) : "");
                    changeProcessRequest.setCoevalProcess(!dxl.equals("") ? dxl.substring(0, dxl.length() - 1) : "");
                    changeProcessRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                    changeProcessRequest.setPrimaryInternal(!xlc_lt.equals("") ? xlc_lt.substring(0, xlc_lt.length() - 1) : "");
                    changeProcessRequest.setCoevalInternal(!dxl_lt.equals("") ? dxl_lt.substring(0, dxl_lt.length() - 1) : "");
                    changeProcessRequest.setReferInternal(!xem_lt.equals("") ? xem_lt.substring(0, xem_lt.length() - 1) : "");
                    changeProcessRequest.setComment("");
                    changeProcessRequest.setType(Constants.TYPE_SEND_PROCESS_REQUEST);
                    changeProcessRequest.setSms(0);
                    changeProcessRequest.setFinish(1);
                    changeProcessRequest.setJob(0);
                    changeProcessRequest.setHanXuLy("");
                    changeProcessRequest.setFiles(new ArrayList<Object>());

                    if (xlc.isEmpty() && xlc_lt.isEmpty()) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                getString(R.string.str_chuachon_xlc), true, AlertDialogManager.INFO);
                        return;
                    }

                    if (xlc.isEmpty() && dxl.isEmpty() && xem.isEmpty()
                            && xlc_lt.isEmpty() && dxl_lt.isEmpty() && xem_lt.isEmpty()) {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    } else {
                        changeDocumentPresenter.changeProcess(changeProcessRequest);
                    }

                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
            }
        } else {
            if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                try {
                    List<Person> personTBList = listPersonEvent.getPersonNotifyList();
                    String xem = "";
                    if (personTBList != null && personTBList.size() > 0) {
                        for (Person person : personTBList) {
                            if (person.isXem()) {
                                xem += person.getId() + ",";
                            }
                        }
                        ChangeDocumentNotifyRequest changeDocumentNotifyRequest = new ChangeDocumentNotifyRequest();
                        changeDocumentNotifyRequest.setDocId(typeChangeDocumentRequest.getDocId());
                        changeDocumentNotifyRequest.setComment("");
                        changeDocumentNotifyRequest.setPrimaryProcess("");
                        changeDocumentNotifyRequest.setCoevalProcess("");
                        changeDocumentNotifyRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                        changeDocumentNotifyRequest.setPrimaryInternal("");
                        changeDocumentNotifyRequest.setCoevalInternal("");
                        changeDocumentNotifyRequest.setReferInternal("");
                        changeDocumentNotifyRequest.setType("1");
                        changeDocumentNotifyRequest.setSms(0);
                        changeDocumentNotifyRequest.setJob(0);
                        changeDocumentNotifyRequest.setHanXuLy("");
                        changeDocumentNotifyRequest.setFiles(new ArrayList<Object>());

                        if (xem.isEmpty()) {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                        } else {
                            changeDocumentPresenter.changeDocNotify(changeDocumentNotifyRequest);
                        }

                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    }
                } catch (Exception e) {
                    System.out.println("================lỗi================= " + e);
                }
            } else {

                List<TypeChangeDocument> typeChangeDocumentList = typeChangeEvent.getTypeChangeDocumentList();
                if (typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep().equals("get_cleck_publish") &&
                        (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                                typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                    List<Person> personXLCList = listPersonEvent.getPersonDirectList();
                    List<Person> personLTList = listPersonEvent.getPersonLienThongList();
                    String xlc = "";
                    String dxl = "";
                    String xem = "";
                    if ((personXLCList != null && personXLCList.size() > 0) ||
                            personLTList != null && personLTList.size() > 0) {
                        if (personXLCList != null && personXLCList.size() > 0) {
                            for (Person person : personXLCList) {
                                if (person.isXlc()) {
                                    xlc += person.getId() + ",";
                                }
                                if (person.isDxl()) {
                                    dxl += person.getId() + ",";
                                }
                                if (person.isXem()) {
                                    xem += person.getId() + ",";
                                }
                            }
                        }
                        String xlc_lt = "";
                        String dxl_lt = "";
                        String xem_lt = "";
                        if (personLTList != null && personLTList.size() > 0) {
                            for (Person person : personLTList) {
                                if (person.isXlc()) {
                                    xlc_lt += person.getId() + ",";
                                }
                                if (person.isDxl()) {
                                    dxl_lt += person.getId() + ",";
                                }
                                if (person.isXem()) {
                                    xem_lt += person.getId() + ",";
                                }
                            }
                        }
                        TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                        ChangeDocumentDirectRequest changeDocumentDirectRequest = new ChangeDocumentDirectRequest();
                        changeDocumentDirectRequest.setDocId(typeChangeDocumentRequest.getDocId());
                        changeDocumentDirectRequest.setComment("");
                        changeDocumentDirectRequest.setPrimaryProcess(!xlc.equals("") ? xlc.substring(0, xlc.length() - 1) : "");
                        changeDocumentDirectRequest.setCoevalProcess(!dxl.equals("") ? dxl.substring(0, dxl.length() - 1) : "");
                        changeDocumentDirectRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                        changeDocumentDirectRequest.setPrimaryInternal(!xlc_lt.equals("") ? xlc_lt.substring(0, xlc_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setCoevalInternal(!dxl_lt.equals("") ? dxl_lt.substring(0, dxl_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setReferInternal(!xem_lt.equals("") ? xem_lt.substring(0, xem_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                        changeDocumentDirectRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                        changeDocumentDirectRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                        changeDocumentDirectRequest.setActionType(String.valueOf(type));
                        changeDocumentDirectRequest.setSms(0);
                        changeDocumentDirectRequest.setJob(0);
                        changeDocumentDirectRequest.setHanXuLy("");
                        changeDocumentDirectRequest.setFiles(new ArrayList<Object>());

                        if (xlc.isEmpty() && xlc_lt.isEmpty()) {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    getString(R.string.str_chuachon_xlc), true, AlertDialogManager.INFO);
                            return;
                        }

                        if (xlc.isEmpty() && dxl.isEmpty() && xem.isEmpty()
                                && xlc_lt.isEmpty() && dxl_lt.isEmpty() && xem_lt.isEmpty()) {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                                    getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                        } else {
                            changeDocumentPresenter.changeDirect(changeDocumentDirectRequest);
                        }

                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    }
                } else {
                    List<Person> personProcessList = listPersonEvent.getPersonProcessList();
                    List<Person> personSendList = listPersonEvent.getPersonSendList();
                    if (personProcessList != null && personProcessList.size() > 0) {
                        String dongXuLy = "";
                        int index = -1;
                        for (int i = 0; i < personProcessList.size(); i++) {
                            if (personProcessList.get(i).isXlc()) {
                                index = i;
                            } else {
                                dongXuLy += personProcessList.get(i).getId() + ",";
                            }
                        }
                        if (index > -1) {
                            String dongGui = "";
                            if (personSendList != null && personSendList.size() > 0) {
                                for (Person person : personSendList) {
                                    if (person.isXlc()) {
                                        dongGui += person.getId() + ",";
                                    }
                                }
                            }
                            TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND)) {
                                ChangeDocumentSendRequest changeDocumentSendRequest = new ChangeDocumentSendRequest();
                                changeDocumentSendRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentSendRequest.setChuTri(personProcessList.get(index).getId());
                                changeDocumentSendRequest.setPhoiHop(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentSendRequest.setSFunc(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep());
                                changeDocumentSendRequest.setSApproved(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentSendRequest.setSMore("");
                                changeDocumentSendRequest.setAssigner("");
                                changeDocumentSendRequest.setAct(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentSendRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentSendRequest.setFormId("");
                                changeDocumentSendRequest.setSendType(String.valueOf(type));
                                changeDocumentSendRequest.setJob(0);
                                changeDocumentSendRequest.setHanXuLy("");
                                changeDocumentSendRequest.setFiles(new ArrayList<Object>());

                                changeDocumentPresenter.changeSend(changeDocumentSendRequest);

                            }
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                                ChangeDocumentReceiveRequest changeDocumentReceiveRequest = new ChangeDocumentReceiveRequest();
                                changeDocumentReceiveRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentReceiveRequest.setChuTri(personProcessList.get(index).getId());
                                changeDocumentReceiveRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentReceiveRequest.setDongXuLy(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentReceiveRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentReceiveRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                                changeDocumentReceiveRequest.setIsBanHanh("0");
                                changeDocumentReceiveRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentReceiveRequest.setComment("");
                                changeDocumentReceiveRequest.setSendType(String.valueOf(type));
                                changeDocumentReceiveRequest.setJob(0);
                                changeDocumentReceiveRequest.setHanXuLy("");
                                changeDocumentReceiveRequest.setFiles(new ArrayList<Object>());

                                changeDocumentPresenter.changeReceive(changeDocumentReceiveRequest);
                            }
                        } else {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                    }
                }
            }
        }
    }

    private List<Person> send_notify_person() {
        PersonSendNotifyCheckEvent personSendNotifyCheckEvent = EventBus.getDefault().getStickyEvent(PersonSendNotifyCheckEvent.class);
        List<PersonSendNotifyCheck> personSendNotifyChecks = personSendNotifyCheckEvent.getPersonSendNotifyCheckList();
        //if (personList == null) {
        List<Person> personList = new ArrayList<>();
        //}
        if (personSendNotifyChecks != null && personSendNotifyChecks.size() > 0) {
            for (int i = 0; i < personSendNotifyChecks.size(); i++) {
                View view = personSendNotifyChecks.get(i).getView();
                CheckBox checkXLChinh = (CheckBox) view.findViewById(R.id.checkXLChinh);
                //if (personSendNotifyChecks.get(i).isNotParent()) {
                if (checkXLChinh.isChecked()) {
//                        int index = -1;
//                        for (int j = 0; j < personList.size(); j++) {
//                            if (personSendNotifyChecks.get(i).getId().equals(personList.get(j).getId())) {
//                                index = j;
//                                break;
//                            }
//                        }
                    //if (index == -1) {
                    List<PersonSendNotifyInfo> personSendNotifyInfoList = EventBus.getDefault().getStickyEvent(PersonSendNotifyEvent.class).getPersonSendNotifyInfos();
                    for (int j = 0; j < personSendNotifyInfoList.size(); j++) {
                        if (personSendNotifyInfoList.get(j).getId().equals(personSendNotifyChecks.get(i).getId())) {
                            Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
                                    personSendNotifyInfoList.get(j).getName(), null, personSendNotifyChecks.get(i).getParentId(), true, false, false, 0);
                            personList.add(person);
                            break;
                        }// else {
//                            if (personSendNotifyChecks.get(i).getParentId() == null || personSendNotifyChecks.get(i).getParentId().trim().equals("")) {
//                                Person person = new Person(personSendNotifyChecks.get(i).getId(), personSendNotifyChecks.get(i).getName(),
//                                        personSendNotifyChecks.get(i).getName(), null, true, false, false, 0);
//                                personList.add(person);
//                                break;
//                            }
//                        }
                    }
                    //}
                }
                //}
            }
        }
        return personList;
    }
    private List<Person> DeleteItemDuplicate(List<Person> personList) {//xóa phần tử trùng lặp trong List
        if (personList != null) {
            for (int i = 0; i < personList.size(); i++) {
                for (int j = i + 1; j < personList.size(); j++) {
                    if (personList.get(i).equals(personList.get(j))) {
                        personList.remove(i);
                    }
                }
            }
            return personList;
        } else {
            return null;
        }
    }

    private List<Person> sendDataPersonSelected(TreeView treeView) {
        List<Person> personList = new ArrayList<>();
        List<TreeNode> selectedNodes = new ArrayList<TreeNode>();
        List<TreeNode> selectedXEMNodes = new ArrayList<TreeNode>();
        List<TreeNode> selectedPHNodes = new ArrayList<TreeNode>();
        if (treeView != null) {
            selectedNodes = treeView.getSelectedXLCNodes();
            selectedXEMNodes = treeView.getSelectedXEMNodes();
            selectedPHNodes = treeView.getSelectedPHNodes();
        }

        if (selectedNodes != null && selectedNodes.size() > 0) {

            for (int i = 0; i < selectedNodes.size(); i++) {

                Person personXLC = new Person(((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getId(),
                        ((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getName(),
                        ((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getName(), null,
                        ((PersonSendNotifyInfo) selectedNodes.get(i).getValue()).getParentId(),
                        true, false, false, 0);
                if (personXLC != null) {
                    personList.add(personXLC);
                }
            }
        }
        if (selectedXEMNodes != null && selectedXEMNodes.size() > 0) {

            for (int i = 0; i < selectedXEMNodes.size(); i++) {
                Person personXEM = new Person(((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getId(),
                        ((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getName(),
                        ((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getName(), null,
                        ((PersonSendNotifyInfo) selectedXEMNodes.get(i).getValue()).getParentId(),
                        false, false, true, 0);
                if (personXEM != null) {
                    personList.add(personXEM);
                }
            }
        }
        if (selectedPHNodes != null && selectedPHNodes.size() > 0) {
            for (int i = 0; i < selectedPHNodes.size(); i++) {
                Person personPHN = new Person(((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getId(),
                        ((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getName(),
                        ((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getName(), null,
                        ((PersonSendNotifyInfo) selectedPHNodes.get(i).getValue()).getParentId(),
                        false, true, false, 0);
                if (personPHN != null) {
                    personList.add(personPHN);
                }
            }
        }
        return personList;
    }

    private void displayPersonProcess(List<PersonProcessInfo> processInfoList) {
        layoutFilter.setVisibility(View.GONE);
        layout_process.setVisibility(View.GONE);
        layoutProcessSend.setVisibility(View.VISIBLE);
        layout_send_notify.setVisibility(View.GONE);
        layout_send_lienthong.setVisibility(View.GONE);
        layoutSendXemdb.setVisibility(View.GONE);
        if (processInfoList != null && processInfoList.size() > 0) {
            tvNodataProcessSend.setVisibility(View.GONE);
            recyclerviewProcess.setVisibility(View.VISIBLE);
            recyclerviewProcess.setHasFixedSize(false);
            layoutManager = new LinearLayoutManager(this);
            recyclerviewProcess.setNestedScrollingEnabled(false);
            recyclerviewProcess.setLayoutManager(layoutManager);
            selectProcessV2Adapter = new SelectProcessV2Adapter(this, R.layout.item_select_process_list, processInfoList);
            recyclerviewProcess.setAdapter(selectProcessV2Adapter);
            if (processInfoList.size() == 1) {
                processPersonInfoList.get(0).setXlc(true);
                processPersonInfoList.get(0).setDxl(false);
                selectProcessV2Adapter.notifyItemChanged(0);
                if (typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable() != null && typeChangeEvent.getTypeChangeDocumentList().get(typeChangeEvent.getSelected()).getCommentEnable().equals("false")) {//nếu !=null và commnent =false thì chuyển văn bản luôn
                    List<Person> personList = new ArrayList<>();
                    Person person = new Person(processPersonInfoList.get(0).getUserId(), processPersonInfoList.get(0).getFullName(),
                            processPersonInfoList.get(0).getSecondUnitName(), null, true, false, false, processPersonInfoList.get(0).getUnitId());
                    personList.add(person);
                    ListPersonEvent listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                    listPersonEvent.setPersonProcessList(personList);
                    EventBus.getDefault().postSticky(listPersonEvent);
                    send();
                } else {
                    dataPersonSendocument();
                }
            }
        } else {
            tvNodataProcessSend.setVisibility(View.VISIBLE);
            recyclerviewProcess.setVisibility(View.GONE);
        }
    }


    @Override
    public void onSuccessChangeDoc() {

    }

    @Override
    public void onSuccessFormList(List<String> listForm) {

    }

    @Override
    public void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos) {

    }

    @Override
    public void onSuccessUpLoad(List<Object> object) {

    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess(Object object) {
        hideProgress();
        tvLoadData.setVisibility(View.GONE);
        if(typePersonEvent != null){
            switch (typePersonEvent.getType()){
                case Constants.TYPE_PERSON_PROCESS:
                    processPersonInfoList = ConvertUtils.fromJSONList(object, PersonProcessInfo.class);
                    displayPersonProcess(processPersonInfoList);
                    if (processPersonInfoList != null && processPersonInfoList.size() > 1) {
                        getPersonOrUnitExpected(DocId);
                    }
                    break;

            }
        }

    }


    @Override
    public void onSuccessJobPossitions(List<JobPositionInfo> jobPositionInfos) {
        displayJobPossition(jobPositionInfos);

    }

    @Override
    public void onSuccessUnits(List<UnitInfo> unitInfos) {

    }

    @Override
    public void onError(APIError apiError) {

    }

    @Override
    public void onSuccessLienThong(List<PersonSendNotifyInfo> lienThongInfos) {

    }

    @Override
    public void onSuccessPersonOrUnit(Object object) {

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

    @Override
    public void onSuccessException(Object object) {

    }

    @Override
    public void onExceptionError(APIError apiError) {

    }
    private View textviewNotData() {
        btnThuGonXL.setVisibility(View.GONE);
        final TextView textView = new TextView(this);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._14sp));
        textView.setText("Không có dữ liệu");
        View view = textView;
        view.setBackgroundColor(Color.WHITE);
        return view;
    }

    private List<PersonSendNotifyInfo> buildUnitTree(List<PersonSendNotifyInfo> personSendTreeNodeNotifyInfoList, final String id) {
        final List<PersonSendNotifyInfo> results = new ArrayList<>();
        if (personSendTreeNodeNotifyInfoList == null) {
            return null;
        }
        for (PersonSendNotifyInfo unit : personSendTreeNodeNotifyInfoList) {
            if (!unit.isTrace() && (unit.getParentId() == id || (unit.getParentId() != null && unit.getParentId().equalsIgnoreCase(id)))) {
                unit.setTrace(true);
                PersonSendNotifyInfo dicItem = new PersonSendNotifyInfo();
                dicItem.setId(unit.getId());
                dicItem.setName(unit.getName());
                dicItem.setParentId(unit.getParentId());
                dicItem.setChildrenList(buildUnitTree(personSendTreeNodeNotifyInfoList, unit.getId()));
                results.add(dicItem);
            }
        }
        return results;
    }

    private void buildTreeData(List<PersonSendNotifyInfo> listData, int level, TreeNode root) {
        if (listData == null || root == null) {
            return;
        }
        if (level > 2) {
            Log.e("buildTreeData", level + ((PersonSendNotifyInfo) root.getValue()).getName());
        }
        for (PersonSendNotifyInfo itemData : listData) {
            TreeNode treeNode = new TreeNode(itemData);
            treeNode.setLevel(level);
            if (itemData.getChildrenList() != null && itemData.getChildrenList().size() > 0) {
                int newlevel = level + 1;
                buildTreeData(itemData.getChildrenList(), newlevel, treeNode);
            }
            root.addChild(treeNode);
        }
    }

    private void displayJobPossition(final List<JobPositionInfo> jobPositionInfos) {
        if (jobPositionInfos != null && jobPositionInfos.size() > 0) {
            String[] spinnerItems = new String[jobPositionInfos.size() + 1];
            spinnerItems[0] = getString(R.string.SELECT_JOB_POSSITION);
            for (int i = 0; i < jobPositionInfos.size(); i++) {
                spinnerItems[i + 1] = jobPositionInfos.get(i).getName();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                    return v;
                }

                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View v = super.getDropDownView(position, convertView, parent);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                    return v;
                }
            };
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            sPosition.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            sPosition.setSelection(0);
            sPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    if (position > 0) {
                        jobSelected = jobPositionInfos.get(position - 1).getId();
                    } else {
                        jobSelected = "";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    private void getPersonOrUnitExpected(int docId) {//lấy danh sách cá nhân/đơn vị dự kiến
        changeDocumentPresenter.getPersonOrUnitExpected(docId);
    }
}
