package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.positionn.Position;

import java.util.List;

public interface ChessPieceHandler {

    List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard);
}
