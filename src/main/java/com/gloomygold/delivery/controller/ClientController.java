package com.gloomygold.delivery.controller;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.dto.GpsPositionDTO;
import com.gloomygold.delivery.model.GpsPosition;
import com.gloomygold.delivery.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@Controller
@RequestMapping("/api/clients")
public class ClientController {

  @Autowired
  private ClientService service;

  @GetMapping
  public ResponseEntity<List<ClientDTO>> getAllClients() {
    return ResponseEntity.ok(service.getClients());
  }

  @PostMapping
  public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO c) {
    ClientDTO created = service.createClient(c);
    return ResponseEntity.created(URI.create("/api/clients")).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO c) {
    return ResponseEntity.ok(service.updateClient(id, c));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }

}
