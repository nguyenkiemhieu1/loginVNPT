package com.example.login.presenter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class ExceptionRequest implements Serializable {
    @SerializedName("userId")
    @Getter
    @Setter
    private String userId;
    @SerializedName("device")
    @Getter @Setter private String device;
    @SerializedName("function")
    @Getter @Setter private String function;
    @SerializedName("exception")
    @Getter @Setter private String exception;

    public ExceptionRequest(){

    }

}
