package com.example.login.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class ListPersonPreEvent {
    @Setter
    @Getter
    private List<Person> personProcessPreList;
    @Setter @Getter private List<Person> personSendPreList;
    @Setter @Getter private List<Person> personNotifyPreList;
}
