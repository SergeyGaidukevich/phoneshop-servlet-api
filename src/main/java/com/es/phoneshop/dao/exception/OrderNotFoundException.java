package com.es.phoneshop.dao.exception;

public class OrderNotFoundException extends DaoException {
    public OrderNotFoundException() {
    }

    public OrderNotFoundException(Throwable e) {
        super(e);
    }

    public OrderNotFoundException(String s) {
        super(s);
    }
}
