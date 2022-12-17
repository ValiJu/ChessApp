package com.example.chess.domain.model.chessBoard.pieces;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.PawnNextPositions;
import com.example.chess.domain.model.positionn.Position;
import lombok.Getter;


public class Pawn extends ChessPiece {
    @Getter
    private final PawnNextPositions nextPositions;

    public Pawn(Position position, Color color) {
        super(position, color);
        nextPositions = calculateNextPositions(position, color);
    }

    private PawnNextPositions calculateNextPositions(Position position, Color color) {
        final PawnNextPositions nextPositions;
        if (color == Color.BLACK) {
            nextPositions = new PawnNextPositions(new Position(position.getX() + 1, position.getY())
                    , new Position(position.getX() + 1, position.getY() + 1)
                    , new Position(position.getX() + 1, position.getY() - 1)
                    , new Position(position.getX() + 2, position.getY()));
        } else {
            nextPositions = new PawnNextPositions(new Position(position.getX() - 1, position.getY())
                    , new Position(position.getX() - 1, position.getY() - 1)
                    , new Position(position.getX() - 1, position.getY() + 1)
                    , new Position(position.getX() - 2, position.getY()));
        }
        return nextPositions;
    }
}
