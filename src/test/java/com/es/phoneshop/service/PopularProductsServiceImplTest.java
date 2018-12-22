package com.es.phoneshop.service;

import com.es.phoneshop.model.PopularProducts;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.impl.PopularProductsServiceImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PopularProductsServiceImplTest {
    private static final int EXPECTED_ONE_VIEWED = 1;
    private static final int EXPECTED_THREE_VIEWED = 3;
    private static final int EXPECTED_TWO_VIEWED = 2;
    private static PopularProductsServiceImpl popularProductsService;

    private PopularProducts popularProducts;
    private List<Product> products;

    @BeforeClass
    public static void setupPopularProductsService() {
        popularProductsService = PopularProductsServiceImpl.getInstance();
    }

    @Before
    public void setupProductAndPopularProduct() {
        popularProducts = new PopularProducts();
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
    public void increaseProductPopularityIfOneViewed() {
        popularProductsService.increaseProductPopularity(popularProducts, products.get(0));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(1));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(2));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(3));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(4));

        Map<Product, Integer> popularProductsMap = popularProducts.getPopularProducts();

        int expectedSizePopularProductsMap = 5;
        int resultSizePopularProductsMap = popularProductsMap.size();

        int numberViewedFirstProduct = popularProductsMap.get(products.get(0));
        int numberViewedSecondProduct = popularProductsMap.get(products.get(1));
        int numberViewedThirdProduct = popularProductsMap.get(products.get(2));
        int numberViewedFourthProduct = popularProductsMap.get(products.get(3));
        int numberViewedFifthProduct = popularProductsMap.get(products.get(4));

        assertEquals(expectedSizePopularProductsMap, resultSizePopularProductsMap);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedFirstProduct);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedSecondProduct);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedThirdProduct);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedFourthProduct);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedFifthProduct);
    }

    @Test
    public void increaseProductPopularityIfMoreOneViewed() {
        popularProductsService.increaseProductPopularity(popularProducts, products.get(0));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(1));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(2));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(3));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(4));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(1));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(4));
        popularProductsService.increaseProductPopularity(popularProducts, products.get(1));

        Map<Product, Integer> popularProductsMap = popularProducts.getPopularProducts();

        int expectedSizePopularProductsMap = 5;
        int resultSizePopularProductsMap = popularProductsMap.size();

        int numberViewedFirstProduct = popularProductsMap.get(products.get(0));
        int numberViewedSecondProduct = popularProductsMap.get(products.get(1));
        int numberViewedThirdProduct = popularProductsMap.get(products.get(2));
        int numberViewedFourthProduct = popularProductsMap.get(products.get(3));
        int numberViewedFifthProduct = popularProductsMap.get(products.get(4));

        assertEquals(expectedSizePopularProductsMap, resultSizePopularProductsMap);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedFirstProduct);
        assertEquals(EXPECTED_THREE_VIEWED, numberViewedSecondProduct);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedThirdProduct);
        assertEquals(EXPECTED_ONE_VIEWED, numberViewedFourthProduct);
        assertEquals(EXPECTED_TWO_VIEWED, numberViewedFifthProduct);
    }

    @Test
    public void getMostPopularProducts() {
        Map<Product, Integer> popularProductsMap = new HashMap<>();
        popularProductsMap.put(products.get(0), 1);
        popularProductsMap.put(products.get(1), 4);
        popularProductsMap.put(products.get(2), 1);
        popularProductsMap.put(products.get(3), 2);
        popularProductsMap.put(products.get(4), 3);
        popularProducts.setPopularProducts(popularProductsMap);

        List<Product> mostPopularProducts = popularProductsService.getMostPopularProducts(popularProducts);

        int expectedSizeMostPopularProducts = 3;
        int resultSizeMostPopularProducts = mostPopularProducts.size();

        Product firstPopularProduct = mostPopularProducts.get(0);
        Product secondPopularProduct = mostPopularProducts.get(1);
        Product thirdPopularProduct = mostPopularProducts.get(2);

        assertEquals(expectedSizeMostPopularProducts, resultSizeMostPopularProducts);
        assertEquals(products.get(1), firstPopularProduct);
        assertEquals(products.get(4), secondPopularProduct);
        assertEquals(products.get(3), thirdPopularProduct);
    }
}
