package com.denis.java_test_client.repositories;

import com.denis.java_test_client.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findClientByClient_id (Integer id);

    Optional<Client> findClientByName (String name, String lastName);
    void deleteClientByClient_id (Integer id);

}
