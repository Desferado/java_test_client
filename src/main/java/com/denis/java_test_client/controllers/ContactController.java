package com.denis.java_test_client.controllers;

import com.denis.java_test_client.dto.ContactDTO;
import com.denis.java_test_client.models.Client;
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
    public List<ContactDTO> getAllContacts() {
        return contactService.findAllContact().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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
        var contactOptional = contactService.findContactById(id);
        return contactOptional.map(this::convertToDtoAndRespond)
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
        var contact = contactService.findContactByEmail(email);
        return contact.map(this::convertToDtoAndRespond)
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
        var contact = contactService.findContactByPhone(phone);
        return contact.map(this::convertToDtoAndRespond)
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
        Contact contact = convertFromDto(contactDTO);
        contactService.save(contact);
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
    public ResponseEntity <Void> updateContact(@PathVariable Long id, @RequestBody ContactDTO newContactDTO) {
        if (!contactService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Contact existingContact = convertFromDto(newContactDTO);
        existingContact.setId(id); // Сохраняем идентификатор клиента
        contactService.save(existingContact);
        return ResponseEntity.noContent().build();
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
            @PathVariable @Parameter (description = "Удаление пользователя с данным id")
            @RequestParam (required = false, name = "номер пользователя") Long id) {
        try {
            contactService.deleteContactById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) { // Перехват исключений при неудачном удалении
            return ResponseEntity.badRequest().body(null);
        }
    }



    private ContactDTO convertToDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setPhone(contact.getPhone());
        dto.setEmail(contact.getEmail());
        return dto;
    }

    private ResponseEntity<ContactDTO> convertToDtoAndRespond(Contact contact) {
        return ResponseEntity.ok(convertToDto(contact));
    }

    private Contact convertFromDto(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setPhone(dto.getPhone());
        contact.setEmail(dto.getEmail());
        return contact;
    }
}
