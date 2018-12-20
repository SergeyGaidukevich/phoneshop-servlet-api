package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.MostPopularProducts;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.MostPopularService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MostPopularServiceImpl implements MostPopularService {

    private static final int START_POPULAR = 1;

    private MostPopularServiceImpl() {
    }

    public static MostPopularServiceImpl getInstance() {
        return MostPopularServiceImpl.InstanceHolder.instance;
    }

    @Override
    public List<Product> sortPopularProducts(Map<Product, Integer> products) {
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
    public void addProductsToPopular(MostPopularProducts popularProducts, Product viewedProduct) {
        Map<Product, Integer> products = popularProducts.getPopularProducts();
        if (!popularProducts.getPopularProducts().containsKey(viewedProduct)) {
            products.put(viewedProduct, START_POPULAR);
        } else {
            int numberViewed = products.get(viewedProduct) + START_POPULAR;
            products.put(viewedProduct, numberViewed);
        }
    }

    private static class InstanceHolder {
        static MostPopularServiceImpl instance = new MostPopularServiceImpl();
    }
}
