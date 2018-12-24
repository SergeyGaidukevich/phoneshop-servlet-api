package com.es.phoneshop.dao;

import com.es.phoneshop.dao.exception.OrderNotFoundException;
import com.es.phoneshop.dao.impl.ArrayListOrderDaoImpl;
import com.es.phoneshop.model.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        Order result = orderDao.get(1L);

        assertNotNull(result);
        assertEquals(firstOrder, result);
    }

    @Test(expected = OrderNotFoundException.class)
    public void getOrderTestIfOrderNotExist() {
        List<Order> orders = new ArrayList<>();
        Order firstOrder = createOrder();
        Order secondOrder = createOrder();
        firstOrder.setId(1L);
        secondOrder.setId(2L);
        Collections.addAll(orders, firstOrder, secondOrder);

        orderDao = new ArrayListOrderDaoImpl(orders);
        orderDao.get(3L);
    }

    @Test
    public void saveTest() {
        Order expected = createOrder();
        expected.setId(1L);

        Order order = createOrder();
        orderDao = new ArrayListOrderDaoImpl(new ArrayList<>());
        orderDao.save(order);

        assertEquals(expected, orderDao.get(1L));
    }

    @Test
    public void deleteTest() {
        Order order = createOrder();
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orderDao = new ArrayListOrderDaoImpl(orders);

        long id = 1L;
        orderDao.delete(id);

        assertTrue(orderDao.getAll().isEmpty());
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
