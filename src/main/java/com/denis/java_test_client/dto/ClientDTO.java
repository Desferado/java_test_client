package com.denis.java_test_client.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ClientDTO {
    private Long client_id;
    private String name;
    private String lastName;
    private Long contactIds; // Список идентификаторов контактов клиента

}
