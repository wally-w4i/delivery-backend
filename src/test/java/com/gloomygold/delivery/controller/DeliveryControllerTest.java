package com.gloomygold.delivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloomygold.delivery.dto.DeliveryDTO;
import com.gloomygold.delivery.dto.DeliveryDetailDTO;
import com.gloomygold.delivery.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryService deliveryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetDeliveries() throws Exception {
        DeliveryDTO delivery1 = new DeliveryDTO();
        delivery1.setId(1L);
        DeliveryDTO delivery2 = new DeliveryDTO();
        delivery2.setId(2L);
        List<DeliveryDTO> deliveries = Arrays.asList(delivery1, delivery2);

        when(deliveryService.getDeliveries()).thenReturn(deliveries);

        mockMvc.perform(get("/api/deliveries").with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    public void testCreateDelivery() throws Exception {
        DeliveryDTO deliveryToCreate = new DeliveryDTO();
        DeliveryDetailDTO detail = new DeliveryDetailDTO();
        detail.setClientId(1L);
        deliveryToCreate.getDetails().add(detail);

        DeliveryDTO createdDelivery = new DeliveryDTO();
        createdDelivery.setId(1L);
        DeliveryDetailDTO createdDetail = new DeliveryDetailDTO();
        createdDetail.setClientId(1L);
        createdDelivery.getDetails().add(createdDetail);

        when(deliveryService.createDelivery(any(DeliveryDTO.class))).thenReturn(createdDelivery);

        mockMvc.perform(post("/api/deliveries").with(SecurityMockMvcRequestPostProcessors.jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryToCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.details[0].clientId").value(1L));
    }
}