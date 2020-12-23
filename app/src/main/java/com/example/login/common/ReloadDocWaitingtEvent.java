package com.example.login.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ReloadDocWaitingtEvent {
    @Getter
    @Setter
    private boolean load;
}
