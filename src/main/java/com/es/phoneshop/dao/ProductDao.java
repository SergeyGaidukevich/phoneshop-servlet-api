package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ArrayListProductDaoException;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id) throws ArrayListProductDaoException;

    List<Product> findProducts(String textSearch, String sortingProperty, String sortMode);

    void save(Product product);

    void delete(Long id);
}
