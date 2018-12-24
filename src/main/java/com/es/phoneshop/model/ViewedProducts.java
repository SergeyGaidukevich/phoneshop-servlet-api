package com.es.phoneshop.model;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.util.Queue;

public class ViewedProducts {
    private static final int SIZE_OF_VIEWED_LIST = 3;

    private Queue<Product> viewedProducts = new CircularFifoQueue<>(SIZE_OF_VIEWED_LIST);

    public Queue<Product> getViewedProducts() {
        return viewedProducts;
    }
}
