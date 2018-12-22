package com.es.phoneshop.dao;

import com.es.phoneshop.model.Order;

public interface OrderDao {
    Order getOrder(Long id);

    void save(Order product);

    void delete(Long id);
}
