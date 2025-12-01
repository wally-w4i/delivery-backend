package com.gloomygold.delivery.service;

import com.gloomygold.delivery.dto.DeliveryDTO;
import com.gloomygold.delivery.dto.DeliveryDetailDTO;
import com.gloomygold.delivery.mapper.Mapper;
import com.gloomygold.delivery.model.Client;
import com.gloomygold.delivery.model.Delivery;
import com.gloomygold.delivery.model.DeliveryDetail;
import com.gloomygold.delivery.repository.ClientRepository;
import com.gloomygold.delivery.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService{

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<DeliveryDTO> getDeliveries() {
        return deliveryRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {

        if (deliveryDTO == null) throw new RuntimeException("DeliveryDTO is null");
        if (deliveryDTO.getDetail() == null || deliveryDTO.getDetail().isEmpty()) throw new RuntimeException("Must include at least one client");

        Delivery created = Delivery.builder()
                .date(LocalDate.now())
                .status(deliveryDTO.getStatus())
                .build();

        List<DeliveryDetail> details = new ArrayList<>();

        for (DeliveryDetailDTO detDTO: deliveryDTO.getDetail()) {
            Client client = clientRepository.findById(detDTO.getClientId()).orElse(null);
            if (client == null)
                throw new RuntimeException("Client not found: " + detDTO.getClientId());

            DeliveryDetail deliveryDetail = new DeliveryDetail();
            deliveryDetail.setClient(client);
            deliveryDetail.setDelivery(created);

            details.add(deliveryDetail);
        }

        created.setDetail(details);

        return Mapper.toDTO(deliveryRepository.save(created));
    }

    @Override
    public DeliveryDTO updateDelivery(Long id, DeliveryDTO d) {
        return null;
    }

    @Override
    public void removeDelivery(Long id) {

    }
}
