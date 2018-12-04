package com.es.phoneshop.model;

import java.util.ArrayList;
import java.util.List;

public class ViewedProducts {
    private List<Product> viewedProducts = new ArrayList<>();

    public List<Product> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(List<Product> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }
}
