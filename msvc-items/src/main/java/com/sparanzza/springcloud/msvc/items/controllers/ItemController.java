package com.sparanzza.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.sparanzza.springcloud.msvc.items.models.Item;
import com.sparanzza.springcloud.msvc.items.services.ItemService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ItemController {

    private final ItemService service;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);


    public ItemController(@Qualifier("itemServiceWebClient") ItemService itemService) {
        this.service = itemService;
    }

    @GetMapping()
    public List<Item> list(@RequestParam(name = "foo", required = false) String name,
            @RequestHeader(name = "X-Request-Foo", required = false) String token) {

        logger.info("name = " + name);
        logger.info("token = " + token);
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        Optional<Item> item = service.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.status(404).body(Collections.singletonMap("message", "Item not found"));
    }

}
