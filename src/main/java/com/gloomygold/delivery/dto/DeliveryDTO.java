package com.gloomygold.delivery.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryDTO {
    private Long id;
    private LocalDate date;
    private String status;

    private List<DeliveryDetailDTO> details = new ArrayList<>();
}
