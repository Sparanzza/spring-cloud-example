package com.sparanzza.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sparanzza.libs.msvc.commons.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
