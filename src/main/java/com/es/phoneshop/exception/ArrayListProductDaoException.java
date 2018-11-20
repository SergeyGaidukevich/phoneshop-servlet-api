package com.es.phoneshop.exception;

public class ArrayListProductDaoException extends Exception{
    public ArrayListProductDaoException(){}
    public ArrayListProductDaoException(Throwable e){
        super(e);
    }

    public ArrayListProductDaoException(String s) {
        super(s);
    }
}
