package com.sparanzza.springcloud.msvc.items.clients;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sparanzza.springcloud.msvc.items.models.Product;

@FeignClient(name = "msvc-products", url = "http://localhost:8001")
public interface ProductFeignClient {

    @GetMapping
    ResponseEntity<List<Product>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<Optional<Product>> findById(@PathVariable Long id);
}