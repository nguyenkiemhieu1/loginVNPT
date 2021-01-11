package com.example.login.model.SaveDocument;

import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FinishDocumentSaveRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private String data;
}
