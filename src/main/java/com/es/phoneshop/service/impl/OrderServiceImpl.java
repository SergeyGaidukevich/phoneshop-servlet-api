package com.es.phoneshop.service.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDaoImpl;
import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.service.OrderService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final String PHONE_REGEX = "^[+]{1}[0-9]{3}[(]{1}[0-9]{2}[)]{1}[0-9]{7}$";

    private OrderDao orderDao;

    private OrderServiceImpl() {
        this.orderDao = ArrayListOrderDaoImpl.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        return OrderServiceImpl.InstanceHolder.instance;
    }

    @Override
    public Order placeOrder(Cart cart, String name, String deliveryAddress, String phone) {
        if (!checkParameters(name, deliveryAddress, phone)) {
            throw new IllegalArgumentException();
        }

        Order order = createOrder(name, deliveryAddress, phone, cart);
        orderDao.save(order);
        updateProductStock(cart.getCartItems());

        return order;
    }

    private Order createOrder(String name, String deliveryAddress, String phone, Cart cart) {
        Order order = new Order();
        order.setName(name);
        order.setDeliveryAddress(deliveryAddress);
        order.setPhone(phone);
        order.setTotalPrice(cart.getTotalPrice());
        order.getCartItems().addAll(cart.getCartItems());

        return order;
    }

    private void updateProductStock(List<CartItem> cartItems) {
        cartItems.forEach(cartItem -> {
            int finalStock = cartItem.getProduct().getStock() - cartItem.getQuantity();
            cartItem.getProduct().setStock(finalStock);
        });
    }

    private boolean checkParameters(String name, String deliveryAddress, String phone) {
        return StringUtils.isNoneBlank(name, deliveryAddress) && isPhone(phone);
    }

    private boolean isPhone(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }

    private static class InstanceHolder {
        static final OrderServiceImpl instance = new OrderServiceImpl();
    }
}
