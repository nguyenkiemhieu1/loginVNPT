package com.example.login.model.PersonGroupChange;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PersonGroupChangeDocInfo {
    @SerializedName("id")
    @Setter
    @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;
    @SerializedName("parentId")
    @Setter @Getter
    private String parentId;
}
