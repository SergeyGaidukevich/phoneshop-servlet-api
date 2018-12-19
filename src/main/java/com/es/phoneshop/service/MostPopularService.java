package com.es.phoneshop.service;

import com.es.phoneshop.model.MostPopularProducts;
import com.es.phoneshop.model.Product;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface MostPopularService {
    List<Product> sortPopularProducts (Map<Product, Integer> products);
    void addProductsToPopular(MostPopularProducts popularProducts, Product viewedProduct);
}
