package com.example.login.view.event;

import com.example.login.model.respone.PersonSendNotifyInfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonSendNotifyEvent {
    @Getter
    @Setter
    private List<PersonSendNotifyInfo> personSendNotifyInfos;
}
