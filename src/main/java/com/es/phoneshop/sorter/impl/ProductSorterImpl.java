package com.es.phoneshop.sorter.impl;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.sorter.ProductSorter;
import com.es.phoneshop.sorter.SortMode;
import com.es.phoneshop.sorter.SortProperty;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSorterImpl implements ProductSorter {

    private final Map<SortProperty, Comparator<Product>> propertyComparators;

    public ProductSorterImpl() {
        propertyComparators = new HashMap<>();
        propertyComparators.put(SortProperty.DESCRIPTION, Comparator.comparing(Product::getDescription));
        propertyComparators.put(SortProperty.PRICE, Comparator.comparing(Product::getPrice));
    }

    @Override
    public void sort(List<Product> queryProducts, SortProperty sortProperty, SortMode sortMode) {
        Comparator<Product> comparator = propertyComparators.get(sortProperty);
        if (sortMode == SortMode.DESCENDING) {
            comparator = comparator.reversed();
        }
        queryProducts.sort(comparator);
    }
}
