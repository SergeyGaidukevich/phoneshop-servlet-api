package com.es.phoneshop.finder.impl;

import com.es.phoneshop.finder.ProductFinder;
import com.es.phoneshop.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductFinderImpl implements ProductFinder {
    private static final String WORDS_SEPARATOR = "\\s";

    @Override
    public List<Product> findProductsByDescription(List<Product> products, String searchText) {
        List<Product> foundProductsBySearchText = findProductsWithDescription(products, searchText);

        String[] searchWords = searchText.split(WORDS_SEPARATOR);
        List<Product> foundProductsByWords = findProductsWhereDescriptionContainsWords(products, searchWords);

        return Stream.of(foundProductsBySearchText, foundProductsByWords)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Product> findProductsWithDescription(List<Product> products, String description) {
        return products.stream()
                .filter(product -> StringUtils.containsIgnoreCase(product.getDescription(), description))
                .collect(Collectors.toList());
    }

    private List<Product> findProductsWhereDescriptionContainsWords(List<Product> products, String[] words) {
        return products.stream()
                .filter(product -> doesProductDescriptionContainAnyWord(product.getDescription(), words))
                .collect(Collectors.toList());
    }

    private boolean doesProductDescriptionContainAnyWord(String description, String[] words) {
        return Arrays.stream(words).anyMatch(word -> StringUtils.containsIgnoreCase(description, word));
    }
}
