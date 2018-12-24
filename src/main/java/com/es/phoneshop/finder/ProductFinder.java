package com.es.phoneshop.finder;

import com.es.phoneshop.model.Product;

import java.util.List;

public interface ProductFinder {
    List<Product> findProductsByDescription(List<Product> products, String searchText);
}
