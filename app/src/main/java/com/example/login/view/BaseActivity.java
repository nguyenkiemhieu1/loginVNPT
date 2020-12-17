package com.example.login.view;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login.R;
import com.example.login.configuration.Application;
import com.example.login.configuration.LocaleManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Application.localeManager.setLocale(base));
    }

    DialogProgress progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
//                MainActivity.class));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initProgressDialog();
    }

    public void initProgressDialog() {
        progressDialog = new DialogProgress(this, getString(R.string.PROCESSING_REQUEST));
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void showProgressDialog() {
        progressDialog.showProgressDialog();
    }

    public void hideProgressDialog() {
        progressDialog.hideProgressDialog();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            try {
                EventBus.getDefault().register(this);
            } catch (Exception e) {
                Log.e("Error", "" + e.getMessage());
            }

        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.stopThread();
        if (progressDialog != null && progressDialog.getProgressDialog().isShowing()) {
            progressDialog.getProgressDialog().dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onBackPressed();
    }


}