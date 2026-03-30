package com.denis.java_test_client.controllers;

import com.denis.java_test_client.dto.ClientDTO;
import com.denis.java_test_client.dto.ContactDTO;
import com.denis.java_test_client.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClientService clientService;

    ContactDTO contact1 = new ContactDTO(1L, "89123456789", "johndoe@mail.ru");
    ContactDTO contact2 = new ContactDTO(2L, "89876543210", "janesmith@mail.ru");
    ContactDTO contact3 = new ContactDTO(3L, "89085764532", "jamestasker@mail.ru");
    ContactDTO expectedContact = new ContactDTO(4L, "89123451111", "jackdaniels@mail.ru");
    ClientDTO client1 = new ClientDTO(1L,"John", "Doe", contact1);
    ClientDTO client2 = new ClientDTO(2L, "Jane", "Smith", contact2);
    ClientDTO client3 = new ClientDTO(3L, "James", "Tasker", contact3);
    ClientDTO expectedClient = new ClientDTO(1L, "Jack", "Daniels", expectedContact);

    @BeforeEach
    void setUp() {
        when(clientService.findAllClient()).thenReturn(List.of(client1, client2, client3));
        when(clientService.findClientByClient_id(1L)).thenReturn(Optional.ofNullable(client1));
        when(clientService.findClientByName("John", "Doe")).thenReturn(Optional.ofNullable(client1));
        when(clientService.updateClientDTO(1L, expectedClient)).thenReturn(expectedClient);
    }

    @Test
    void shouldReturnListOfClientsWhenGettingAllClients() throws Exception {
        // Выполнение GET-запроса к endpoint "/get-clients"
        mockMvc.perform(MockMvcRequestBuilders.get("/client/"))
                .andDo(print())
                .andExpect(status().isOk())          // Проверка статуса HTTP 200
                .andExpect(MockMvcResultMatchers.content().contentType(String.valueOf(MediaType.APPLICATION_JSON))) // Проверка MIME-типа
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())  // Проверка, что ответ — массив
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("John")) // Проверка первого элемента
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].contact.phone").value("89123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].contact.email").value("johndoe@mail.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Jane")) // Проверка второго элемента
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].contact.phone").value("89876543210"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].contact.email").value("janesmith@mail.ru"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].name").value("James")) // Проверка третьего элемента
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].lastName").value("Tasker"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].contact.phone").value("89085764532"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].contact.email").value("jamestasker@mail.ru"));
    }

    @Test
    void getClientById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/client/{id}", 1L) // Используем path variable {id}
                        .param("номер клиента", String.valueOf(1L)) // Передаем обязательный RequestParam
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.phone").value("89123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.email").value("johndoe@mail.ru"));
    }

    @Test
    void getClientByName() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/client/findClient")
                        .param("name", "John")
                        .param("lastName", "Doe")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.phone").value("89123456789"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.email").value("johndoe@mail.ru"));
    }

    @Test
    void createClient() throws Exception{
        ContactDTO contact = new ContactDTO(4L, "89123451111", "jackdaniels@mail.ru");
        ClientDTO client = new ClientDTO(4L,"Jack", "Daniels", contact);
        String jsonBodyNew = """
            {
              "clientId": 4,
              "name": "Jack",
              "lastName": "Daniels",
              "contact": {
                "id": 4,
                "phone": "89123451111",
                "email": "jackdaniels@mail.ru"
              }
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/client/")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(jsonBodyNew))
                .andExpect(status().isCreated());
        verify(clientService, times(1)).save(client);
        }

    @Test
    void updateClient() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(expectedClient);
        mockMvc.perform(MockMvcRequestBuilders.put("/client/{id}", 1L)
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clientId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jack"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Daniels"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.phone").value("89123451111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contact.email").value("jackdaniels@mail.ru"));
        verify(clientService, times(1)).updateClientDTO(1L, expectedClient);
    }

    @Test
    void removeClient() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/client/{id}", 1L)
                        .param("номер клиента", String.valueOf(1L))
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk());

        verify(clientService, times(1)).deleteById(1L);
    }


}