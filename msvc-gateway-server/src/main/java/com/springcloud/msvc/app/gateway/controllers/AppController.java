package com.springcloud.msvc.app.gateway.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AppController {

    @GetMapping("/authorized")
    public Map<String, String> autorizedMap(@RequestParam String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        return map;
    }


    @GetMapping("/logout")
    public Map<String, String> logout() {
        return Collections.singletonMap("logout", "ok");
    }

}
