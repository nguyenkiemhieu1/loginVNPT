package com.example.login.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class TypeChangeEvent {
    @Getter
    @Setter
    private String note;
    @Getter @Setter private int selected;
    @Getter @Setter private List<TypeChangeDocument> typeChangeDocumentList;
}
