package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.Knight;
import com.example.chess.domain.model.positionn.Position;

import java.util.List;

import static com.example.chess.domain.util.UtilChessBoard.getKnightPositions;

public class KnightHandler implements ChessPieceHandler {

    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        Knight knight = (Knight) chessPiece;
        return getKnightPositions(knight.getPosition()).stream()
                .filter(Position::isEligible)
                .filter(p -> chessBoard[p.getX()][p.getY()].containsEnemy(chessPiece.getColor()) ||
                        chessBoard[p.getX()][p.getY()].containsNothing())
                .toList();
    }


}
