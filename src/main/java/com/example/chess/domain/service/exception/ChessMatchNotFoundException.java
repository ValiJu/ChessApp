package com.example.chess.domain.service.exception;

import java.util.function.Supplier;

public class ChessMatchNotFoundException extends RuntimeException {
    public ChessMatchNotFoundException(String message) {
        super(message);
    }

}
