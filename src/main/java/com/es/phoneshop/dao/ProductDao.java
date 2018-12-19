package com.es.phoneshop.dao;

import com.es.phoneshop.dao.sortParameters.SortMode;
import com.es.phoneshop.dao.sortParameters.SortProperty;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);

    List<Product> getAll();

    List<Product> findProducts(String textSearch);

    List<Product> findProducts(SortProperty sortProperty, SortMode sortMode);

    List<Product> findProducts(String textSearch, SortProperty sortProperty, SortMode sortMode);

    void save(Product product);

    void delete(Long id);
}
