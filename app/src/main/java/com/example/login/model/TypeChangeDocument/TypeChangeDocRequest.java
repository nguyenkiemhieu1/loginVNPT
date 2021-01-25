package com.example.login.model.TypeChangeDocument;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class TypeChangeDocRequest {
    @SerializedName("docId")
    @Setter
    @Getter
    private String docId;
    @SerializedName("processDefinitionId")
    @Setter @Getter
    private String processDefinitionId;
}