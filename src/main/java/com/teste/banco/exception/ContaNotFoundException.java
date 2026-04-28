package com.teste.banco.exception;

public class ContaNotFoundException extends RuntimeException {

    public ContaNotFoundException(String message) {
        super(message);
    }
}
