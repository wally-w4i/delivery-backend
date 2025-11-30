package com.gloomygold.delivery.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private String description;
    private String address;
    private GpsPositionDTO gpsPosition;
}
