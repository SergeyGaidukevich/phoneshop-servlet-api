package com.es.phoneshop.dao;

import com.es.phoneshop.model.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAll();

    Order get(Long id);

    void save(Order order);

    void delete(Long id);
}
