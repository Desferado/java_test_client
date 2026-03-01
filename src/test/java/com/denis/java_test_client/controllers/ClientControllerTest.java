package com.denis.java_test_client.controllers;

import com.denis.java_test_client.models.Client;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        // Заготовленные объекты для тестов
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1,"John", "Doe"));
        clients.add(new Client(2, "Jane", "Smith"));

        // Моделируем поведение service.findAllClient(), возвращающего подготовленный список клиентов
        when(clientService.findAllClient()).thenReturn(clients);
    }

    @Test
    void shouldReturnListOfClientsWhenGettingAllClients() throws Exception {
        // Выполнение GET-запроса к endpoint "/get-clients"
        mockMvc.perform(MockMvcRequestBuilders.get("/client/get-clients"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())          // Проверка статуса HTTP 200
                .andExpect(MockMvcResultMatchers.content().contentType(String.valueOf(MediaType.APPLICATION_JSON))) // Проверка MIME-типа
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())  // Проверка, что ответ — массив
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("John")) // Проверка первого элемента
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Jane")); // Проверка второго элемента
    }
}