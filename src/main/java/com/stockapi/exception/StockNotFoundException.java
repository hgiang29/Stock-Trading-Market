package com.stockapi.exception;

public class StockNotFoundException extends Exception{

    public StockNotFoundException(String message) {
        super(message);
    }
}
