package com.es.phoneshop.dao;

import com.es.phoneshop.model.Product;

import java.util.List;

public interface AssistantFindProducts {
    void sortProducts(List<Product> queryProducts, String sortingProperty, String sortMode);

    List<Product> findProductsByDescription(List<Product> products, String searchText);


}
