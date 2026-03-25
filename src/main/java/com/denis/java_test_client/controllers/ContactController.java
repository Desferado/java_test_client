package com.denis.java_test_client.controllers;

import com.denis.java_test_client.dto.ContactDTO;
import com.denis.java_test_client.models.Contact;
import com.denis.java_test_client.services.ContactService;
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
import java.util.stream.Collectors;

@RequestMapping("/contact")
@RestController
public class ContactController {
    /**
     * Класс Contact.
     * В контроллере прописана логика работы с контактами: Добавление, удаление,
     * редактирование, получение всего списка.
     */
    private final ContactService contactService;
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @Operation(
            summary = "Получение списка всех контактов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получение списка всех контактов",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Contact.class))
                            )
                    )
            })
    @GetMapping("/")
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        return ResponseEntity.ok(contactService.findAllContact());
    }
    @Operation(
            summary = "Поиск контакта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск контакта по id",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @GetMapping("{id}")
    public ResponseEntity<ContactDTO> getContactById(
            @PathVariable @Parameter(description = "Поиск контакта с данным id")
            @RequestParam(required = true, name = "номер контакта") Long id) {
        Optional<ContactDTO> contactOptional = contactService.findContactById(id);
        return contactOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Поиск контакта по email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск контакта по email",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @GetMapping("{email}")
    public ResponseEntity <ContactDTO> getContactByEmail(
            @PathVariable @Parameter(description = "Поиск контакта по email")
            @RequestParam(name = "email контакта") String email) {
        Optional<ContactDTO> contactOptional = contactService.findContactByEmail(email);
        return contactOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Поиск контакта по номеру телефона",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск контакта по номеру телефона",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @GetMapping("{phone}")
    public ResponseEntity <ContactDTO> getContactByPhone(
            @PathVariable @Parameter(description = "Поиск контакта с данными номером телефона")
            @RequestParam(name = "номер телефона контакта") String phone) {
        Optional<ContactDTO> contactOptional = contactService.findContactByPhone(phone);
        return contactOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Заведение контакта в базу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заведение контакта в базу",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @PostMapping("/")
    public ResponseEntity <ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        contactService.save(contactDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(
            summary = "Изменение контакта в базе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Измененеие контакта в базе",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @PutMapping("/{id}")
    public ResponseEntity <ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO newContactDTO) {
        return ResponseEntity.ok(contactService.updateContact(id, newContactDTO));
    }
    @Operation(
            summary = "Удаление контакта из базы",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаление контакта из базы",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeContact(
            @PathVariable @Parameter (description = "Индитификатор контакта")
            @RequestParam (required = false, name = "номер контакта") Long id) {
            contactService.deleteContactById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
