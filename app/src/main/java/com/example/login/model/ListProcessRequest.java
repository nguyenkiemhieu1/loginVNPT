package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ListProcessRequest {
    @SerializedName("func")
    @Setter
    @Getter
    private String func;
    @SerializedName("roleId")
    @Setter @Getter
    private String roleId;
    @SerializedName("approved")
    @Setter @Getter
    private String approved;
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("donViSoanThao")
    @Setter @Getter
    private String donViSoanThao;
}
