package com.example.login.model;


import com.example.login.common.Constants;
import com.example.login.common.ConvertUtils;
import com.example.login.configuration.Application;
import com.example.login.configuration.HeaderConfiguration;
import com.example.login.model.Login.LoginInfo;
import com.example.login.model.Login.LoginRespone;
import com.example.login.presenter.LoginServiceHolder;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private LoginServiceHolder myServiceHolder;

    public TokenAuthenticator(LoginServiceHolder myServiceHolder) {
        this.myServiceHolder = myServiceHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException{
        if (myServiceHolder == null) {
            return null;
        }

        try {
            ResponseBody responseBodyCopy = response.peekBody(Long.MAX_VALUE);
            String jsonResponse = responseBodyCopy.string();
            JSONObject objReponse = new JSONObject(jsonResponse);
            JSONObject objStatus = objReponse.optJSONObject("status");
            if (objStatus != null && objStatus.optString("code") != null
                    && objStatus.optString("code").equalsIgnoreCase("error_authentication_required")) {
                retrofit2.Response retrofitResponse = myServiceHolder.get()
                        .getUserLogin(Application.getApp().getAppPrefs().getAccount()).execute();

                if (retrofitResponse != null) {
                    String newAccessToken = "";
                    LoginRespone refreshTokenResponse = (LoginRespone) retrofitResponse.body();
                    if (refreshTokenResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                        LoginInfo loginInfo = ConvertUtils.fromJSON(refreshTokenResponse.getData(), LoginInfo.class);
                        newAccessToken = loginInfo.getToken();
                        Application.getApp().getAppPrefs().setToken(newAccessToken);
                    }


                    return response.request().newBuilder()
                            .header(HeaderConfiguration.AUTHORIZATION_HEADER, newAccessToken)
                            .build();
                }

            }
            else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
}
