package com.example.login.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonSendNotifyCheckEvent {
    @Getter
    @Setter
    private List<PersonSendNotifyCheck> personSendNotifyCheckList;
}
