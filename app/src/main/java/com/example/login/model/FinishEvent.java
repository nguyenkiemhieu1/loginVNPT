package com.example.login.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class FinishEvent {
    @Getter
    @Setter
    private int type;
    @Getter @Setter private boolean finish;
}
