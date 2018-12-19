package com.es.phoneshop.service;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Order;

public interface OrderService {
    Order placeOrder(Cart cart, String name, String deliveryAddress, String phone);
}
