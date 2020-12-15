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
    

    public File writeResponseBodyToDisk(ResponseBody body, String filename) {
        try {
            // todo change the file location/name according to your needs
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
            int count = 0;
            while (file.exists()) {
                int i = filename.lastIndexOf(".");
                count++;
                String filenameNew = filename.substring(0, i) + "(" + String.valueOf(count) + ")" + "." + filename.substring(i + 1, filename.length());
                file = new File(mContext.getExternalFilesDir(null) + File.separator + filenameNew);
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return file;
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

//    public String validateExtension(String filename) {
//        if (filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOC)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.DOCX)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.XLS)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.XLSX)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PDF)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.JPG)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.JPEG)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.PNG)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.GIF)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.TIFF)
//                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.vnptoffice.common.Constants.BMP)) {
//            return filename.trim().toUpperCase();
//        }
//        return null;
//    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isAtLeastVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }
}
