package com.denis.java_test_client.services;

import com.denis.java_test_client.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client createClient (Client client);
    List<Client> findAllClient ();
    Optional<Client> findClientByName (String name, String lastName);
    Optional<Client> findClientByClient_id(Integer id);
    Client updateClient(Integer id, Client newClient);
    Optional<Client> deleteClientByClient_id (Integer id);
}
