package com.example.chess.domain.model.chessBoard;

import com.example.chess.domain.model.chessBoard.pieces.*;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import lombok.Data;
import org.springframework.stereotype.Component;

import static com.example.chess.domain.model.positionn.StartPositions.*;

public class ChessPieceFactory {

    public static  ChessPiece getChessPiece(String pieceType, Position position, Color color) {
        return switch (pieceType) {
            case ROOK -> new Rook(position, color);
            case KNIGHT -> new Knight(position, color);
            case BISHOP -> new Bishop(position, color);
            case QUEEN -> new Queen(position, color);
            case KING -> new King(position, color);
            case PAWN -> new Pawn(position, color);
            default -> null;
        };
    }

}
