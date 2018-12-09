package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;
import com.es.phoneshop.service.ViewedProductsService;

import java.util.List;

public class ViewedProductsServiceImpl implements ViewedProductsService {
    private static final int LAST_VIEWED_PRODUCT_INDEX = 0;
    private final static int SIZE_OF_VIEWED_LIST = 3;

    private ViewedProductsServiceImpl() {
    }

    public static ViewedProductsServiceImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void addProductsToViewed(ViewedProducts viewedProducts, Product viewedProduct) {
        List<Product> products = viewedProducts.getViewedProducts();
        boolean doesViewedProductExist = products.removeIf(viewedProduct::equals);
        if (!doesViewedProductExist && products.size() == SIZE_OF_VIEWED_LIST) {
            products.remove(products.size() - 1);
        }
        products.add(LAST_VIEWED_PRODUCT_INDEX, viewedProduct);
    }

    private static class InstanceHolder {
        static ViewedProductsServiceImpl instance = new ViewedProductsServiceImpl();
    }
}
