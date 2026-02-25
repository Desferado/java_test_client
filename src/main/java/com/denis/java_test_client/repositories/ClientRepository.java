package com.denis.java_test_client.repositories;

import com.denis.java_test_client.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByName(String name);

}
