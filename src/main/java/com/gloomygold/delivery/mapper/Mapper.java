package com.gloomygold.delivery.mapper;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.dto.DeliveryDTO;
import com.gloomygold.delivery.dto.DeliveryDetailDTO;
import com.gloomygold.delivery.dto.GpsPositionDTO;
import com.gloomygold.delivery.model.Client;
import com.gloomygold.delivery.model.Delivery;
import com.gloomygold.delivery.model.DeliveryDetail;
import com.gloomygold.delivery.model.GpsPosition;

public class Mapper {
    public static ClientDTO toDTO(Client c) {
        if (c == null) return null;

        return ClientDTO.builder()
                .id(c.getId())
                .description(c.getDescription())
                .address(c.getAddress())
                .gpsPosition(toDTO(c.getGpsPosition()))
                .build();
    }

    public static GpsPositionDTO toDTO(GpsPosition gps) {
        if (gps == null) return null;

        return GpsPositionDTO.builder()
                .latitude(gps.getLatitude())
                .longitude(gps.getLongitude())
                .build();
    }

    public static DeliveryDTO toDTO(Delivery d) {
        if (d == null) return null;

        return DeliveryDTO.builder()
                .id(d.getId())
                .date(d.getDate())
                .status(d.getStatus())
                .build();
    }

}
