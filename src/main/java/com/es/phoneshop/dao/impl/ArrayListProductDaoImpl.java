package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.AssistantFindProducts;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.ArrayListProductDaoException;
import com.es.phoneshop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDaoImpl implements ProductDao {
    private static final Predicate<Product> NON_NULL_PRICE = product -> Objects.nonNull(product.getPrice());
    private static final Predicate<Product> STOCK_LEVEL_HIGHER_THAN_ZERO = product -> product.getStock() > 0;

    private final List<Product> products = new CopyOnWriteArrayList<>();
    private final AtomicLong currentId = new AtomicLong(1);

    private AssistantFindProducts assistantFindProducts = AssistantFindProductsImpl.getInstance();

    private ArrayListProductDaoImpl() {
    }

    public ArrayListProductDaoImpl(List<Product> products) {
        this.products.addAll(products);
        this.products.forEach(this::populateId);
    }

    public static ArrayListProductDaoImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Product getProduct(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ArrayListProductDaoException(String.format("Product with code %d not found", id)));
    }

    @Override
    public List<Product> findProducts(String textSearch, String sortingProperty, String sortMode) throws ArrayListProductDaoException {
        List<Product> queryProducts = new ArrayList<>(getAllProducts());

        queryProducts = assistantFindProducts.findProductsByDescription(queryProducts, textSearch);
        assistantFindProducts.sortProducts(queryProducts, sortingProperty, sortMode);

        return queryProducts;
    }

    @Override
    public void save(Product savingProduct) {
        boolean isNotThereAlreadyProductInDao = products.stream()
                .noneMatch(savingProduct::equals);

        if (isNotThereAlreadyProductInDao) {
            populateId(savingProduct);
            products.add(savingProduct);
        } else {
            throw new ArrayListProductDaoException("Such phone already exists");
        }
    }

    @Override
    public void delete(Long id) {
        throw new RuntimeException("Not implemented");
    }

    private void populateId(Product product) {
        product.setId(currentId.getAndIncrement());
    }

    private static class InstanceHolder {
        static ArrayListProductDaoImpl instance = new ArrayListProductDaoImpl();
    }

    private List<Product> getAllProducts() {
        return products.stream()
                .filter(NON_NULL_PRICE)
                .filter(STOCK_LEVEL_HIGHER_THAN_ZERO)
                .collect(Collectors.toList());
    }

}
