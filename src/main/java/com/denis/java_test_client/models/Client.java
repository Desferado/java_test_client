package com.denis.java_test_client.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;  //идентификатор клиента
    @Column(nullable = false)
    private String name;        //имя клиента
    @Column(nullable = false)
    private String lastName;   //фамилия клиента

    @OneToOne @JoinColumn(name = "сontact_id")
    private Contact contact_id; //контакты клиента

    public Client(Long clientId, String name, String lastName) {
        this.clientId = clientId;
        this.name = name;
        this.lastName = lastName;
    }
}
