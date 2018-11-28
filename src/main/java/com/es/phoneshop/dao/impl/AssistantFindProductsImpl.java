package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.AssistantFindProducts;
import com.es.phoneshop.exception.ArrayListProductDaoException;
import com.es.phoneshop.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssistantFindProductsImpl implements AssistantFindProducts {
    private static final String WORDS_SEPARATOR = "\\s";
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private Map<String, Comparator<Product>> sortPropertyToComparatorMap;

    private AssistantFindProductsImpl() {
        sortPropertyToComparatorMap = new HashMap<>();
        String description = SortingProperty.DESCRIPTION.toString().toLowerCase();
        String price = SortingProperty.PRICE.toString().toLowerCase();

        sortPropertyToComparatorMap.put(description, Comparator.comparing(Product::getDescription));
        sortPropertyToComparatorMap.put(price, Comparator.comparing(Product::getPrice));
    }

    static AssistantFindProducts getInstance(){
        return new AssistantFindProductsImpl();
    }

    @Override
    public void sortProducts(List<Product> queryProducts, String sortingProperty, String sortMode) {
        if (StringUtils.isNotBlank(sortingProperty) && StringUtils.isNotBlank(sortMode)) {
            Comparator<Product> comparator = sortPropertyToComparatorMap.get(sortingProperty);

            if (comparator == null){
                throw new ArrayListProductDaoException("Sorting property failed");
            } else if (DESC.equals(sortMode)) {
                comparator = comparator.reversed();
            } else if (!ASC.equals(sortMode)) {
                throw new ArrayListProductDaoException("Sort mode failed");
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

            return Stream
                    .of(foundProductsBySearchText, foundProductsByWords)
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return products;
    }

    private boolean doesProductDescriptionContainAnyWord(String description, String[] words) {
        return Arrays.stream(words)
                .anyMatch(word -> StringUtils.containsIgnoreCase(description, word));
    }
}
