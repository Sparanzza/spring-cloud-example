package com.sparanzza.springcloud.msvc.items.models;

import java.time.LocalDate;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private LocalDate createAt;

    private int port;

    public Product() {
    }

    public Product(Long id, String name, Integer price, LocalDate createAt, int port) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createAt = createAt;
        this.port = port;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
