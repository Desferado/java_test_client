package com.denis.java_test_client.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contacts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      //идентификатор контакта
    @Column(nullable = false)
    private String phone;    // номер телефона
    @Column(nullable = false)
    private String email;    //адрес электронной почты

}
