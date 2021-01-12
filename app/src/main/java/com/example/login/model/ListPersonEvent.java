package com.example.login.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ListPersonEvent {
    @Setter
    @Getter
    private List<Person> personProcessList;
    @Setter @Getter private List<Person> personSendList;
    @Setter @Getter private List<Person> personNotifyList;
    @Setter @Getter private List<Person> personDirectList;
    @Setter @Getter private List<Person> personLienThongList;
    @Setter @Getter private List<Person> personGroupList;
}
