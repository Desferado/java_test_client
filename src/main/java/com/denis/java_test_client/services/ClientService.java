package com.denis.java_test_client.services;

import com.denis.java_test_client.exception.ClientNotFoundException;
import com.denis.java_test_client.models.Client;
import com.denis.java_test_client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client createClient(Client client) {
        if (clientRepository.findClientByName(client.getName(), client.getLast_name()).isEmpty()){
            clientRepository.save(client);
        }
        else {
            System.out.println("This is client already added");
        }
        return client;
    }


    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }


    public Optional<Client> findClientByName(String name, String lastName) {
        return Optional.ofNullable(clientRepository.findClientByName(name, lastName)
                .orElseThrow(ClientNotFoundException::new));
    }


    public Optional<Client> findClientByClient_id(Long id) {
        return Optional.ofNullable(clientRepository.findClientByClient_id(id)
                .orElseThrow(ClientNotFoundException::new));
    }


    /**
     * Сохранить нового клиента или обновить существующего.
     */
    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }
    /**
     * Проверить существование клиента по идентификатору.
     */
    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }
    /**
     * Удалить клиента по идентификатору.
     */
    @Transactional
    public void deleteById(Long id) {
        if (!existsById(id)) {
            throw new IllegalArgumentException("Клиент с таким ID не найден!");
        }
        clientRepository.deleteById(id);
    }
}
