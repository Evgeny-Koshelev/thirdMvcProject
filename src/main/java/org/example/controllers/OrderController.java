package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.entities.Orders;
import org.example.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.UUID;

@RestController
@RequestMapping("/mvc/ords/")
public class OrderController {

    private OrderService orderService;

    private final ObjectMapper mapper;

    private static final String mess = "Some problems!";

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Orders orders = orderService.getById(uuid);
            String jsonString = mapper.writeValueAsString(orders);
            return ResponseEntity.status(HttpStatus.OK).body(jsonString);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String order) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(orderService
                    .save(mapper.readValue(order, Orders.class))));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

}
