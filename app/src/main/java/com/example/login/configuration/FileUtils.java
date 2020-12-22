package com.example.login.configuration;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

class FileUtils {
    private Context mContext;

    public FileUtils(Context mContext) {
        this.mContext = mContext;
    }




    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}
