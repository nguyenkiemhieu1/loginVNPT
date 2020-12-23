package com.example.login.model.DetailDocumentWaiting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class DetailDocumentInfo implements Serializable {
    @SerializedName("id") @Setter
    @Getter
    private String id;
    @SerializedName("type") @Setter @Getter private String type;
    @SerializedName("recover") @Setter @Getter private String recover;
}

