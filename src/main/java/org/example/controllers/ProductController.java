package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Product;
import org.example.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mvc")
public class ProductController {

    private ProductService productService;

    private final ObjectMapper mapper;

    private static final String mess = "Some problems!";

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;

        this.mapper = new ObjectMapper();
    }

    @GetMapping("/products")
    public ResponseEntity<String> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(productService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Product product = productService.getById(uuid);
            String jsonString = mapper.writeValueAsString(product);
            return ResponseEntity.status(HttpStatus.OK).body(jsonString);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper
                    .writeValueAsString(productService.delete(UUID.fromString(id))));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody String product) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(productService
                    .save(mapper.readValue(product, Product.class))));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

    @PatchMapping("/change")
    public ResponseEntity<String> change(@RequestBody String product) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(productService
                    .save(mapper.readValue(product, Product.class))));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mess);
        }
    }

}
