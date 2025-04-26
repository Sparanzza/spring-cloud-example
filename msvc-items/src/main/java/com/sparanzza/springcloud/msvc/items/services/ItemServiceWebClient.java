package com.sparanzza.springcloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sparanzza.springcloud.msvc.items.models.Item;
import com.sparanzza.springcloud.msvc.items.models.Product;

// @Primary
@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder client;
    private final Random random = new Random();

    public ItemServiceWebClient(WebClient.Builder client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        return this.client.build().get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class)
                .map(p -> new Item(p, random.nextInt(9) + 1))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {        
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        return this.client.build().get()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .map(p -> new Item(p, random.nextInt(9) + 1))
                .map(Optional::of)
                .block();

    }

    @Override
    public Product save(Product product) {
        return this.client.build().post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public Product update(Product product, long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        return this.client.build().put()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)

                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public void deleteById(Long id) {
        this.client.build().delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
