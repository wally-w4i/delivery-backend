package com.gloomygold.delivery.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDetailDTO {
    private Long id;
    private Long clientId;
    private String status;
}
