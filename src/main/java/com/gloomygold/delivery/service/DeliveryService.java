package com.gloomygold.delivery.service;

import com.gloomygold.delivery.dto.DeliveryDTO;

import java.util.List;

public interface DeliveryService {
    List<DeliveryDTO> getDeliveries();
    DeliveryDTO createDelivery(DeliveryDTO d);
    DeliveryDTO updateDelivery(Long id, DeliveryDTO d);
    void removeDelivery(Long id);
}
