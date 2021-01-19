package com.example.login.model.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PersonSendNotifyInfo {
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
    @SerializedName("level")
    @Setter @Getter
    private int level;
    @SerializedName("empId")
    @Setter @Getter
    private String empId;
    @SerializedName("unitId")
    @Setter @Getter
    private String unitId;
    @Setter @Getter
    private boolean isTrace;
    @Setter @Getter
    private List<PersonSendNotifyInfo> childrenList;

}