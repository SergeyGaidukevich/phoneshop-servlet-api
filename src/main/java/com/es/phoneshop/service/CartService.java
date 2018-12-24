package com.es.phoneshop.service;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.CartItem;
import com.es.phoneshop.model.Product;

import java.util.List;

public interface CartService {
    void addCartItemsToCart(Cart cart, List<CartItem> cartItems);

    void addProductToCart(Cart cart, Product product, int quantity);

    void updateCart(Cart cart, Product product, int quantity);

    void deleteCartItem(Cart cart, Product product);

    void clearCart(Cart cart);
}
