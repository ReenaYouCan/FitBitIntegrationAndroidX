package com.fitbit.api.exceptions;


public class FitbitAPIException extends RuntimeException {

    public FitbitAPIException() {
    }

    public FitbitAPIException(String message) {
        super(message);
    }
}
