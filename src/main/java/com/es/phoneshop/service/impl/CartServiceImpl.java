package com.es.phoneshop.service.impl;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.CartService;

import java.util.Optional;

public class CartServiceImpl implements CartService {
    private CartServiceImpl() {
    }

    public static CartServiceImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void addProductToCart(Cart cart, Product product, int quantity) throws IllegalArgumentException{
        int currentStock = product.getStock();
        if (currentStock < quantity){
            throw  new IllegalArgumentException();
        }

        Optional<CartItem> cartItemOptional = findProductInCart(product, cart);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int orderedQuantity = cartItem.getQuantity() + quantity;
            if(currentStock < orderedQuantity){
                throw new IllegalArgumentException();
            } else {
                cartItem.setQuantity(orderedQuantity);
            }
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }

    private Optional<CartItem> findProductInCart(Product product, Cart cart){
        return cart.getCartItems().stream()
                .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
                .findAny();
    }

    private static class InstanceHolder {
        static CartServiceImpl instance = new CartServiceImpl();
    }
}
