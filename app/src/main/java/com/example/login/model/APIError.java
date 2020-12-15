package com.example.login.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class APIError {
    @Getter
    @Setter
    private int code;
    @Getter @Setter private String message;
}
