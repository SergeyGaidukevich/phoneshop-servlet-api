package com.es.phoneshop.model;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class ViewedProducts {
    private static final int SIZE_OF_VIEWED_LIST = 3;

    private CircularFifoQueue<Product> viewedProducts = new CircularFifoQueue<>(SIZE_OF_VIEWED_LIST);

    public CircularFifoQueue<Product> getViewedProducts() {
        return viewedProducts;
    }

    public void setViewedProducts(CircularFifoQueue<Product> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }


}
