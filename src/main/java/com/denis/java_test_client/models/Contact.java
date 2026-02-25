package com.denis.java_test_client.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="contacts")
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;      //идентификатор контакта
    @Column(nullable = false)
    private String phone;    // номер телефона
    @Column(nullable = false)
    private String email;    //адрес электронной почты

}
