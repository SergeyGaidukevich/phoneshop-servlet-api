package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ArrayListProductDaoException;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.service.FindProductsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static final Predicate<Product> NON_NULL_PRICE = product -> Objects.nonNull(product.getPrice());
    private static final Predicate<Product> STOCK_LEVEL_HIGHER_THAN_ZERO = product -> product.getStock() > 0;
    private static final FindProductsService FIND_PRODUCTS_SERVICE = new FindProductsService();
    private static ArrayListProductDao INSTANCE;
    private final List<Product> products = new CopyOnWriteArrayList<>();
    private final AtomicLong currentId = new AtomicLong(1);

    private ArrayListProductDao() {
    }

    public ArrayListProductDao(List<Product> products) {
        this.products.addAll(products);
        this.products.forEach(this::populateId);
    }

    public static ArrayListProductDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ArrayListProductDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ArrayListProductDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Product getProduct(Long id) throws ArrayListProductDaoException {
        return products.stream()
                .filter((p) -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ArrayListProductDaoException("There is no element with such id = " + id));
    }

    @Override
    public List<Product> findProducts(String textSearch, String sortingProperty, String sortMode) {
        List<Product> queryProducts = new ArrayList<>(products);

        if (textSearch != null && sortingProperty != null && sortMode != null) {
            if (!textSearch.isEmpty()) {
                queryProducts = FIND_PRODUCTS_SERVICE.findSearchProducts(queryProducts, textSearch);
            }
            queryProducts = FIND_PRODUCTS_SERVICE.sortProduct(queryProducts, sortingProperty, sortMode);
        }

        return queryProducts.stream()
                .filter(NON_NULL_PRICE)
                .filter(STOCK_LEVEL_HIGHER_THAN_ZERO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        boolean isThereAlreadyProductInDao = products.stream()
                .noneMatch(product1 -> product1.getCode().equals(product.getCode()) &&
                        product1.getDescription().equals(product.getDescription()) &&
                        product1.getPrice().equals(product.getPrice()) &&
                        product1.getCurrency().equals(product.getCurrency()) &&
                        product1.getStock() == product.getStock() &&
                        product1.getImageUrl().equals(product.getImageUrl()));

        if (isThereAlreadyProductInDao) {
            populateId(product);
            products.add(product);
        }
    }

    @Override
    public void delete(Long id) {
        throw new RuntimeException("Not implemented");
    }

    private void populateId(Product product) {
        product.setId(currentId.getAndIncrement());
    }
}
