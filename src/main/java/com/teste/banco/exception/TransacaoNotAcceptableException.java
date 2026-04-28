package com.teste.banco.exception;

public class TransacaoNotAcceptableException extends RuntimeException {
    public TransacaoNotAcceptableException(String message) {
        super(message);
    }
}
