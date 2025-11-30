package com.gloomygold.delivery.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String address;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private GpsPosition gpsPosition;
}
