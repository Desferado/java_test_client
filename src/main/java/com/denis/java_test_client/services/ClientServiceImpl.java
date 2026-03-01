package com.denis.java_test_client.services;

import com.denis.java_test_client.models.Client;
import com.denis.java_test_client.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        if (clientRepository.findClientByName(client.getName(), client.getLast_name()).isEmpty()){
            clientRepository.save(client);
        }
        else {
            System.out.println("This is client already added");
        }
        return client;
    }

    @Override
    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient(Client client, Client newClient) {
        if(clientRepository.findClientByClient_id(client.getClient_id()).isPresent()){
            clientRepository.save(newClient);
        }
        return newClient;
    }

    @Override
    public void deleteClient(Client client) {
        if (clientRepository.findClientByClient_id(client.getClient_id()).isPresent()) {
            clientRepository.deleteClientByClient_id(client.getClient_id());
        }
        else {
            System.out.println("Client not found in database");
        }
    }
}
