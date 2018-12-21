package com.es.phoneshop.service;

import com.es.phoneshop.model.PopularProducts;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface PopularProductService {
    List<Product> getMostPopularProducts(PopularProducts popularProducts);

    void increaseProductPopularity(PopularProducts popularProducts, Product viewedProduct);
}
