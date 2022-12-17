package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.Queen;
import com.example.chess.domain.model.positionn.Position;

import java.util.ArrayList;
import java.util.List;

import static com.example.chess.domain.util.UtilChessBoard.*;

public class QueenHandler implements ChessPieceHandler {

    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        Queen queen = (Queen) chessPiece;
        List<Position> positions = new ArrayList<>();
        positions.addAll(getLeftTopPositions(queen, chessBoard));
        positions.addAll(getRightTopPositions(queen, chessBoard));
        positions.addAll(getLeftBottomPositions(queen, chessBoard));
        positions.addAll(getRightBottomPositions(queen, chessBoard));

        positions.addAll(getLeftPositions(queen, chessBoard));
        positions.addAll(getRightPositions(queen, chessBoard));
        positions.addAll(getTopPositions(queen, chessBoard));
        positions.addAll(getBottomPositions(queen, chessBoard));

        return positions;

    }
}
