package com.example.login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import com.example.login.BuildConfig;
import com.example.login.R;
import com.example.login.common.ConnectionDetector;
import com.example.login.common.Constants;
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
import com.example.login.presenter.Version.CheckVersionRequest;
import com.example.login.presenter.Version.IVersionPresenter;
import com.example.login.presenter.Version.VersionPresenterImpl;
import com.example.login.presenter.loginView.ILoginView;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import butterknife.BindView;
import io.realm.Realm;


public class MainActivity extends BaseActivity implements ILoginView {
    private ApplicationSharedPreferences appPrefs;
    private boolean isValidateLogin;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private Realm realm;


    @BindView(R.id.tv_language)
    TextView tv_language;
    @BindView(R.id.image_language)
    ImageView image_language;
    @NotEmpty(messageResId = R.string.USERNAME_REQUIRED)
    @Length(max = 100, messageResId = R.string.USERNAME_INVALID_LENGTH)
    @BindView(R.id.txtUserName)
    //@Pattern(regex = StringDef.USERNAME_PATTERN, messageResId = R.string.USERNAME_INVALID_STRENGTH)
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
        appPrefs.setDeviceName(nameDevice);
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        return manufacturer + " " + model;
    }

    @SuppressLint("NewApi")
    private void checkNgonNgu() {

        String lang_local = Application.localeManager.getLanguage();
        if (lang_local != null) {
            switch (lang_local) {
                case "vi":
                    tv_language.setText(getString(R.string.str_vn));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_vn));
                    break;
                case "en":
                    tv_language.setText(getString(R.string.str_en));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_en));
                    break;
                case "lo":
                    tv_language.setText(getString(R.string.str_ls));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_ls));
                    break;
                default:
                    tv_language.setText(getString(R.string.str_vn));
                    image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_vn));
                    break;
            }
        } else {
            tv_language.setText(getString(R.string.str_vn));
            image_language.setImageDrawable(getResources().getDrawable(R.drawable.icon_language_vn));
        }


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
//        Intent i = new Intent(this, LoginActivity.class);
//        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private void setFont() {
        txtname1.setTypeface(Application.getApp().getTypeface());
        txtname2.setTypeface(Application.getApp().getTypeface());
        etUserLayout.setTypeface(Application.getApp().getTypeface());
        etPasswordLayout.setTypeface(Application.getApp().getTypeface());
        txtUsername.setTypeface(Application.getApp().getTypeface());

        btnDangNhap.setTypeface(Application.getApp().getTypeface());
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
//        if (connectionDetector.isConnectingToInternet()) {
//            versionPresenter.checkVersion(new CheckVersionRequest("android", BuildConfig.VERSION_NAME));
//        } else {
//            Toast.makeText(this, getString(R.string.NETWORK_TITLE_ERROR) + " + " + getString(R.string.NO_INTERNET_ERROR), Toast.LENGTH_LONG).show();
//        }
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
}