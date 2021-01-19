package com.example.login.model.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class JobPositionInfo {
    @SerializedName("id") @Setter
    @Getter
    private String id;
    @SerializedName("name") @Setter @Getter private String name;
}
