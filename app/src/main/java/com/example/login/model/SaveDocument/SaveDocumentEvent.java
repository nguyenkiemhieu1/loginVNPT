package com.example.login.model.SaveDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class SaveDocumentEvent {
    @Getter
    @Setter
    int idDoc;
}
