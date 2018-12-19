package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.impl.ArrayListOrderDaoImpl;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.OrderService;
import org.apache.commons.lang3.StringUtils;

public class OrderServiceImpl implements OrderService {
    private OrderServiceImpl() {

    }

    public static OrderServiceImpl getInstance() {
        return OrderServiceImpl.InstanceHolder.instance;
    }

    @Override
    public Order placeOrder(Cart cart, String name, String deliveryAddress, String phone) {
        if (!checkParameters(name, deliveryAddress, phone)) {
            throw new IllegalArgumentException();
        }
        Order order = new Order();

        order.setName(name);
        order.setDeliveryAddress(deliveryAddress);
        order.setPhone(phone);
        order.getCartItems().addAll(cart.getCartItems());
        order.setTotalPrice(cart.getTotalPrice());

        ArrayListOrderDaoImpl.getInstance().save(order);

        return order;
    }

    private boolean checkParameters(String name, String deliveryAddress, String phone) {
        return (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(deliveryAddress) && StringUtils.isNotBlank(phone));
    }

    private static class InstanceHolder {
        static OrderServiceImpl instance = new OrderServiceImpl();
    }
}
