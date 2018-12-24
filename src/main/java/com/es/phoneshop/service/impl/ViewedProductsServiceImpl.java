package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;
import com.es.phoneshop.service.ViewedProductsService;

import java.util.Queue;

public class ViewedProductsServiceImpl implements ViewedProductsService {

    private ViewedProductsServiceImpl() {
    }

    public static ViewedProductsServiceImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void addProductToViewed(ViewedProducts viewedProducts, Product product) {
        Queue<Product> products = viewedProducts.getViewedProducts();
        products.removeIf(product::equals);
        products.add(product);
    }

    private static class InstanceHolder {
        static final ViewedProductsServiceImpl instance = new ViewedProductsServiceImpl();
    }
}
