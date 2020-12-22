package com.example.login.model;

import com.example.login.common.Constants;
import com.example.login.presenter.HandleSyncService;
import com.example.login.presenter.ICallFinishedListener;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseDao {
    public static <T> void call(Call<T> call, final ICallFinishedListener finishedListener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == Constants.RESPONE_SUCCESS_HANDLER) {
                    finishedListener.onCallSuccess(response.body());
                } else {
                    finishedListener.onCallError(BaseService.parseErrorHandler(response));
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    finishedListener.onCallError(new APIError(0, ErrorDef.MESSAGE_TIMEOUT_NETWORK));
                } else {
                    if (t instanceof IOException) {
                        finishedListener.onCallError(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    } else {
                        finishedListener.onCallError(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    }
                }
            }
        });
    }
    public static <T> void call(Call<T> call, final HandleSyncService.HandleGetRecords handleGetRecords) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == Constants.RESPONE_SUCCESS_HANDLER) {
                    handleGetRecords.onSuccessGetRecords(response.body());
                } else {
                    handleGetRecords.onErrorGetRecords(BaseService.parseErrorHandler(response));
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    handleGetRecords.onErrorGetRecords(new APIError(0, ErrorDef.MESSAGE_TIMEOUT_NETWORK));
                } else {
                    if (t instanceof IOException) {
                        handleGetRecords.onErrorGetRecords(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    } else {
                        handleGetRecords.onErrorGetRecords(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    }
                }
            }
        });
    }
}
