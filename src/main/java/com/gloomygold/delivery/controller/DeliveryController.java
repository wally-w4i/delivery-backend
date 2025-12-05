package com.gloomygold.delivery.controller;

import com.gloomygold.delivery.dto.ClientDTO;
import com.gloomygold.delivery.dto.DeliveryDTO;
import com.gloomygold.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/deliveries")
public class DeliveryController {

  @Autowired
  private DeliveryService service;

  @GetMapping
  public ResponseEntity<List<DeliveryDTO>> getDeliveries() {
    return ResponseEntity.ok(service.getDeliveries());
  }

  @PostMapping
  public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody DeliveryDTO d) {
    DeliveryDTO created = service.createDelivery(d);
    return ResponseEntity.created(URI.create("/api/deliveries/"+created.getId())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DeliveryDTO> updateClient(@PathVariable Long id, @RequestBody DeliveryDTO d) {
    return ResponseEntity.ok(service.updateDelivery(id, d));
  }

}
