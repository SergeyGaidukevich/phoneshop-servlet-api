package com.es.phoneshop.service;

import com.es.phoneshop.model.Cart;
import com.es.phoneshop.model.Product;

public interface CartService {
    void addProductToCart(Cart cart, Product product, int quantity);
}
