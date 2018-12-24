package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.PopularProducts;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.PopularProductService;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularProductsServiceImpl implements PopularProductService {

    private static final int ONE_VIEW = 1;
    private static final int MOST_POPULAR_PRODUCTS_SIZE = 3;

    private PopularProductsServiceImpl() {
    }

    public static PopularProductsServiceImpl getInstance() {
        return PopularProductsServiceImpl.InstanceHolder.instance;
    }

    @Override
    public List<Product> getMostPopularProducts(PopularProducts popularProducts) {
        return sortProductsMapByViewsCount(popularProducts.getPopularProducts())
                .keySet()
                .stream()
                .limit(MOST_POPULAR_PRODUCTS_SIZE)
                .collect(Collectors.toList());
    }

    @Override
    public void increaseProductPopularity(PopularProducts popularProducts, Product product) {
        Map<Product, Integer> products = popularProducts.getPopularProducts();
        int viewsNumber = products.containsKey(product) ? products.get(product) + ONE_VIEW : ONE_VIEW;
        products.put(product, viewsNumber);
    }

    private Map<Product, Integer> sortProductsMapByViewsCount(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private static class InstanceHolder {
        static final PopularProductsServiceImpl instance = new PopularProductsServiceImpl();
    }
}
