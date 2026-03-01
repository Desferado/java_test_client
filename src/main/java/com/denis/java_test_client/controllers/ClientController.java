package com.denis.java_test_client.controllers;

import com.denis.java_test_client.models.Client;
import com.denis.java_test_client.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/client")
@RestController
public class ClientController {
    /**
     * Класс Client.
     * В контроллере прописана логика работы с клиентами: Добавление, удаление,
     * редактирование, получение всего списка.
     */
    private final ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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
    @GetMapping("/get-clients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.findAllClient());
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
    public ResponseEntity<Optional<Client>> getClientById(
            @Parameter(description = "Поиск клиента с данным id")
            @RequestParam(required = true, name = "номер клиента") Integer id) {
        Optional<Client> client = clientService.findClientByClient_id(id);
        if (client.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(client);
        }
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
    public ResponseEntity <Client> getClientByName(
            @Parameter(description = "Поиск клиента с данными именем и фамилией")
            @PathVariable String firstName,
            @PathVariable String lastName) {
        Optional<Client> clientOptional = clientService.findClientByName(firstName, lastName);
        return clientOptional.map(ResponseEntity::ok)
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
    @PostMapping
    public ResponseEntity <Client> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.createClient(client));
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
    @PutMapping
    public ResponseEntity <Client> updateClient(@RequestBody Integer id, Client newClient) {
        Client client = clientService.updateClient(id, newClient);
        if (client == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(client);
        }
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
    public ResponseEntity<Optional<Client>> removeClient(
            @Parameter (description = "Удаление пользователя с данным id")
            @RequestParam (required = false, name = "номер пользователя") Integer id) {
        Optional<Client> client = clientService.deleteClientByClient_id(id);
        if (client.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(client);
    }
}
