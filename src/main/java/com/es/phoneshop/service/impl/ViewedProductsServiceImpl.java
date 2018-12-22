package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;
import com.es.phoneshop.service.ViewedProductsService;
import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ViewedProductsServiceImpl implements ViewedProductsService {

    private ViewedProductsServiceImpl() {
    }

    public static ViewedProductsServiceImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void addProductToViewed(ViewedProducts viewedProducts, Product viewedProduct) {
        CircularFifoQueue<Product> products = viewedProducts.getViewedProducts();
        products.removeIf(viewedProduct::equals);
        products.add(viewedProduct);
    }

    private static class InstanceHolder {
        static final ViewedProductsServiceImpl instance = new ViewedProductsServiceImpl();
    }
}
