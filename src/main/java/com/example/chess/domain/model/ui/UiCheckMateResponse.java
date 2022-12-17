package com.example.chess.domain.model.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UiCheckMateResponse {
    String winnerId;
    String looserId;
    boolean isCheckMate;
}
