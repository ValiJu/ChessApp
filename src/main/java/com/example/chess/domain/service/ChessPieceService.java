package com.example.chess.domain.service;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.*;
import com.example.chess.domain.model.positionn.Position;
import com.example.chess.domain.service.handler.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChessPieceService implements ChessPieceHandler {
    private final Map<Class,ChessPieceHandler> handlers;

    public ChessPieceService(){
        handlers = new HashMap<>();
        handlers.put(Pawn.class, new PawnHandler());
        handlers.put(Rook.class, new RookHandler());
        handlers.put(Knight.class, new KnightHandler());
        handlers.put(Bishop.class, new BishopHandler());
        handlers.put(King.class, new KingHandler());
        handlers.put(Queen.class, new QueenHandler());
    }

    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        return handlers.get(chessPiece.getClass()).getAllPossiblePositions(chessPiece,chessBoard);
    }

}
