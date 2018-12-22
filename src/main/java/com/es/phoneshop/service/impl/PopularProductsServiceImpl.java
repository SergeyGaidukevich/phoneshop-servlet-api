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

public class PopularProductsServiceImpl implements PopularProductService {

    private static final int ONE_VIEW = 1;

    private PopularProductsServiceImpl() {
    }

    public static PopularProductsServiceImpl getInstance() {
        return PopularProductsServiceImpl.InstanceHolder.instance;
    }

    @Override
    public List<Product> getMostPopularProducts(PopularProducts popularProducts) {
        Map<Product, Integer> sortedPopularProducts = sortProductsMapByNumberViews(popularProducts.getPopularProducts());
        List<Product> mostPopularProducts = new ArrayList<>();
        int maxSizeMostPopularProductsList = 3;
        int index = 0;

        for (Product product : sortedPopularProducts.keySet()) {
            if (maxSizeMostPopularProductsList > index) {
                mostPopularProducts.add(product);
                index++;
            }
        }

        return mostPopularProducts;
    }

    @Override
    public void increaseProductPopularity(PopularProducts popularProducts, Product viewedProduct) {
        Map<Product, Integer> products = popularProducts.getPopularProducts();

        if (!popularProducts.getPopularProducts().containsKey(viewedProduct)) {
            products.put(viewedProduct, ONE_VIEW);
        } else {
            int numberViews = products.get(viewedProduct) + ONE_VIEW;
            products.put(viewedProduct, numberViews);
        }
    }

    private Map<Product, Integer> sortProductsMapByNumberViews(Map<Product, Integer> products) {
        return products.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private static class InstanceHolder {
        static final PopularProductsServiceImpl instance = new PopularProductsServiceImpl();
    }
}
