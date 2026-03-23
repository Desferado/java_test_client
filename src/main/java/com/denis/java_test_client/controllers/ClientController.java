package com.denis.java_test_client.controllers;

import com.denis.java_test_client.dto.ClientDTO;
import com.denis.java_test_client.models.Client;
import com.denis.java_test_client.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/client")
@RestController
public class ClientController {
    /**
     * Класс Client.
     * В контроллере прописана логика работы с клиентами: Добавление, удаление,
     * редактирование, получение всего списка.
     */
    private final ClientService сlientService;
    @Autowired
    public ClientController(ClientService сlientService) {
        this.сlientService = сlientService;
    }
    @Operation(
            summary = "Получение списка всех клиентов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получение списка всех клиентов",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Client.class))
                            )
                    )
            })
    @GetMapping("/")
    public List<ClientDTO> getAllClients() {
        return сlientService.findAllClient()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Operation(
            summary = "Поиск клиента по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск пользователя по id",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    )
            })
    @GetMapping("{id}")
    public ResponseEntity<ClientDTO> getClientById(
            @PathVariable @Parameter(description = "Поиск клиента с данным id")
            @RequestParam(required = true, name = "номер клиента") Long id) {
        var clientOptional = сlientService.findClientByClient_id(id);
        return clientOptional.map(this::convertToDtoAndRespond)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Поиск клиента по имени и фамилии",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск пользователя по имени",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    )
            })
    @GetMapping("/clients/{firstName}/{lastName}")
    public ResponseEntity <ClientDTO> getClientByName(
            @Parameter(description = "Поиск клиента с данными именем и фамилией")
            @PathVariable String firstName,
            @PathVariable String lastName) {
        var clientOptional = сlientService.findClientByName(firstName, lastName);
        return clientOptional.map(this::convertToDtoAndRespond)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Заведение клиента в базу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заведение клиента в базу",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    )
            })
    @PostMapping("/")
    public ResponseEntity <Void> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = convertFromDto(clientDTO);
        сlientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
            summary = "Изменение клиента в базе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененеие клиента в базе",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    )
            })
    @PutMapping("/{id}")
    public ResponseEntity <Void> updateClient(@PathVariable Long id
            ,@RequestBody ClientDTO clientDTO) {
        if (!сlientService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Client existingClient = convertFromDto(clientDTO);
        existingClient.setClient_id(id); // Сохраняем идентификатор клиента
        сlientService.save(existingClient);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Удаление клиента из базы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление клиента из базы",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Client.class)
                            )
                    )
            })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeClient(
            @Parameter (description = "Удаление пользователя с данным id")
            @RequestParam (required = false, name = "номер пользователя") Long id) {
        try {
            сlientService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Перехват исключений при неудачном удалении
            return ResponseEntity.badRequest().body(null);
        }
    }


    private ClientDTO convertToDto(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setClient_id(client.getClient_id());
        dto.setName(client.getName());
        dto.setLastName(client.getLast_name());
        return dto;
    }

    private ResponseEntity<ClientDTO> convertToDtoAndRespond(Client client) {
        return ResponseEntity.ok(convertToDto(client));
    }

    private Client convertFromDto(ClientDTO dto) {
        Client client = new Client();
        client.setClient_id(dto.getClient_id());
        client.setName(dto.getName());
        client.setLast_name(dto.getLastName());
        return client;
    }
}

