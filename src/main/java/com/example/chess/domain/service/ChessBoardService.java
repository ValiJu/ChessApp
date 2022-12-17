package com.example.chess.domain.service;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.Pawn;
import com.example.chess.domain.model.chessMatch.Move;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.chess.domain.util.UtilChessBoard.*;

@Component
@Data
@RequiredArgsConstructor
public class ChessBoardService {
    private final ChessPieceService chessPieceService;

    public List<Position> removeInCheckPositions(Position currentPosition, List<Position> positions, ChessSquare[][] chessBoard) {
        if (positions.isEmpty()) return positions;
        List<Position> availablePositions = new ArrayList<>(positions);
        positions.forEach(position -> {
            Move move = new Move(currentPosition, position);
            ChessPiece possibleEnemy = getChessPiece(move.getDesiredPosition(), chessBoard);
            executeMove(move, chessBoard);
            if (isKingInCheck(position, chessBoard)) {
                availablePositions.remove(position);
            }
            revertMove(move, possibleEnemy, chessBoard);
        });
        return availablePositions;
    }

    public void executeMove(Move move, ChessSquare[][] chessBoard) {
        moveChessPiece(move, chessBoard);
        removeChessPieceFromCurrentPosition(move.getCurrentPosition(), chessBoard);
        updateChessPiecePosition(move.getDesiredPosition(), chessBoard);
    }

    private void revertMove(Move move, ChessPiece possibleEnemy, ChessSquare[][] chessBoard) {
        moveChessPiece(new Move(move.getDesiredPosition(), move.getCurrentPosition()), chessBoard);
        getChessSquare(move.getDesiredPosition(), chessBoard).setChessPiece(possibleEnemy);
        updateChessPiecePosition(move.getCurrentPosition(), chessBoard);

    }

    private void updateChessPiecePosition(Position position, ChessSquare[][] chessBoard) {
        getChessSquare(position, chessBoard).updateChessPiecePosition(position);
    }

    private void removeChessPieceFromCurrentPosition(Position position, ChessSquare[][] chessBoard) {
        getChessSquare(position, chessBoard).setChessPiece(null);
    }

    private void moveChessPiece(Move move, ChessSquare[][] chessBoard) {
        if (getChessPiece(move.getCurrentPosition(), chessBoard) instanceof Pawn) {
            movePawn(move, chessBoard);
            return;
        }
        getChessSquare(move.getDesiredPosition(), chessBoard)
                .setChessPiece(getChessPiece(move.getCurrentPosition(), chessBoard));
    }

    private void movePawn(Move move, ChessSquare[][] chessBoard) {
        Pawn freshPawn = new Pawn(move.getDesiredPosition(), getChessPieceColor(move.getCurrentPosition(), chessBoard));
        getChessSquare(move.getDesiredPosition(), chessBoard).setChessPiece(freshPawn);
    }

    public boolean isKingInCheck(Position position, ChessSquare[][] chessBoard) {
        Color kingColor = getChessPieceColor(position, chessBoard);
        List<Position> inCheckPositions = new ArrayList<>();
        List<ChessPiece> enemyChessPieces = getEnemyPieces(kingColor, chessBoard);
        Position kingPosition = findKingPosition(kingColor, chessBoard);

        enemyChessPieces.forEach(chessPiece -> {
            List<Position> allPossiblePositions = chessPieceService.getAllPossiblePositions(chessPiece, chessBoard);
            inCheckPositions.addAll(allPossiblePositions);
        });
        return inCheckPositions.contains(kingPosition);

    }

}

