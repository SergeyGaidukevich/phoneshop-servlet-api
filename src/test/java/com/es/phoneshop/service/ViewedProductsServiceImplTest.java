package com.es.phoneshop.service;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ViewedProducts;
import com.es.phoneshop.service.impl.ViewedProductsServiceImpl;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ViewedProductsServiceImplTest {
    private static ViewedProductsService viewedProductService;

    private ViewedProducts viewedProducts;
    private List<Product> products;

    @BeforeClass
    public static void setupViewedProductService() {
        viewedProductService = ViewedProductsServiceImpl.getInstance();
    }

    @Before
    public void setupProductsAndViewedProducts() {
        viewedProducts = new ViewedProducts();
        products = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        BigDecimal price = new BigDecimal(50);
        products.add(new Product("sgs1", "Samsung1", price, usd, 100, "https://galaxy.jpg"));
        products.add(new Product("sgs2", "Samsung2", price, usd, 99, "https://galaxy.jpg"));
        products.add(new Product("sgs3", "Samsung3", price, usd, 10, "https://galaxy.jpg"));
        products.add(new Product("sgs4", "Samsung4", price, usd, 12, "https://galaxy.jpg"));
        products.add(new Product("sgs5", "Samsung5", price, usd, 55, "https://galaxy.jpg"));
    }

    @Test
    public void addProductToViewed() {
        viewedProductService.addProductToViewed(viewedProducts, products.get(0));
        viewedProductService.addProductToViewed(viewedProducts, products.get(1));
        viewedProductService.addProductToViewed(viewedProducts, products.get(2));
        viewedProductService.addProductToViewed(viewedProducts, products.get(3));
        viewedProductService.addProductToViewed(viewedProducts, products.get(4));

        CircularFifoQueue<Product> actualViewedProducts = (CircularFifoQueue<Product>) viewedProducts.getViewedProducts();
        Product firsLastViewedProduct = actualViewedProducts.get(2);
        Product secondLastViewedProduct = actualViewedProducts.get(1);
        Product thirdLastViewedProduct = actualViewedProducts.get(0);

        assertEquals(3, actualViewedProducts.size());
        assertEquals(products.get(4), firsLastViewedProduct);
        assertEquals(products.get(3), secondLastViewedProduct);
        assertEquals(products.get(2), thirdLastViewedProduct);
    }
}
