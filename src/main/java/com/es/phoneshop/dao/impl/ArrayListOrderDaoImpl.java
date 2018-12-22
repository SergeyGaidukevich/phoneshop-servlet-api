package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.Order;

import java.util.List;
import java.util.NoSuchElementException;
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
    public Order getOrder(Long id) {
        return orders.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(Order order) {
        populateId(order);
        Long id = order.getId();
        if (productExist(id)) {
            throw new IllegalArgumentException("Order with id" + id + "already exists.");
        } else {
            orders.add(order);
        }
    }

    @Override
    public void delete(Long id) {
        if (!orders.removeIf(p -> p.getId().equals(id))) {
            throw new IllegalArgumentException("Order not exists with such id = " + id);
        }
    }

    private void populateId(Order order) {
        order.setId(currentId.getAndIncrement());
    }

    private boolean productExist(Long id) {
        return orders.stream().anyMatch(product -> product.getId().equals(id));
    }

    private static class InstanceHolder {
        static final ArrayListOrderDaoImpl instance = new ArrayListOrderDaoImpl();
    }
}
