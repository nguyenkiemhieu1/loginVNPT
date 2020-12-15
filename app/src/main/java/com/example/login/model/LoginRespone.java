package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class LoginRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private Object data;
}
