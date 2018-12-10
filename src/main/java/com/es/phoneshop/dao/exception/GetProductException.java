package com.es.phoneshop.dao.exception;

public class GetProductException extends ArrayListProductDaoException {
    public GetProductException() {
    }

    public GetProductException(Throwable e) {
        super(e);
    }

    public GetProductException(String s) {
        super(s);
    }
}
