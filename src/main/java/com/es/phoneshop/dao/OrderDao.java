package com.es.phoneshop.dao;

import com.es.phoneshop.model.Order;

public interface OrderDao {
    Order getProduct(Long id);
    void save(Order product);
}
