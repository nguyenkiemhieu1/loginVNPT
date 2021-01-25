package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class ChangeProcessRequest {
    @SerializedName("docId")
    @Setter
    @Getter
    private String docId;
    @SerializedName("primaryProcess")
    @Setter @Getter
    private String primaryProcess;
    @SerializedName("coevalProcess")
    @Setter @Getter
    private String coevalProcess;
    @SerializedName("referProcess")
    @Setter @Getter
    private String referProcess;
    @SerializedName("primaryInternal")
    @Setter @Getter
    private String primaryInternal;
    @SerializedName("coevalInternal")
    @Setter @Getter
    private String coevalInternal;
    @SerializedName("referInternal")
    @Setter @Getter
    private String referInternal;
    @SerializedName("comment")
    @Setter @Getter
    private String comment;
    @SerializedName("type")
    @Setter @Getter
    private String type;
    @SerializedName("sms")
    @Setter @Getter
    private int sms;
    @SerializedName("finish")
    @Setter @Getter
    private int finish;
    @SerializedName("job")
    @Setter @Getter
    private int job;
    @SerializedName("hanXuLy")
    @Setter @Getter
    private String hanXuLy;
    @SerializedName("files")
    @Setter @Getter
    private List<Object> files;

}