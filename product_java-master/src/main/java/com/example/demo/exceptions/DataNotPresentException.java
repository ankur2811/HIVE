package com.example.demo.exceptions;

public class DataNotPresentException extends Exception{
    public DataNotPresentException() {
        super();
    }

    public DataNotPresentException(String message) {
        super(message);
    }

    public DataNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotPresentException(Throwable cause) {
        super(cause);
    }

    protected DataNotPresentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
