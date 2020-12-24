package com.example.login.presenter;

import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class CheckFinishDocumentRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private String data;
}
