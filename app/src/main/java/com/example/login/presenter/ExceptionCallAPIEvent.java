package com.example.login.presenter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ExceptionCallAPIEvent {
    @Getter
    @Setter
    private String urlAPI;
}
