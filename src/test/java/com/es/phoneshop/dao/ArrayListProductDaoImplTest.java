package com.es.phoneshop.dao;

import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.model.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

public class ArrayListProductDaoImplTest {
    private static final String CODE = "sgs";
    private static final String DESCRIPTION = "Samsung";
    private static final int STOCK = 100;
    private static final String IMAGE_URL = "https://image.jpg";
    private static final BigDecimal PRICE = new BigDecimal(100);
    private static final Currency CURRENCY = Currency.getInstance("USD");

    private ProductDao productDao;

    @Test
    public void testFindProductsWhenProductsAreValid() {
        List<Product> products = Collections.nCopies(2, createProduct());
        productDao = new ArrayListProductDaoImpl(products);

        List<Product> result = productDao.findProducts();

        assertNotNull(result);
        assertEquals(result.size(), 2);
        result.forEach(this::assertProduct);
    }

    @Test
    public void testFindProductsWhenProductContainsNullPrice() {
        List<Product> products = Collections.singletonList(createProductWithNullPrice());
        productDao = new ArrayListProductDaoImpl(products);

        List<Product> result = productDao.findProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindProductsWhenProductContainsNotPositiveStock() {
        List<Product> products = Collections.singletonList(createProductWithNotPositiveStock());
        productDao = new ArrayListProductDaoImpl(products);

        List<Product> result = productDao.findProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    private void assertProduct(Product product) {
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(product.getCode(), CODE);
        assertEquals(product.getDescription(), DESCRIPTION);
        assertEquals(product.getPrice(), PRICE);
        assertEquals(product.getCurrency(), CURRENCY);
        assertEquals(product.getStock(), STOCK);
        assertEquals(product.getImageUrl(), IMAGE_URL);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setCode(CODE);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setCurrency(CURRENCY);
        product.setStock(STOCK);
        product.setImageUrl(IMAGE_URL);

        return product;
    }

    private Product createProductWithNullPrice() {
        Product product = createProduct();
        product.setPrice(null);

        return product;
    }

    private Product createProductWithNotPositiveStock() {
        Product product = createProduct();
        product.setStock(Integer.MIN_VALUE);

        return product;
    }
}
