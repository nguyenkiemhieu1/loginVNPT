package com.example.login.presenter;

import com.example.login.model.Login.LoginRequest;
import com.example.login.model.Login.LoginRespone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ILoginService {
    @POST(ServiceUrl.LOGIN_V2_URL)
    @Headers({"Content-Type: application/json"})
    Call<LoginRespone> getUserLogin(@Body LoginRequest loginRequest);
}
