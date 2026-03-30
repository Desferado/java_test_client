package com.denis.java_test_client.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;


@Data
public class ClientDTO {
    private Long clientId;
    private String name;
    private String lastName;
    @JsonManagedReference
    private ContactDTO contact; // Список идентификаторов контактов клиента

    public ClientDTO(Long clientId, String name, String lastName, ContactDTO contact) {
        this.clientId = clientId;
        this.name = name;
        this.lastName = lastName;
        this.contact = contact;
    }
}
