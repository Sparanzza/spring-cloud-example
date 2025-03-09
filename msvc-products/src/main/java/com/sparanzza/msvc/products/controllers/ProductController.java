package com.sparanzza.msvc.products.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sparanzza.msvc.products.entities.Product;
import com.sparanzza.msvc.products.services.ProductService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(this.productService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable long id) {
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

}
