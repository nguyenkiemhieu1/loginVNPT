package com.example.login.model.SaveDocument;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class DocumentSavedRequest {
    @SerializedName("id")
    @Getter
    @Setter
    private String id;
    @SerializedName("docId")
    @Getter @Setter private int docId;
    @SerializedName("finish")
    @Getter @Setter int  finish;
    @SerializedName("comment")
    @Getter @Setter String  comment;

}
