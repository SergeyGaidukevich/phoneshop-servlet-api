package com.es.phoneshop.dao.exception;

public class ArrayListProductDaoException extends RuntimeException {
    ArrayListProductDaoException() {
    }

    ArrayListProductDaoException(Throwable e) {
        super(e);
    }

    ArrayListProductDaoException(String s) {
        super(s);
    }
}
