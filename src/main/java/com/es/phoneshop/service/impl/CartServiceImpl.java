package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static final String NOT_ENOUGH_QUANTITY_IN_STOCK = "Not enough quantity in stock";

    private CartServiceImpl() {
    }

    public static CartServiceImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void addCartItemsToCart(Cart cart, List<CartItem> cartItems) {
        List<CartItem> newCartItems = new ArrayList<>();
        cartItems.forEach(item -> {
            Optional<CartItem> oCartItem = findProductInCart(item.getProduct(), cart);
            if (oCartItem.isPresent()) {
                CartItem cartItem = oCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            } else {
                newCartItems.add(item);
            }
        });
        cart.getCartItems().addAll(newCartItems);
        calculateTotalPrice(cart);
    }

    @Override
    public void addProductToCart(Cart cart, Product product, int quantity) {
        int currentStock = product.getStock();
        if (currentStock < quantity) {
            throw new IllegalArgumentException(NOT_ENOUGH_QUANTITY_IN_STOCK);
        }

        Optional<CartItem> cartItemOptional = findProductInCart(product, cart);
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int orderedQuantity = cartItem.getQuantity() + quantity;
            if (currentStock >= orderedQuantity) {
                cartItem.setQuantity(orderedQuantity);
            } else {
                throw new IllegalArgumentException(NOT_ENOUGH_QUANTITY_IN_STOCK);
            }
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
        calculateTotalPrice(cart);
    }

    @Override
    public void updateCart(Cart cart, Product product, int quantity) {
        int currentStock = product.getStock();
        if (currentStock < quantity) {
            throw new IllegalArgumentException(NOT_ENOUGH_QUANTITY_IN_STOCK);
        }

        Optional<CartItem> oCartItem = findProductInCart(product, cart);
        if (oCartItem.isPresent()) {
            CartItem cartItem = oCartItem.get();
            cartItem.setQuantity(quantity);
        } else {
            throw new NoSuchElementException(product.getCode());
        }
        calculateTotalPrice(cart);
    }

    @Override
    public void deleteCartItem(Cart cart, Product product) {
        cart.getCartItems().removeIf(cartItem -> product.equals(cartItem.getProduct()));
        calculateTotalPrice(cart);
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getCartItems().clear();
        calculateTotalPrice(cart);
    }


    private Optional<CartItem> findProductInCart(Product product, Cart cart) {
        return cart.getCartItems().stream()
                .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
                .findAny();
    }

    private void calculateTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }

    private static class InstanceHolder {
        static final CartServiceImpl instance = new CartServiceImpl();
    }
}
