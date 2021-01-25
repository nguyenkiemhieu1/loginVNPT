package com.example.login.view.event;

import android.view.View;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonProcessCheck {
    @Getter
    @Setter
    private View view;
    @Getter @Setter private String id;
    @Getter @Setter private int unitId;
}
