package com.sparanzza.msvc.products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparanzza.msvc.products.entities.Product;
import com.sparanzza.msvc.products.repositories.ProductRepository;
import org.springframework.core.env.Environment;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final Environment enviroment;

    public ProductServiceImpl(ProductRepository repository, Environment enviroment) {
        this.repository = repository;
        this.enviroment = enviroment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return ((List<Product>) repository.findAll()).stream().map(product -> {
            product.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
            return product;
        }).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(enviroment.getProperty("local.server.port")));
            return product;
        });
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
