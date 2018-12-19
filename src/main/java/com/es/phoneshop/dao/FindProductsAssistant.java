package com.es.phoneshop.dao;

import com.es.phoneshop.dao.sortParameters.SortMode;
import com.es.phoneshop.dao.sortParameters.SortProperty;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface FindProductsAssistant {
    void sortProducts(List<Product> queryProducts, SortProperty sortProperty, SortMode sortMode);

    List<Product> findProductsByDescription(List<Product> products, String searchText);


}
