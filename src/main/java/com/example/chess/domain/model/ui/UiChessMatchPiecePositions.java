package com.example.chess.domain.model.ui;

import lombok.Data;

import java.util.List;

@Data
public class UiChessMatchPiecePositions {
    private List<String> users;
    private List<UiChessPiece> pieces;
}
