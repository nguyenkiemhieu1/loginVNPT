package com.example.login.presenter;

public interface HandleSyncService {
    interface HandleGetCount {
        void onSuccessGetCount(Object object);
        void onErrorGetCount(Object object);
    }
    interface HandleGetRecords {
        void onSuccessGetRecords(Object object);
        void onErrorGetRecords(Object object);
    }
    interface HandleGetSchedules {
        void onSuccessGetSchedules(Object object);
        void onErrorGetSchedules(Object object);
    }
}
