package com.denis.java_test_client.repositories;

import com.denis.java_test_client.models.Client;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN FETCH c.contact")
    @NullMarked
    List<Client> findAll();
    @Query("SELECT c FROM Client c JOIN FETCH c.contact")
    Client findClientByClient_id (Long id);
    @Query("SELECT c FROM Client c JOIN FETCH c.contact")
    Client findClientByName (String name, String lastName);
    @Query("SELECT c FROM Client c JOIN FETCH c.contact WHERE c.clientId = :id")
    @NullMarked
    Optional<Client> findById(@Param("id") Long id);
}

