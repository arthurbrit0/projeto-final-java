package com.mercado.sistema_vendas.exceptions;

public class ProdutoEmUsoException extends RuntimeException {
    public ProdutoEmUsoException(String message) {
        super(message);
    }
}
