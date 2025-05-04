package com.sparanzza.springcloud.msvc.items.clients;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sparanzza.libs.msvc.commons.entities.Product;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping
    ResponseEntity<List<Product>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<Optional<Product>> findById(@PathVariable Long id);

    @PostMapping
    public Product create(@RequestBody Product product);

    @PutMapping("/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id);
}