package com.example.login.model.DocumentWaiting;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class DocumentWaitingRequest {
    @SerializedName("pageNo")
    @Getter @Setter
    private String pageNo;

    @SerializedName("pageRec")
    @Getter @Setter
    private String pageRec;

    @SerializedName("param")
    @Getter @Setter
    private String parameter;

    @SerializedName("kho")
    @Getter @Setter
    private String kho;

}