package com.example.chess.domain.util;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessPieceFactory;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessBoard.pieces.King;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import com.example.chess.domain.model.positionn.StartPositions;

import java.util.*;

public class UtilChessBoard {

    //////////////////// Initial setup

    public static ChessSquare[][] createInitialChessBoard() {
        ChessSquare[][] chessBoard = new ChessSquare[8][8];
        createChessSquares(chessBoard);
        addChessPieces(chessBoard, Color.BLACK);
        addChessPieces(chessBoard, Color.WHITE);
        //printBoard(chessBoard);
        return chessBoard;
    }

    public static void createChessSquares(ChessSquare[][] chessBoard) {
        Color currentColor = Color.WHITE;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = new ChessSquare(currentColor);
                currentColor = UtilChessBoard.switchColor(currentColor);
            }
            currentColor = UtilChessBoard.switchColor(currentColor);
        }
    }

    public static void addChessPieces(ChessSquare[][] chessBoard, Color color) {
        int startLine, stopLine;
        if (color == Color.BLACK) {
            startLine = 0;
            stopLine = 2;
        } else {
            startLine = 6;
            stopLine = 8;
        }

        for (int i = startLine; i < stopLine; i++) {
            for (int j = 0; j < 8; j++) {
                Position currentPosition = new Position(i, j);
                addChessPiece(chessBoard, color, currentPosition);
            }
        }
    }

    public static void addChessPiece(ChessSquare[][] chessBoard, Color color, Position position) {
        String pieceType = StartPositions.getPieceTypeByPosition(position);
        getChessSquare(position, chessBoard).setChessPiece(ChessPieceFactory.getChessPiece(pieceType, position, color));
    }

    ////////////// KingMethods

    public static Position findKingPosition(Color color, ChessSquare[][] chessBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece chessPiece = chessBoard[i][j].getChessPiece();
                if (chessPiece instanceof King && chessPiece.getColor() == color) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }
    /////////////////////// Get free positions to move

    public static List<Position> getLeftTopPositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = (chessPiece.getPosition().getX() - 1), j = chessPiece.getPosition().getY() - 1; i >= 0 && j >= 0; i--, j--) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(i, j))) break;
        }
        return positions;
    }

    public static List<Position> getLeftBottomPositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = (chessPiece.getPosition().getX() + 1), j = chessPiece.getPosition().getY() - 1; i < 8 && j >= 0; i++, j--) {
            if (reachEnemy(chessPiece,chessBoard, positions, new Position(i, j))) break;
        }
        return positions;
    }

    public static List<Position> getRightBottomPositions(ChessPiece chessPiece,ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = (chessPiece.getPosition().getX() + 1), j = chessPiece.getPosition().getY() + 1; i < 8 && j < 8; i++, j++) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(i, j))) break;
        }
        return positions;
    }

    public static List<Position> getRightTopPositions(ChessPiece chessPiece,ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = chessPiece.getPosition().getX() - 1, j = chessPiece.getPosition().getY() + 1; i >= 0 && j < 8; i--, j++) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(i, j))) break;
        }
        return positions;
    }

    public static List<Position> getLeftPositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = chessPiece.getPosition().getY() - 1; i >= 0; i--) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(chessPiece.getPosition().getX(), i))) break;
        }
        return positions;
    }

    public static List<Position> getRightPositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = chessPiece.getPosition().getY() + 1; i < 8; i++) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(chessPiece.getPosition().getX(), i))) break;
        }
        return positions;
    }

    public static List<Position> getTopPositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = chessPiece.getPosition().getX() - 1; i >= 0; i--) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(i, chessPiece.getPosition().getY()))) break;
        }
        return positions;
    }

    public static List<Position> getBottomPositions(ChessPiece chessPiece, ChessSquare[][] chessBoard) {
        List<Position> positions = new ArrayList<>();
        for (int i = chessPiece.getPosition().getX() + 1; i < 8; i++) {
            if (reachEnemy(chessPiece, chessBoard, positions, new Position(i, chessPiece.getPosition().getY()))) break;
        }
        return positions;
    }

    public static List<Position> getKnightPositions(Position position) {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(position.getX() - 2, position.getY() + 1));
        positions.add(new Position(position.getX() - 1, position.getY() + 2));
        positions.add(new Position(position.getX() - 2, position.getY() - 1));
        positions.add(new Position(position.getX() - 1, position.getY() - 2));
        positions.add(new Position(position.getX() + 1, position.getY() - 2));
        positions.add(new Position(position.getX() + 2, position.getY() - 1));
        positions.add(new Position(position.getX() + 1, position.getY() + 2));
        positions.add(new Position(position.getX() + 2, position.getY() + 1));
        return positions;
    }

    public static List<Position> getKingPositions(Position position) {
        return Arrays.asList(
                new Position(position.getX(), position.getY() - 1),
                new Position(position.getX(), position.getY() + 1),
                new Position(position.getX() - 1, position.getY()),
                new Position(position.getX() + 1, position.getY()),
                new Position(position.getX() + 1, position.getY() + 1),
                new Position(position.getX() - 1, position.getY() - 1),
                new Position(position.getX() + 1, position.getY() - 1),
                new Position(position.getX() - 1, position.getY() + 1)
        );
    }

    private static boolean reachEnemy(ChessPiece chessPiece, ChessSquare[][] chessBoard, List<Position> positions, Position position) {
        if (!position.isEligible()) return false;

        if (chessBoard[position.getX()][position.getY()].containsNothing()) {
            positions.add(position);
        }
        if (chessBoard[position.getX()][position.getY()].containsFriend(chessPiece.getColor())) {
            return true;
        }
        if (chessBoard[position.getX()][position.getY()].containsEnemy(chessPiece.getColor())) {
            positions.add(position);
            return true;
        }
        return false;
    }

    /////////////////////// UsefulMethods

    public static void printBoard(ChessSquare[][] chessBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].getChessPiece() != null) {
                    System.out.print((chessBoard[i][j].getChessPiece().getClass().getSimpleName() + chessBoard[i][j].getChessPiece().getColor().getLabel() + "         ").substring(0, 8));
                } else {
                    System.out.print(chessBoard[i][j].getColor().getLabel() + "       ");
                }
            }
            System.out.println("");
        }
    }

    public static List<ChessPiece> getEnemyPieces(Color color, ChessSquare[][] chessBoard) {
        List<ChessPiece> enemyChessPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].containsEnemy(color) ) {     //&& !(chessBoard[i][j].getChessPiece() instanceof King)) {
                    enemyChessPieces.add(chessBoard[i][j].getChessPiece());
                }
            }
        }
        return enemyChessPieces;
    }

    public static Color switchColor(Color color) {
        return color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public static ChessSquare getChessSquare(Position position, ChessSquare[][] chessBoard) {
        return chessBoard[position.getX()][position.getY()];
    }

    public static ChessPiece getChessPiece(Position position, ChessSquare[][] chessBoard) {
        return getChessSquare(position, chessBoard).getChessPiece();
    }

    public static Color getChessPieceColor(Position position, ChessSquare[][] chessBoard) {
        ChessPiece chessPiece = getChessPiece(position, chessBoard);
        return chessPiece != null ? chessPiece.getColor() : Color.NO_COLOR;
    }

    public static Map<Position, ChessSquare> getChessBoardAsMap(ChessSquare[][] chessBoard) {
        Map<Position, ChessSquare> chessBoardAsMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoardAsMap.put(new Position(i, j), chessBoard[i][j]);
            }
        }
        return chessBoardAsMap;
    }

    public static ChessSquare[][] getChessBoardAsMatrix(Map<Position, ChessSquare> chessBoardAsMap) {
        ChessSquare[][] chessBoard = new ChessSquare[8][8];
        chessBoardAsMap.forEach((k, v) -> {
            chessBoard[k.getX()][k.getY()] = v;
        });
        return chessBoard;
    }
}
