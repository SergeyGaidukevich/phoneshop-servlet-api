package com.es.phoneshop.dao.exception;

public class SaveProductException extends ArrayListProductDaoException {
    public SaveProductException() {
    }

    public SaveProductException(Throwable e) {
        super(e);
    }

    public SaveProductException(String s) {
        super(s);
    }
}
