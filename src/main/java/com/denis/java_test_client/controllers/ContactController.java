package com.denis.java_test_client.controllers;

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
    @GetMapping("/get-contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
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
    public ResponseEntity<Optional<Contact>> getContactById(
            @Parameter(description = "Поиск контакта с данным id")
            @RequestParam(required = true, name = "номер контакта") Integer id) {
        Optional<Contact> Contact = contactService.findContactById(id);
        if (Contact.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(Contact);
        }
    }

    @Operation(
            summary = "Поиск контакта по номеру телефона",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Поиск контакта по имени",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Contact.class)
                            )
                    )
            })
    @GetMapping("{phone}")
    public ResponseEntity <Contact> getContactByPhone(
            @Parameter(description = "Поиск контакта с данными номером телефона")
            @RequestParam(name = "номер телефона контакта") String phone) {
        Contact contact = contactService.findContactByPhone(phone).orElse(null);
        if (contact == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(contact);
        }
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
    @PostMapping
    public ResponseEntity <Contact> createContact(@RequestBody Contact Contact) {
        return ResponseEntity.ok(contactService.createContact(Contact));
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
    @PutMapping
    public ResponseEntity <Contact> updateContact(@RequestBody Integer id, Contact newContact) {
        Contact Contact = contactService.updateContact(id, newContact);
        if (Contact == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(Contact);
        }
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
    public ResponseEntity<Optional<Contact>> removeContact(
            @Parameter (description = "Удаление пользователя с данным id")
            @RequestParam (required = false, name = "номер пользователя") Integer id) {
        Optional<Contact> Contact = contactService.deleteContactById(id);
        if (Contact.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(Contact);
    }
}
