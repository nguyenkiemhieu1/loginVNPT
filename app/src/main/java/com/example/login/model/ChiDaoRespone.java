package com.example.login.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ChiDaoRespone extends StatusRespone {
    @SerializedName("data")
    @Setter
    @Getter
    private List<Object> data;
}
