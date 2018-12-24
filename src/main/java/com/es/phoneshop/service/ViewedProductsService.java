package com.es.phoneshop.service;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;

public interface ViewedProductsService {
    void addProductToViewed(ViewedProducts viewedProducts, Product product);
}
