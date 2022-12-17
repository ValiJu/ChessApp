package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.King;
import com.example.chess.domain.model.positionn.Position;

import java.util.ArrayList;
import java.util.List;

import static com.example.chess.domain.util.UtilChessBoard.*;

public class KingHandler implements ChessPieceHandler {

    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        King king = (King) chessPiece;
        Position opponentKingPosition = findKingPosition(switchColor(king.getColor()), chessBoard);

        return new ArrayList<>(getKingPositions(king.getPosition())
                .stream()
                .filter(Position::isEligible)
                .filter(p -> chessBoard[p.getX()][p.getY()].containsNothing() ||
                        chessBoard[p.getX()][p.getY()].containsEnemy(chessPiece.getColor()))
                .filter(p -> !getKingPositions(opponentKingPosition).contains(p))
                .toList());
    }

}
