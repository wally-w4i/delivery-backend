package com.gloomygold.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllClients_shouldReturnListOfClients() throws Exception {
        ClientDTO client1 = new ClientDTO();
        client1.setId(1L);
        client1.setDescription("Client A");
        ClientDTO client2 = new ClientDTO();
        client2.setId(2L);
        client2.setDescription("Client B");
        List<ClientDTO> allClients = Arrays.asList(client1, client2);

        when(clientService.getClients()).thenReturn(allClients);

        mockMvc.perform(get("/api/clients").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("Client A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].description").value("Client B"));
    }

    @Test
    void createClient_shouldReturnCreatedClient() throws Exception {
        ClientDTO clientToCreate = new ClientDTO();
        clientToCreate.setDescription("New Client");
        clientToCreate.setAddress("123 Main St");

        ClientDTO createdClient = new ClientDTO();
        createdClient.setId(3L);
        createdClient.setDescription("New Client");
        createdClient.setAddress("123 Main St");

        when(clientService.createClient(any(ClientDTO.class))).thenReturn(createdClient);

        mockMvc.perform(post("/api/clients").with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientToCreate)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/clients/3"))
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.description").value("New Client"))
                .andExpect(jsonPath("$.address").value("123 Main St"));
    }

    @Test
    void updateClient_shouldReturnUpdatedClient() throws Exception {
        Long clientId = 1L;
        ClientDTO updatedClientDTO = new ClientDTO();
        updatedClientDTO.setId(clientId);
        updatedClientDTO.setDescription("Updated Client Name");
        updatedClientDTO.setAddress("Updated Address");

        when(clientService.updateClient(clientId, updatedClientDTO)).thenReturn(updatedClientDTO);

        mockMvc.perform(put("/api/clients/{id}", clientId).with(SecurityMockMvcRequestPostProcessors.jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClientDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClient_shouldReturnNoContent() throws Exception {
        Long clientId = 1L;
        doNothing().when(clientService).remove(clientId);
        mockMvc.perform(delete("/api/clients/{id}", clientId).with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isNoContent());
    }
}
