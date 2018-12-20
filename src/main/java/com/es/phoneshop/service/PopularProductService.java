package com.es.phoneshop.service;

import com.es.phoneshop.model.MostPopularProducts;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface PopularProductService {
    List<Product> getMostPopularProducts(MostPopularProducts popularProducts);

    void addProductsToPopular(MostPopularProducts popularProducts, Product viewedProduct);
}
