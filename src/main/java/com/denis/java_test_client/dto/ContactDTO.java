package com.denis.java_test_client.dto;

import lombok.Data;

@Data
public class ContactDTO {
    private Long id;      //идентификатор контакта
    private String phone;    // номер телефона
    private String email;

    public ContactDTO(Long id, String phone, String email) {
        this.id = id;
        this.phone = phone;
        this.email = email;
    }
}
