package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PersonOrUnitExpectedInfo {
    @SerializedName("id")
    @Setter
    @Getter
    private String id;
    @SerializedName("status")
    @Setter @Getter
    private String status;
}
