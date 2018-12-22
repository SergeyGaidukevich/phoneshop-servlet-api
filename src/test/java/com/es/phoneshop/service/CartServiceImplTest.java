package com.es.phoneshop.service;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.impl.CartServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class CartServiceImplTest {
    private static Product product;
    private static CartService cartService;

    private Cart cart;

    @BeforeClass
    public static void setupProduct() {
        cartService = CartServiceImpl.getInstance();
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
    public void addProductToCartTest() {
        int quantity = 5;
        cartService.addProductToCart(cart, product, quantity);

        int expectedSizeCart = 1;
        int resultSizeCart = cart.getCartItems().size();

        int expectedSizeQuantity = 5;
        int resultSizeQuantity = cart.getCartItems().get(0).getQuantity();

        BigDecimal expectedTotalPrice = new BigDecimal(500);
        BigDecimal resultTotalPrice = cart.getTotalPrice();

        assertEquals(expectedSizeCart, resultSizeCart);
        assertEquals(expectedSizeQuantity, resultSizeQuantity);
        assertEquals(expectedTotalPrice, resultTotalPrice);
    }

    @Test
    public void addProductToCartTestIfProductExistInCart() {
        int firstAddedQuantity = 5;
        int secondAddedQuantity = 7;
        cartService.addProductToCart(cart, product, firstAddedQuantity);
        cartService.addProductToCart(cart, product, secondAddedQuantity);

        int expectedSizeCart = 1;
        int resultSizeCart = cart.getCartItems().size();

        int expectedQuantity = 12;
        int resultQuantity = cart.getCartItems().get(0).getQuantity();

        BigDecimal expectedTotalPrice = new BigDecimal(1200);
        BigDecimal resultTotalPrice = cart.getTotalPrice();

        assertEquals(expectedSizeCart, resultSizeCart);
        assertEquals(expectedQuantity, resultQuantity);
        assertEquals(expectedTotalPrice, resultTotalPrice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addProductToCartTestIfCurrentStockLessQuantity() {
        int quantity = 150;
        cartService.addProductToCart(cart, product, quantity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addProductToCartTestIfCurrentStockLessOrderedQuantity() {
        int firstOrderedQuantity = 99;
        int secondOrderedQuantity = 5;
        cartService.addProductToCart(cart, product, firstOrderedQuantity);
        cartService.addProductToCart(cart, product, secondOrderedQuantity);
    }

    @Test
    public void updateCartTest() {
        int quantity = 5;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(product, quantity));
        cart.setCartItems(cartItems);

        cartService.updateCart(cart, product, quantity);

        int expectedSizeCart = 1;
        int resultSizeCart = cart.getCartItems().size();

        int expectedQuantity = 5;
        int resultQuantity = cart.getCartItems().get(0).getQuantity();

        BigDecimal expectedTotalPrice = new BigDecimal(500);
        BigDecimal resultTotalPrice = cart.getTotalPrice();

        assertEquals(expectedSizeCart, resultSizeCart);
        assertEquals(expectedQuantity, resultQuantity);
        assertEquals(expectedTotalPrice, resultTotalPrice);
    }

    @Test(expected = NoSuchElementException.class)
    public void updateCartTestIfProductNotFoundInCart() {
        cartService.updateCart(cart, product, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateCartTestIfCurrentStockLessQuantity() {
        int quantity = 5;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(product, quantity));
        cart.setCartItems(cartItems);

        cartService.updateCart(cart, product, 5000);
    }

    @Test
    public void deleteCartItem() {
        int quantity = 5;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(product, quantity));
        cart.setCartItems(cartItems);

        cartService.deleteCartItem(cart, product);

        int expectedSizeCart = 0;
        int resultSizeCart = cart.getCartItems().size();

        BigDecimal expectedTotalPrice = new BigDecimal(0);
        BigDecimal resultTotalPrice = cart.getTotalPrice();

        assertEquals(expectedSizeCart, resultSizeCart);
        assertEquals(expectedTotalPrice, resultTotalPrice);
    }

    @Test
    public void clearCart() {
        int quantity = 5;
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(product, quantity));
        cart.setCartItems(cartItems);

        cartService.clearCart(cart);

        int expectedSizeCart = 0;
        int resultSizeCart = cart.getCartItems().size();

        BigDecimal expectedTotalPrice = new BigDecimal(0);
        BigDecimal resultTotalPrice = cart.getTotalPrice();

        assertEquals(expectedSizeCart, resultSizeCart);
        assertEquals(expectedTotalPrice, resultTotalPrice);
    }
}
