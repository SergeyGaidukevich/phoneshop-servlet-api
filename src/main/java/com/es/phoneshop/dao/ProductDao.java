package com.es.phoneshop.dao;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.sorter.SortMode;
import com.es.phoneshop.sorter.SortProperty;

import java.util.List;

public interface ProductDao {
    Product get(Long id);

    Product get(String code);

    List<Product> getAll();

    List<Product> findProducts(String textSearch);

    List<Product> findProducts(SortProperty sortProperty, SortMode sortMode);

    List<Product> findProducts(String textSearch, SortProperty sortProperty, SortMode sortMode);

    void save(Product product);

    void delete(Long id);
}
