package com.denis.java_test_client.repositories;

import com.denis.java_test_client.dto.ClientDTO;
import com.denis.java_test_client.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByClient_id (Long id);

    Client findClientByName (String name, String lastName);
}
