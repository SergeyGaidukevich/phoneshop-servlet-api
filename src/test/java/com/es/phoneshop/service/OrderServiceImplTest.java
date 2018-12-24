package com.es.phoneshop.service;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Order;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceImplTest {
    private static OrderService orderService;
    private static Product product;

    private Cart cart;
    private Order order;

    @BeforeClass
    public static void setupProduct() {
        orderService = OrderServiceImpl.getInstance();
        Currency usd = Currency.getInstance("USD");
        BigDecimal price = new BigDecimal(100);
        product = new Product("sgs", "Samsung", price, usd, 100, "https://galaxy.jpg");
        product.setId(1L);
    }

    @Before
    public void setupCart() {
        cart = new Cart();
    }

    @Test
    public void placeOrderTest() {
        int quantity = 5;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(product, quantity));
        cart.setCartItems(cartItems);
        order = orderService.placeOrder(cart, "sss", "ppp", "+375(44)1234567");

        assertEquals(95, product.getStock());
        assertEquals(1, order.getCartItems().size());
        assertEquals("sss", order.getName());
        assertEquals("ppp", order.getDeliveryAddress());
        assertEquals("+375(44)1234567", order.getPhone());
    }

    @Test(expected = IllegalArgumentException.class)
    public void placeOrderIfParametersNotValid() {
        int quantity = 5;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(new Product(), quantity));
        cart.setCartItems(cartItems);
        order = orderService.placeOrder(cart, "", " ", "qqq");
    }


}
