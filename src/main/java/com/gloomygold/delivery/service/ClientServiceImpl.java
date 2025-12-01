package com.gloomygold.delivery.service;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.dto.GpsPositionDTO;
import com.gloomygold.delivery.mapper.Mapper;
import com.gloomygold.delivery.model.Client;
import com.gloomygold.delivery.model.GpsPosition;
import com.gloomygold.delivery.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository repo;

    @Override
    public List<ClientDTO> getClients() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ClientDTO createClient(ClientDTO c) {
        Client created = Client.builder()
                .description(c.getDescription())
                .address(c.getAddress())
                .gpsPosition(GpsPosition.builder()
                        .latitude(c.getGpsPosition().getLatitude())
                        .longitude(c.getGpsPosition().getLongitude())
                        .build())
                .build();

        return Mapper.toDTO(repo.save(created));
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO c) {
        Client saved = repo.findById(id).orElseThrow(() -> new RuntimeException("No client found to update"));

        saved.setAddress(c.getAddress());
        saved.setDescription(c.getDescription());
        saved.setAddress(c.getAddress());
        saved.setGpsPosition(GpsPosition.builder()
                .latitude(c.getGpsPosition().getLatitude())
                .longitude(c.getGpsPosition().getLongitude())
                .build());
        return Mapper.toDTO(repo.save(saved));
    }

    @Override
    public void remove(Long id) {
        if (!repo.existsById(id))
            throw new RuntimeException("No client found to delete");

        repo.deleteById(id);
    }

}
