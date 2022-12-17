package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.Bishop;
import com.example.chess.domain.model.positionn.Position;

import java.util.ArrayList;
import java.util.List;

import static com.example.chess.domain.util.UtilChessBoard.*;

public class BishopHandler implements ChessPieceHandler {

    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        Bishop bishop = (Bishop) chessPiece;
        List<Position> positions = new ArrayList<>();
        positions.addAll(getLeftTopPositions(bishop, chessBoard));
        positions.addAll(getRightTopPositions(bishop, chessBoard));
        positions.addAll(getLeftBottomPositions(bishop, chessBoard));
        positions.addAll(getRightBottomPositions(bishop, chessBoard));
        return positions;
    }

}
