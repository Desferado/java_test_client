package com.denis.java_test_client.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "clients")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer client_id;  //идентификатор клиента
    @Column(nullable = false)
    private String name;        //имя клиента
    @Column(nullable = false)
    private String last_name;   //фамилия клиента

    @OneToOne @JoinColumn(name = "сontact_id")
    private Contact contact_id; //контакты клиента

}
