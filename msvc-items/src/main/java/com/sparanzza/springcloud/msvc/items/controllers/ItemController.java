package com.sparanzza.springcloud.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.sparanzza.springcloud.msvc.items.models.Item;
import com.sparanzza.springcloud.msvc.items.services.ItemService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RefreshScope
@RestController
public class ItemController {

    private final ItemService service;
    private final CircuitBreakerFactory cbFactory;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Value("${configuracion.texto}")
    private String text;


    @Autowired
    private Environment env;

    public ItemController(@Qualifier("itemServiceWebClient") ItemService itemService, CircuitBreakerFactory cbFactory) {
        this.service = itemService;
        this.cbFactory = cbFactory;
    }

    @GetMapping("/config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> map = new HashMap <>();
        map.put("text", text);
        map.put("port", port);
        logger.info(port);
        logger.info(text);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            map.put("author.name", env.getProperty("configuracion.autor.nombre"));
            map.put("author.email", env.getProperty("configuracion.autor.apellido"));
        }

        return ResponseEntity.ok(map);
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
