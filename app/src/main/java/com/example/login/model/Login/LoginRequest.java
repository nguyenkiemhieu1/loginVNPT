package com.example.login.model.Login;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class LoginRequest {
    @SerializedName("username")
    @Setter
    @Getter
    private String username;
    @SerializedName("password")
    @Setter @Getter
    private String password;
    @SerializedName("tokenFireBase")
    @Setter @Getter
    private String tokenFireBase;
    @SerializedName("type")
    @Setter @Getter
    private String type;
    @SerializedName("device")
    @Setter @Getter
    private String device;
    @SerializedName("language")
    @Setter @Getter
    private String language;
}