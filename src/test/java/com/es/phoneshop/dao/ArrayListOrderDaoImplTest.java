package com.es.phoneshop.dao;

import com.es.phoneshop.dao.impl.ArrayListOrderDaoImpl;
import org.junit.Test;

import com.es.phoneshop.model.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArrayListOrderDaoImplTest {
    private OrderDao orderDao;

    @Test
    public void getOrderTest() {
        List<Order> orders = new ArrayList<>();
        Order firstOrder = createOrder();
        Order secondOrder = createOrder();
        firstOrder.setId(1L);
        secondOrder.setId(2L);
        Collections.addAll(orders, firstOrder, secondOrder);

        orderDao = new ArrayListOrderDaoImpl(orders);
        Order result = orderDao.getOrder(1L);

        assertNotNull(result);
        assertEquals(firstOrder, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void getOrderTestIfOrderNotExist() {
        List<Order> orders = new ArrayList<>();
        Order firstOrder = createOrder();
        Order secondOrder = createOrder();
        firstOrder.setId(1L);
        secondOrder.setId(2L);
        Collections.addAll(orders, firstOrder, secondOrder);

        orderDao = new ArrayListOrderDaoImpl(orders);
        orderDao.getOrder(3L);
    }

    @Test
    public void saveTest() {
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder());
        orderDao = new ArrayListOrderDaoImpl(orders);
        Order expected = createOrder();
        expected.setId(2L);

        Order product = createOrder();
        orderDao.save(product);
        Order result = orderDao.getOrder(2L);

        assertNotNull(orderDao);
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTest() {
        Order order = createOrder();
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orderDao = new ArrayListOrderDaoImpl(orders);

        long id = 80L;
        orderDao.delete(id);
    }

    private Order createOrder() {
        Order order = new Order();
        order.setName("aaa");
        order.setDeliveryAddress("ddd");
        order.setPhone("333");
        order.setTotalPrice(new BigDecimal("500"));

        return order;
    }
}
