package com.es.phoneshop.service;

import com.es.phoneshop.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class FindProductsService {
    private Map<String, Comparator<Product>> map;

    public FindProductsService(){
        map = new HashMap<>();
    }

    public List<Product> sortProduct(List<Product> products, String sortingProperty, String sortMode) {
        map.put("description", Comparator.comparing(Product::getDescription));
        map.put("price", Comparator.comparing(Product::getPrice));

        Comparator<Product> comparator = map.get(sortingProperty);

        if ("desc".equals(sortMode)){
            comparator = comparator.reversed();
        }
        products.sort(comparator);

        return products;
    }

    public List<Product> findSearchProducts(List<Product> productsDao, String textSearch) {
        textSearch = textSearch.toLowerCase();
        String finalTextSearch = textSearch;
        String[] wordsSearch = textSearch.split("\\s");
        List<Product> queryProducts = new ArrayList<>(productsDao);

        queryProducts = queryProducts.stream()
                .filter(product -> product.getDescription().toLowerCase().contains(finalTextSearch))
                .collect(Collectors.toList());

        for (String word: wordsSearch){
            queryProducts.addAll(productsDao.stream()
                    .filter(product -> product.getDescription().toLowerCase().contains(word))
                    .collect(Collectors.toList()));
        }

        return queryProducts.stream().distinct().collect(Collectors.toList());
    }
}
