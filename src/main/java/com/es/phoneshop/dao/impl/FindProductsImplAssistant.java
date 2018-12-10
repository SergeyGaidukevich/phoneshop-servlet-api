package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.FindProductsAssistant;
import com.es.phoneshop.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FindProductsImplAssistant implements FindProductsAssistant {
    private static final String WORDS_SEPARATOR = "\\s";

    private Map<SortProperty, Comparator<Product>> propertyComparators;

    private FindProductsImplAssistant() {
        propertyComparators = new HashMap<>();
        propertyComparators.put(SortProperty.DESCRIPTION, Comparator.comparing(Product::getDescription));
        propertyComparators.put(SortProperty.PRICE, Comparator.comparing(Product::getPrice));
    }

    public static FindProductsAssistant getInstance() {
        return new FindProductsImplAssistant();
    }

    @Override
    public void sortProducts(List<Product> queryProducts, SortProperty sortProperty, SortMode sortMode) {
        Comparator<Product> comparator = propertyComparators.get(sortProperty);
        if (sortMode == SortMode.DESCENDING) {
            comparator = comparator.reversed();
        }
        queryProducts.sort(comparator);
    }

    @Override
    public List<Product> findProductsByDescription(List<Product> products, String searchText) {
        List<Product> foundProductsBySearchText = products.stream()
                .filter(product -> StringUtils.containsIgnoreCase(product.getDescription(), searchText))
                .collect(Collectors.toList());

        String[] searchWords = searchText.split(WORDS_SEPARATOR);
        List<Product> foundProductsByWords = products.stream()
                .filter(product -> doesProductDescriptionContainAnyWord(product.getDescription(), searchWords))
                .collect(Collectors.toList());

        return Stream.of(foundProductsBySearchText, foundProductsByWords)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean doesProductDescriptionContainAnyWord(String description, String[] words) {
        return Arrays.stream(words).anyMatch(word -> StringUtils.containsIgnoreCase(description, word));
    }
}
