package com.example.login.model.DetailDocumentWaiting;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class UnitLogInfo {
    @SerializedName("schema")
    @Setter
    @Getter
    private String schema;
    @SerializedName("unit")
    @Setter @Getter
    private String unit;
    @SerializedName("parameter")
    @Setter @Getter
    private Object parameter;
}
