package com.example.login.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ErrorBodyContent extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private Object token;
}
