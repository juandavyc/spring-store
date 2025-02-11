package com.juandavyc.gadgetplus.controllers;

import com.juandavyc.gadgetplus.dto.OrderDTO;
import com.juandavyc.gadgetplus.services.OrdersCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "order")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersCrudService ordersCrudService;


    @GetMapping(path = "{id}")
    public ResponseEntity<OrderDTO> read(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(ordersCrudService.read(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDTO order) {
        var path ="/"+ordersCrudService.create(order);
        return ResponseEntity.created(URI.create(path)).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(ordersCrudService.update(orderDTO, id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestParam(name = "name") String clientName) {
        ordersCrudService.delete(clientName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id")  Long idToDelete) {
        ordersCrudService.deleteById(idToDelete);
        return ResponseEntity.noContent().build();
    }
}
