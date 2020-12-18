package com.example.login.configuration;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;

import lombok.Getter;
import lombok.Setter;
import io.realm.Realm;


public class Application extends android.app.Application {

        @Getter
        private static Application app;
        @Getter
        @Setter
        private ApplicationSharedPreferences appPrefs;
        @Getter
        private Typeface typeface;
        @Getter
        private int countFunction; // Số lượng chức năng bên menu trái
        @Getter
        private int timeSync; // Thời gian đồng bộ dữ liệu
        @Getter
        private String baseAPIUrl;
        @Getter
        private String baseAPISigningUrl;

        @Getter
        private String keyStorePassword;
        public static Resources resources;

        public static LocaleManager localeManager;


        @Override
        protected void attachBaseContext(Context base) {
            localeManager = new LocaleManager(base);
            super.attachBaseContext(localeManager.setLocale(base));
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            localeManager.setLocale(this);
        }

        @Override
        public void onCreate() {
            super.onCreate();
//        Fabric.with(this, new Crashlytics());

            app = this;
            resources = getResources();
            appPrefs = new ApplicationSharedPreferences(this);
            countFunction = 15;
            timeSync = 180000;
            baseAPIUrl = "https://dev-vpdt.vnptioffice.vn/qlvb/";
            baseAPISigningUrl = "http://123.30.60.210:80";
            keyStorePassword = "qlvbdhcaobangpassword";
                Realm.init(this);
        }
}
