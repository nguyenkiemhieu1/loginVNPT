package com.example.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.R;
import com.example.login.common.ConnectionDetector;

import com.example.login.configuration.AlertDialogManager;
import com.example.login.configuration.Application;
import com.example.login.configuration.ApplicationSharedPreferences;
import com.example.login.model.APIError;
import com.example.login.model.LoginInfo;
import com.example.login.model.LoginRequest;
import com.example.login.presenter.ExceptionCallAPIEvent;
import com.example.login.presenter.ExceptionRequest;
import com.example.login.presenter.ILoginPresenter;
import com.example.login.presenter.LoginPresenterImpl;

import com.example.login.presenter.loginView.ILoginView;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements ILoginView, Validator.ValidationListener {
    private ApplicationSharedPreferences appPrefs;

    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private boolean isValidateLogin;
    private Validator validator;

    @BindView(R.id.tv_language)
    TextView tv_language;

    @BindView(R.id.image_language)
    ImageView image_language;

    @NotEmpty(messageResId = R.string.USERNAME_REQUIRED)
    @Length(max = 100, messageResId = R.string.USERNAME_INVALID_LENGTH)
    @BindView(R.id.txtUserName)
    EditText txtUsername;
    @NotEmpty(messageResId = R.string.PASSWORD_REQUIRED)
    @Length(min = 8, messageResId = R.string.PASSWORD_INVALID_LENGTH)
    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @BindView(R.id.etUserLayout)
    TextInputLayout etUserLayout;

    @BindView(R.id.etPasswordLayout)
    TextInputLayout etPasswordLayout;

    @BindView(R.id.btn_login)
    Button btnDangNhap;

    @BindView(R.id.txtname2)
    TextView txtname2;

    @BindView(R.id.txtname1)
    TextView txtname1;

    @BindView(R.id.layoutDisplay)
    ConstraintLayout layoutDisplay;

    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    String language = "VI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefs = Application.getApp().getAppPrefs();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        try {
            if (appPrefs.isSaveAccount() && appPrefs.getAccount() != null) {
                txtUsername.setText(appPrefs.getAccount().getUsername());
                txtPassword.setText(appPrefs.getAccount().getPassword());
            }
        } catch (Exception ex) {
        }
        if (appPrefs.isSaveAccount()) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.loginPresenter(appPrefs.getAccount());
            } else {
                Toast.makeText(this, getString(R.string.NETWORK_TITLE_ERROR) + "      +     " + getString(R.string.NO_INTERNET_ERROR), Toast.LENGTH_LONG).show();
            }
        }
        String nameDevice = getDeviceName();
        validator = new Validator(this);
        validator.setValidationListener(this);
        appPrefs.setDeviceName(nameDevice);
    }

    @OnClick({R.id.btn_login, R.id.tv_language, R.id.image_language})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (connectionDetector.isConnectingToInternet()) {
                    validator.validate();
                    if (isValidateLogin) {
                        loginPresenter.loginPresenter(new LoginRequest(txtUsername.getText().toString(),
                                        txtPassword.getText().toString(),
                                        appPrefs.getFirebaseToken(),
                                        "ANDROID",
                                        appPrefs.getDeviceName(),
                                        language
                                )
                        );
                    }
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
                break;
            case R.id.tv_language:
                dialogLanguage();
                break;
            case R.id.image_language:
                dialogLanguage();
                break;

        }
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

    private void dialogLanguage() {
        String lang_local = Application.localeManager.getLanguage();
        int position = 0;
        if (lang_local != null) {
            switch (lang_local) {
                case "vi":
                    position = 0;
                    break;
                case "en":
                    position = 1;
                    break;
                case "lo":
                    position = 2;
                    break;
                default:
                    position = 0;
                    break;
            }
        } else {
            position = 0;
        }

        final String[] list = {getResources().getString(R.string.str_vn), getResources().getString(R.string.str_en), getResources().getString(R.string.str_ls)};
        AlertDialog.Builder aler = new AlertDialog.Builder(this);
        aler.setTitle(R.string.str_language);
        aler.setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        language = "VI";
                        setLanguage("vi");
                        break;
                    case 1:
                        language = "EN";
                        setLanguage("en");
                        break;
                    case 2:
                        language = "LA";
                        setLanguage("lo");
                        break;
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = aler.create();
        dialog.show();
    }

    private void setLanguage(String lang) {
        Application.localeManager.setNewLocale(this, lang);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }


    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        appPrefs.setLogined(true);
        appPrefs.setAccount(new LoginRequest(
                txtUsername.getText().toString(),
                txtPassword.getText().toString(),
                appPrefs.getFirebaseToken(),
                "ANDROID",
                appPrefs.getDeviceName(),
                language
        ));
        appPrefs.setToken(loginInfo.getToken());
        appPrefs.setUnitUser(loginInfo.getUnitId());
        appPrefs.setAccountLogin(loginInfo);
        EventBus.getDefault().postSticky(loginInfo);

    }

    @Override
    public void onErrorLogin(APIError apiError) {
        sendExceptionError(apiError);
        if (!isFinishing()) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR), getString(R.string.LOGIN_INVALID), true, AlertDialogManager.ERROR);
        }

    }

    @Override
    public void showProgress() {
        {
            showProgressDialog();

        }

    }
    @Override
    public void hideProgress() {
        hideProgressDialog();
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

    @Override
    public void onValidationSucceeded() {
        isValidateLogin = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isValidateLogin = false;
    }
}