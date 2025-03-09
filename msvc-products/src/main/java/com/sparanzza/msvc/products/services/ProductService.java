package com.sparanzza.msvc.products.services;

import java.util.List;
import java.util.Optional;

import com.sparanzza.msvc.products.entities.Product;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);

}
