package com.example.chess.domain.model.chessBoard.pieces;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {
    public Bishop(Position position, Color color) {
        super(position, color);
    }
}
