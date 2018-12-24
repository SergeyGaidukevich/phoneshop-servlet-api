package com.es.phoneshop.dao;

import com.es.phoneshop.dao.exception.DaoException;
import com.es.phoneshop.dao.impl.ArrayListProductDaoImpl;
import com.es.phoneshop.finder.impl.ProductFinderImpl;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.sorter.SortMode;
import com.es.phoneshop.sorter.SortProperty;
import com.es.phoneshop.sorter.impl.ProductSorterImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoImplTest {
    private static final String CODE = "sgs";
    private static final String DESCRIPTION_SAMSUNG = "Samsung";
    private static final int STOCK = 100;
    private static final String IMAGE_URL = "https://image.jpg";
    private static final BigDecimal PRICE = new BigDecimal(100);
    private static final Currency CURRENCY = Currency.getInstance("USD");
    private static final String DESCRIPTION_SAMSUNG_GALAXY = "Samsung Galaxy";
    private static final BigDecimal ANOTHER_PRICE = new BigDecimal(200);

    private ProductDao productDao;

    private static ProductSorterImpl sorter = new ProductSorterImpl();
    private static ProductFinderImpl finder = new ProductFinderImpl();

    @Test
    public void testGetAllWhenProductsAreValid() {
        List<Product> products = Collections.nCopies(2, createProduct());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        List<Product> result = productDao.getAll();

        assertNotNull(result);
        assertEquals(result.size(), 2);
        result.forEach(this::assertProduct);
    }

    @Test
    public void testGetAllWhenProductContainsNullPrice() {
        List<Product> products = Collections.singletonList(createProductWithNullPrice());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        List<Product> result = productDao.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAllWhenProductContainsNotPositiveStock() {
        List<Product> products = Collections.singletonList(createProductWithNotPositiveStock());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        List<Product> result = productDao.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindProductByTextSearch() {
        List<Product> expected = new ArrayList<>();
        Collections.addAll(expected, createProductWithAnotherDescription(), createProduct());
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, createProduct(), createProductWithAnotherDescription());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        List<Product> result = productDao.findProducts(DESCRIPTION_SAMSUNG_GALAXY);

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(expected, result);
    }

    @Test
    public void testFindProductAndSortByPriceAndDesc() {
        List<Product> expected = new ArrayList<>();
        Collections.addAll(expected, createProductWithAnotherPrice(), createProduct());
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, createProduct(), createProductWithAnotherPrice());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        List<Product> result = productDao.findProducts(SortProperty.PRICE, SortMode.DESCENDING);

        assertNotNull(result);
        assertEquals(result.size(), 2);
        assertEquals(expected, result);
    }

    @Test
    public void testGetProductWithExistingProduct() {
        Product expected = createProduct();
        expected.setId(2L);
        List<Product> products = new ArrayList<>();
        Collections.addAll(products, createProductWithAnotherPrice(), createProduct());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        Product result = productDao.get(2L);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test(expected = DaoException.class)
    public void testGetProductWithNotExistingProduct() {
        List<Product> products = Collections.nCopies(2, createProduct());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        productDao.get(7L);
    }

    @Test
    public void testSaveProductIfIsNotThereAlreadyProductInDao() {
        List<Product> products = new ArrayList<>();
        products.add(createProductWithAnotherPrice());
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);
        Product expected = createProduct();
        expected.setId(2L);

        Product product = createProduct();
        productDao.save(product);
        Product result = productDao.get(2L);

        assertEquals(expected, result);
    }

    @Test
    public void deleteTest() {
        Product product = createProduct();
        List<Product> products = new ArrayList<>();
        products.add(product);
        productDao = new ArrayListProductDaoImpl(products, sorter, finder);

        long id = 1L;
        productDao.delete(id);

        assertTrue(productDao.getAll().isEmpty());
    }

    private void assertProduct(Product product) {
        assertNotNull(product);
        assertNotNull(product.getId());
        assertEquals(product.getCode(), CODE);
        assertEquals(product.getDescription(), DESCRIPTION_SAMSUNG);
        assertEquals(product.getPrice(), PRICE);
        assertEquals(product.getCurrency(), CURRENCY);
        assertEquals(product.getStock(), STOCK);
        assertEquals(product.getImageUrl(), IMAGE_URL);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setCode(CODE);
        product.setDescription(DESCRIPTION_SAMSUNG);
        product.setPrice(PRICE);
        product.setCurrency(CURRENCY);
        product.setStock(STOCK);
        product.setImageUrl(IMAGE_URL);

        return product;
    }

    private Product createProductWithAnotherDescription() {
        Product product = createProduct();
        product.setDescription(DESCRIPTION_SAMSUNG_GALAXY);

        return product;
    }

    private Product createProductWithAnotherPrice() {
        Product product = createProduct();
        product.setPrice(ANOTHER_PRICE);

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
