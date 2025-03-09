package com.sparanzza.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.sparanzza.springcloud.msvc.items.clients.ProductFeignClient;
import com.sparanzza.springcloud.msvc.items.models.Item;

import feign.FeignException;

@Service
public class ItemServiceImpl implements ItemService {

    private final ProductFeignClient client;
    private final Random random = new Random();

    public ItemServiceImpl(ProductFeignClient client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        return Optional.ofNullable(client.findAll().getBody())
                .orElseThrow(() -> new RuntimeException("Response body is null"))
                .stream().map(p -> new Item(p, random.nextInt(9) + 1)).toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            var productResponse = client.findById(id).getBody();
            if (productResponse != null && productResponse.isPresent()) {
                return Optional.of(new Item(productResponse.get(), random.nextInt(9) + 1));
            }

        } catch (FeignException e) {
            if (e.status() == 404) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
