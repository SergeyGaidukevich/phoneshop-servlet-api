package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.PopularProducts;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.PopularProductService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularProductProductServiceImpl implements PopularProductService {

    private static final int START_POPULAR = 1;

    private PopularProductProductServiceImpl() {
    }

    public static PopularProductProductServiceImpl getInstance() {
        return PopularProductProductServiceImpl.InstanceHolder.instance;
    }

    @Override
    public List<Product> getMostPopularProducts(PopularProducts popularProducts) {
        Map<Product, Integer> products = popularProducts.getPopularProducts();
        Map<Product, Integer> sortProducts = products.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        List<Product> productList = new ArrayList<>();
        int maxSizePopularList = 2;
        int index = 0;
        for (Product product : sortProducts.keySet()) {
            if (maxSizePopularList >= index) {
                productList.add(product);
                index++;
            }
        }

        return productList;
    }

    @Override
    public void addProductToPopular(PopularProducts popularProducts, Product viewedProduct) {
        Map<Product, Integer> products = popularProducts.getPopularProducts();
        if (!popularProducts.getPopularProducts().containsKey(viewedProduct)) {
            products.put(viewedProduct, START_POPULAR);
        } else {
            int numberViewed = products.get(viewedProduct) + START_POPULAR;
            products.put(viewedProduct, numberViewed);
        }
    }

    private static class InstanceHolder {
        static PopularProductProductServiceImpl instance = new PopularProductProductServiceImpl();
    }
}
