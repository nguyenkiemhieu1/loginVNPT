package com.example.login.presenter.DocumentWaitingDao;

import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class CountDocumentWaitingRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private Object data;
}
