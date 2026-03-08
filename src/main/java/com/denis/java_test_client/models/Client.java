package com.denis.java_test_client.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long client_id;  //идентификатор клиента
    @Column(nullable = false)
    private String name;        //имя клиента
    @Column(nullable = false)
    private String last_name;   //фамилия клиента

    @OneToOne @JoinColumn(name = "сontact_id")
    private Contact contact_id; //контакты клиента

    public Client(Long client_id, String name, String last_name) {
        this.client_id = client_id;
        this.name = name;
        this.last_name = last_name;
    }
}
