package com.example.login.presenter.DetailDocumentWaiting;

import com.example.login.model.DetailDocumentWaiting.DetailDocumentWaiting;
import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class DetailDocumentWaitingRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private DetailDocumentWaiting data;
}
