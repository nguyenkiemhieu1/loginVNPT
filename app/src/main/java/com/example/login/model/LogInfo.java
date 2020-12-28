package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class LogInfo {
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("updateBy")
    @Setter @Getter
    private String updateBy;
    @SerializedName("updateDate")
    @Setter @Getter
    private String updateDate;
    @SerializedName("status")
    @Setter @Getter
    private String status;
    @SerializedName("comment")
    @Setter @Getter
    private String comment;
    @SerializedName("chuyenToi")
    @Setter @Getter
    private String chuyenToi;
    @SerializedName("action")
    @Setter @Getter
    private String action;
    @SerializedName("avatar")
    @Setter @Getter
    private String avatar;
}