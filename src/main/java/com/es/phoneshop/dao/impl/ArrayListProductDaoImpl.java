package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.exception.ProductAlreadyExistsException;
import com.es.phoneshop.dao.exception.ProductNotFoundException;
import com.es.phoneshop.finder.ProductFinder;
import com.es.phoneshop.finder.impl.ProductFinderImpl;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.sorter.ProductSorter;
import com.es.phoneshop.sorter.SortMode;
import com.es.phoneshop.sorter.SortProperty;
import com.es.phoneshop.sorter.impl.ProductSorterImpl;
import org.apache.commons.lang3.StringUtils;

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

    private ProductSorter sorter;
    private ProductFinder finder;

    private ArrayListProductDaoImpl() {
        sorter = new ProductSorterImpl();
        finder = new ProductFinderImpl();
    }

    public ArrayListProductDaoImpl(List<Product> products, ProductSorter sorter, ProductFinder finder) {
        this.products.addAll(products);
        this.products.forEach(this::populateId);
        this.sorter = sorter;
        this.finder = finder;
    }

    public static ArrayListProductDaoImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public Product get(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id = %d not found", id)));
    }

    @Override
    public Product get(String code) {
        return products.stream()
                .filter(p -> StringUtils.equalsIgnoreCase(p.getCode(), code))
                .findAny()
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with code = %s not found", code)));
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
        return finder.findProductsByDescription(getAll(), textSearch);
    }

    @Override
    public List<Product> findProducts(SortProperty sortProperty, SortMode sortMode) {
        List<Product> queryProducts = getAll();
        sorter.sort(queryProducts, sortProperty, sortMode);

        return queryProducts;
    }

    @Override
    public List<Product> findProducts(String textSearch, SortProperty sortProperty, SortMode sortMode) {
        List<Product> queryProducts = findProducts(textSearch);
        sorter.sort(queryProducts, sortProperty, sortMode);

        return queryProducts;
    }

    @Override
    public void save(Product savingProduct) {
        if (doesProductAlreadyExist(savingProduct)) {
            throw new ProductAlreadyExistsException("Such product already exists");
        }

        populateId(savingProduct);
        products.add(savingProduct);
    }

    @Override
    public void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    private boolean doesProductAlreadyExist(Product product) {
        return products.stream().anyMatch(product::equals);
    }

    private void populateId(Product product) {
        product.setId(currentId.getAndIncrement());
    }

    private static class InstanceHolder {
        static final ArrayListProductDaoImpl instance = new ArrayListProductDaoImpl();
    }
}
