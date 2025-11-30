package com.gloomygold.delivery.mapper;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.dto.GpsPositionDTO;
import com.gloomygold.delivery.model.Client;
import com.gloomygold.delivery.model.GpsPosition;

public class Mapper {
    public static ClientDTO toDTO(Client c) {
        if (c == null) return null;

        return ClientDTO.builder()
                .id(c.getId())
                .description(c.getDescription())
                .address(c.getAddress())
                .build();
    }

    public static GpsPositionDTO toDTO(GpsPosition gps) {
        if (gps == null) return null;

        return GpsPositionDTO.builder()
                .id(gps.getId())
                .latitude(gps.getLatitude())
                .longitude(gps.getLongitude())
                .build();
    }
}
