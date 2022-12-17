package com.example.chess.domain.service.handler;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.Rook;
import com.example.chess.domain.model.positionn.Position;

import java.util.ArrayList;
import java.util.List;

import static com.example.chess.domain.util.UtilChessBoard.*;

public class RookHandler implements ChessPieceHandler {
    @Override
    public List<Position> getAllPossiblePositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        Rook rook = (Rook) chessPiece;
        List<Position> positions = new ArrayList<>();
        positions.addAll(getLeftPositions(rook, chessBoard));
        positions.addAll(getRightPositions(rook, chessBoard));
        positions.addAll(getTopPositions(rook, chessBoard));
        positions.addAll(getBottomPositions(rook, chessBoard));
        return positions;

    }

//    public List<Position> getLeftPositions(Rook rook, ChessSquare[][] chessBoard) {
//        List<Position> positions = new ArrayList<>();
//        for (int i = rook.getPosition().getY() - 1; i >= 0; i--) {
//            if (reachEnemy(rook, chessBoard, positions, new Position(rook.getPosition().getX(), i))) break;
//        }
//        return positions;
//    }
//
//    public List<Position> getRightPositions(Rook rook, ChessSquare[][] chessBoard) {
//        List<Position> positions = new ArrayList<>();
//        for (int i = rook.getPosition().getY() + 1; i < 8; i++) {
//            if (reachEnemy(rook, chessBoard, positions, new Position(rook.getPosition().getX(), i))) break;
//        }
//        return positions;
//    }
//
//    public List<Position> getTopPositions(Rook rook, ChessSquare[][] chessBoard) {
//        List<Position> positions = new ArrayList<>();
//        for (int i = rook.getPosition().getX() - 1; i >= 0; i--) {
//            if (reachEnemy(rook, chessBoard, positions, new Position(i, rook.getPosition().getY()))) break;
//        }
//        return positions;
//    }
//
//    public List<Position> getBottomPositions(Rook rook, ChessSquare[][] chessBoard) {
//        List<Position> positions = new ArrayList<>();
//        for (int i = rook.getPosition().getX() + 1; i < 8; i++) {
//            if (reachEnemy(rook, chessBoard, positions, new Position(i, rook.getPosition().getY()))) break;
//        }
//        return positions;
//    }
//
//    private boolean reachEnemy(Rook rook, ChessSquare[][] chessBoard, List<Position> positions, Position position) {
//        Position nextPosition = new Position(position.getX(), position.getY());
//        if (!nextPosition.isEligible()) return false;
//
//        if (chessBoard[nextPosition.getX()][nextPosition.getY()].containsNothing()) {
//            positions.add(nextPosition);
//        }
//        if (chessBoard[nextPosition.getX()][nextPosition.getY()].containsFriend(rook.getColor())) {
//            return true;
//        }
//        if (chessBoard[nextPosition.getX()][nextPosition.getY()].containsEnemy(rook.getColor())) {
//            positions.add(nextPosition);
//            return true;
//        }
//        return false;
//    }
}
