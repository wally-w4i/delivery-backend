package com.gloomygold.delivery.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GpsPositionDTO {
    private Double latitude;
    private Double longitude;
}
