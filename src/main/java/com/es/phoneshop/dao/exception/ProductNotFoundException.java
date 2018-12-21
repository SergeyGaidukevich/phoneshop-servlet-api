package com.es.phoneshop.dao.exception;

public class ProductNotFoundException extends ArrayListProductDaoException {
    public ProductNotFoundException() {
    }

    public ProductNotFoundException(Throwable e) {
        super(e);
    }

    public ProductNotFoundException(String s) {
        super(s);
    }
}
