package com.example.login.view.event;

import android.view.View;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonSendNotifyCheck {
    @Getter
    @Setter
    private View view;
    @Getter @Setter private String id;
    @Getter @Setter private String parentId;
    @Getter @Setter private String name;
    @Getter @Setter private String avatar;
    @Getter @Setter private boolean notParent;
}
