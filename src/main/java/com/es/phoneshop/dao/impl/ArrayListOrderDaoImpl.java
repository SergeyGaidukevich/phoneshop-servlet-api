package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.exception.OrderAlreadyExistsException;
import com.es.phoneshop.dao.exception.OrderNotFoundException;
import com.es.phoneshop.model.Order;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class ArrayListOrderDaoImpl implements OrderDao {
    private final List<Order> orders = new CopyOnWriteArrayList<>();
    private final AtomicLong currentId = new AtomicLong(1);

    private ArrayListOrderDaoImpl() {

    }

    public ArrayListOrderDaoImpl(List<Order> orders) {
        this.orders.addAll(orders);
        this.orders.forEach(this::populateId);
    }

    public static ArrayListOrderDaoImpl getInstance() {
        return ArrayListOrderDaoImpl.InstanceHolder.instance;
    }

    @Override
    public List<Order> getAll() {
        return orders;
    }

    @Override
    public Order get(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order with id = %d not found", id)));
    }

    @Override
    public void save(Order order) {
        if (doesOrderExist(order)) {
            throw new OrderAlreadyExistsException("Such order already exists.");
        }

        populateId(order);
        orders.add(order);
    }

    @Override
    public void delete(Long id) {
        orders.removeIf(p -> p.getId().equals(id));
    }

    private void populateId(Order order) {
        order.setId(currentId.getAndIncrement());
    }

    private boolean doesOrderExist(Order order) {
        return orders.stream().anyMatch(order::equals);
    }

    private static class InstanceHolder {
        static final ArrayListOrderDaoImpl instance = new ArrayListOrderDaoImpl();
    }
}
