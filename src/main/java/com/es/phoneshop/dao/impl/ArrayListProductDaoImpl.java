package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.FindProductsAssistant;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.ProductAlreadyExistsException;
import com.es.phoneshop.dao.exception.ProductNotFoundException;
import com.es.phoneshop.dao.sortParameters.SortMode;
import com.es.phoneshop.dao.sortParameters.SortProperty;
import com.es.phoneshop.model.Product;

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

    private FindProductsAssistant findProductsAssistant = FindProductsImplAssistant.getInstance();

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
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with code %d not found", id)));
    }

    @Override
    public List<Product> getAll() {
        return products.stream()
                .filter(NON_NULL_PRICE)
                .filter(STOCK_LEVEL_HIGHER_THAN_ZERO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String textSearch) {
        return findProductsAssistant.findProductsByDescription(getAll(), textSearch);
    }

    @Override
    public List<Product> findProducts(SortProperty sortProperty, SortMode sortMode) {
        List<Product> queryProducts = getAll();
        findProductsAssistant.sortProducts(queryProducts, sortProperty, sortMode);

        return queryProducts;
    }

    @Override
    public List<Product> findProducts(String textSearch, SortProperty sortProperty, SortMode sortMode) {
        List<Product> queryProducts = findProducts(textSearch);
        findProductsAssistant.sortProducts(queryProducts, sortProperty, sortMode);

        return queryProducts;
    }

    @Override
    public void save(Product savingProduct) {
        if (!doesProductAlreadyExist(savingProduct)) {
            populateId(savingProduct);
            products.add(savingProduct);
        } else {
            throw new ProductAlreadyExistsException("Such product already exists");
        }
    }

    private boolean doesProductAlreadyExist(Product product) {
        return products.stream().anyMatch(product::equals);
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
}
