package com.example.chess.domain.model.ui;

import com.example.chess.domain.model.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UiChessMatch {
    private String whiteUserId;
    private String blackUserId;
    private Color colorToMove;
}
