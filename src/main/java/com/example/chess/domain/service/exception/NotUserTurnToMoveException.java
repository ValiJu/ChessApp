package com.example.chess.domain.service.exception;

public class NotUserTurnToMoveException extends RuntimeException {
    public NotUserTurnToMoveException(String message) {
        super(message);
    }
}
