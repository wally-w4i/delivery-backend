package com.gloomygold.delivery.service;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.dto.GpsPositionDTO;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();
    ClientDTO createClient(ClientDTO c);
    ClientDTO updateClient(Long id, ClientDTO c);
    void remove(Long id);

    GpsPositionDTO updateGpsPosition(Long id, GpsPositionDTO gps);
}
