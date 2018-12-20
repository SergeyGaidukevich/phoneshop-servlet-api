package com.es.phoneshop.model;

import java.util.HashMap;
import java.util.Map;

public class MostPopularProducts {
    private Map<Product, Integer> popularProducts = new HashMap<>();

    public Map<Product, Integer> getPopularProducts() {
        return popularProducts;
    }

    public void setPopularProducts(Map<Product, Integer> popularProducts) {
        this.popularProducts = popularProducts;
    }
}
