package com.example.login.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class TypePersonEvent {
    @Getter
    @Setter
    private String type;
}
