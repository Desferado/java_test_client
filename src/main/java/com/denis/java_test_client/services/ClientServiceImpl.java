package com.denis.java_test_client.services;

import com.denis.java_test_client.utils.MethodLog;
import com.denis.java_test_client.exception.ClientNotFoundException;
import com.denis.java_test_client.models.Client;
import com.denis.java_test_client.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Client> findClientByName(String name, String lastName) {
        return Optional.ofNullable(clientRepository.findClientByName(name, lastName)
                .orElseThrow(ClientNotFoundException::new));
    }

    @Override
    public Optional<Client> findClientByClient_id(Integer id) {
        return Optional.ofNullable(clientRepository.findClientByClient_id(id)
                .orElseThrow(ClientNotFoundException::new));
    }

    @Override
    public Client updateClient(Integer id, Client newClient) {
        if(clientRepository.findClientByClient_id(id).isPresent()){
            clientRepository.save(newClient);
        }
        return newClient;
    }

    @Override
    public Optional<Client> deleteClientByClient_id(Integer id) {
        Optional<Client> deleteClient = clientRepository.findClientByClient_id(id);
        if (deleteClient.isEmpty()) {
            System.out.println("Client not found in database");
        }
        else {
            clientRepository.deleteClientByClient_id(id);
        }
        return deleteClient;
    }
}
