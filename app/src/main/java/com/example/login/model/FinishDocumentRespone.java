package com.example.login.model;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FinishDocumentRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private String data;
}
