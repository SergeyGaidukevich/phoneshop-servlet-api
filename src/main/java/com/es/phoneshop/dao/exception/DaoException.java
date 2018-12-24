package com.es.phoneshop.dao.exception;

public class DaoException extends RuntimeException {
    DaoException() {
    }

    DaoException(Throwable e) {
        super(e);
    }

    DaoException(String s) {
        super(s);
    }
}
