package com.denis.java_test_client.services;

import com.denis.java_test_client.models.Client;

import java.util.List;

public interface ClientService {
    Client createClient (Client client);
    List<Client> findAllClient ();
    Client updateClient(Client client, Client newClient);
    void deleteClient (Client client);
}
