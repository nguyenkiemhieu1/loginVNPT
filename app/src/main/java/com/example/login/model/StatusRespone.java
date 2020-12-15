package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class StatusRespone {
    @SerializedName("status")
    @Setter
    @Getter
    private ResponeAPI responeAPI;
}
