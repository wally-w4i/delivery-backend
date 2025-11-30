package com.gloomygold.delivery.mapper;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.model.Client;

public class Mapper {
    public static ClientDTO toDTO(Client c) {
        if (c == null) return null;

        return ClientDTO.builder()
                .id(c.getId())
                .description(c.getDescription())
                .address(c.getAddress())
                .build();
    }
}
