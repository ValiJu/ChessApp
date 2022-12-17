package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.Pawn;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PawnHandler implements ChessPieceHandler {

    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        Pawn pawn = (Pawn) chessPiece;
        List<Position> positions = new ArrayList<>();
        verifyForwardAndGet(chessBoard, pawn.getNextPositions().getForward()).ifPresent(position -> {
            positions.add(position);
            if (pawn.getPosition().getX() == getPawnLine(pawn)) {
                verifyForwardAndGet(chessBoard, pawn.getNextPositions().getDoubleForward()).ifPresent(positions::add);
            }
        });
        verifyDiagonalAndGet(pawn, chessBoard, pawn.getNextPositions().getForwardLeft()).ifPresent(positions::add);
        verifyDiagonalAndGet(pawn, chessBoard, pawn.getNextPositions().getForwardRight()).ifPresent(positions::add);

        return positions;
    }

    private Optional<Position> verifyDiagonalAndGet(Pawn pawn, ChessSquare[][] chessBoard, Position position) {
        if (position.isEligible() &&
                chessBoard[position.getX()][position.getY()].containsEnemy(pawn.getColor())) {
            return Optional.of(position);
        }
        return Optional.empty();
    }

    private Optional<Position> verifyForwardAndGet(ChessSquare[][] chessBoard, Position position) {
        if (position.isEligible() &&
                chessBoard[position.getX()][position.getY()].containsNothing()) {
            return Optional.of(position);
        }
        return Optional.empty();
    }

    private int getPawnLine(Pawn pawn) {
        return pawn.getColor() == Color.BLACK ? 1 : 6;
    }

}
