package com.denis.java_test_client.services;

import com.denis.java_test_client.dto.ClientDTO;
import com.denis.java_test_client.mapper.ClientMapper;
import com.denis.java_test_client.models.Client;
import com.denis.java_test_client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    /**
     * Найти всех клиентов.
     */
    public List<ClientDTO> findAllClient() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::toClientDTO)
                .collect(Collectors.toList());
    }

    /**
     * Найти клиента по имени и фамилии.
     */
    public Optional<ClientDTO> findClientByName(String name, String lastName) {
        return Optional.ofNullable(ClientMapper.INSTANCE.toClientDTO(clientRepository.findClientByName(name, lastName)));
    }
    /**
     * Найти клиента по id.
     */
    public Optional<ClientDTO> findClientByClient_id(Long id) {
        return Optional.ofNullable(ClientMapper.INSTANCE.toClientDTO(clientRepository.findClientByClient_id(id)));
    }
    
    public ClientDTO updateClientDTO (Long id, ClientDTO updatedClientDTO) {
        Optional<Client> existingClientOpt = clientRepository.findById(id);
        Client existingClient = existingClientOpt.orElseThrow();
        existingClient.setName(updatedClientDTO.getName());
        existingClient.setLastName(updatedClientDTO.getLastName());
        clientRepository.save(existingClient);
        return ClientMapper.INSTANCE.toClientDTO(existingClient);
    }
    /**
     * Сохранить нового клиента или обновить существующего.
     */
    @Transactional
    public void save(ClientDTO clientDTO) {
        clientRepository.save(ClientMapper.INSTANCE.ClientDTOToClient(clientDTO));
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
