package com.es.phoneshop.dao.exception;

public class OrderAlreadyExistsException extends DaoException {
    public OrderAlreadyExistsException() {
    }

    public OrderAlreadyExistsException(Throwable e) {
        super(e);
    }

    public OrderAlreadyExistsException(String s) {
        super(s);
    }
}
