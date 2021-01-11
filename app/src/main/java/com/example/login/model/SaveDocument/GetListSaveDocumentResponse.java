package com.example.login.model.SaveDocument;

import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetListSaveDocumentResponse extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<SaveDocumentRespone> list;
}
