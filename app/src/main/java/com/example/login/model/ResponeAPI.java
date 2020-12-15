package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ResponeAPI {
    @SerializedName("code")
    @Setter
    @Getter
    private String code;

    @SerializedName("message")
    @Setter @Getter
    private String message;
}