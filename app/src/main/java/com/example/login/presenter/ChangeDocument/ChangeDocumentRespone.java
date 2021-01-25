package com.example.login.presenter.ChangeDocument;

import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ChangeDocumentRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private String data;
}