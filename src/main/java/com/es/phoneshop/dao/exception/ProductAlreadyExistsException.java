package com.es.phoneshop.dao.exception;

public class ProductAlreadyExistsException extends ArrayListProductDaoException {
    public ProductAlreadyExistsException() {
    }

    public ProductAlreadyExistsException(Throwable e) {
        super(e);
    }

    public ProductAlreadyExistsException(String s) {
        super(s);
    }
}
