package com.es.phoneshop.service;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;

import java.util.List;

public interface ViewedProductsService {
    void addProductsToViewed(ViewedProducts viewedProducts, Product viewedProduct);
}
