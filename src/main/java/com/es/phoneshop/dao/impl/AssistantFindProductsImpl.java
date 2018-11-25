package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.AssistantFindProducts;
import com.es.phoneshop.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssistantFindProductsImpl implements AssistantFindProducts {
    public static final String WORDS_SEPARATOR = "\\s";
    private Map<String, Comparator<Product>> sortPropertyToComparatorMap;

    public AssistantFindProductsImpl() {
        sortPropertyToComparatorMap = new HashMap<>();
        String DESCRIPTION = SortingProperty.DESCRIPTION.toString().toLowerCase();
        String PRICE = SortingProperty.PRICE.toString().toLowerCase();

        sortPropertyToComparatorMap.put(DESCRIPTION, Comparator.comparing(Product::getDescription));
        sortPropertyToComparatorMap.put(PRICE, Comparator.comparing(Product::getPrice));
    }

    @Override
    public void sortProducts(List<Product> queryProducts, String sortingProperty, String sortMode) {
        if (StringUtils.isNotBlank(sortingProperty) && StringUtils.isNotBlank(sortMode)) {
            Comparator<Product> comparator = sortPropertyToComparatorMap.get(sortingProperty);

            if ("desc".equals(sortMode) && comparator != null) {
                comparator = comparator.reversed();
            }
            queryProducts.sort(comparator);
        }
    }

    @Override
    public List<Product> findProductsByDescription(List<Product> products, String searchText) {
        if (StringUtils.isNoneBlank(searchText)) {
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
        return products;
    }

    private boolean doesProductDescriptionContainAnyWord(String description, String[] words) {
        return Arrays.stream(words).anyMatch(word -> StringUtils.containsIgnoreCase(description, word));
    }
}
