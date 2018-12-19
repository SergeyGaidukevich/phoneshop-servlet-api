package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.MostPopularProducts;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.MostPopularService;

import java.util.ArrayList;
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
        products.putAll(products.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new)));

        List<Product> productList = new ArrayList<>();
        int sizePopularList = 3;
        int index = 0;
        for (Product product : products.keySet()) {
            if (sizePopularList >= index) {
                productList.add(product);
                index++;
            }
        }

        return productList;
    }

    @Override
    public void addProductsToPopular(MostPopularProducts popularProducts, Product viewedProduct) {
        Map<Product, Integer> products = popularProducts.getPopularProducts();

        int numberViewed = products.get(viewedProduct) + START_POPULAR;
        products.put(viewedProduct, numberViewed);
    }

    private static class InstanceHolder {
        static MostPopularServiceImpl instance = new MostPopularServiceImpl();
    }
}
