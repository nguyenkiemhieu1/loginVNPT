package com.example.login.model.DocumentWaiting;


import com.example.login.model.StatusRespone;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class DocumentWaitingRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private Object data;
}