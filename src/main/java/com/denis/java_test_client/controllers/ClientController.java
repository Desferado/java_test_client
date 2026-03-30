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
    @Autowired
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
    @GetMapping("/")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
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
    public ResponseEntity<ClientDTO> getClientById(
            @PathVariable @Parameter(description = "Индитификатор клиента")
            @RequestParam(name = "номер клиента") Long id) {
        Optional<ClientDTO> clientDTO = clientService.findClientByClient_id(id);
        return clientDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
    @GetMapping("/findClient")
    public ResponseEntity <ClientDTO> getClientByName(
            @Parameter(description = "Имя и фамилия клиента")
            @RequestParam String name,
            @RequestParam String lastName) {
        Optional<ClientDTO> clientDTO = clientService.findClientByName(name, lastName);
        return clientDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
        clientService.save(clientDTO);
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
    @PutMapping("{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id
            , @RequestBody ClientDTO clientDTO) {
            ClientDTO updatedClient = clientService.updateClientDTO(id, clientDTO);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(updatedClient);
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
            @PathVariable @Parameter(description = "Индитификатор клиента")
            @RequestParam(required = false, name = "номер клиента") Long id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



}

