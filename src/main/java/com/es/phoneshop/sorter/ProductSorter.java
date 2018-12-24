package com.es.phoneshop.sorter;

import com.es.phoneshop.model.Product;

import java.util.List;

public interface ProductSorter {
    void sort(List<Product> queryProducts, SortProperty sortProperty, SortMode sortMode);
}
