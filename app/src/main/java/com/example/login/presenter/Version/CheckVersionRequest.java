package com.example.login.presenter.Version;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class CheckVersionRequest {
    @SerializedName("type")
    @Getter
    @Setter
    private String type;
    @SerializedName("version")
    @Getter @Setter private String version;
}