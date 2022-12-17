package com.example.chess.domain.model.positionn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartPositions {

    public static final String ROOK = "rook";
    public static final String BISHOP = "bishop";
    public static final String QUEEN = "queen";
    public static final String KNIGHT = "knight";
    public static final String KING = "king";
    public static final String PAWN = "pawn";
    public static final String NONE = "none";

    private static final Map<String, List<Position>> positions = new HashMap<>();

    public static String getPieceTypeByPosition(Position position) {
        return positions.keySet()
                .stream()
                .filter(k -> positions.get(k).contains(position))
                .findFirst()
                .orElse(NONE);
    }


    static {
        positions.put(ROOK, getRookPositions());
        positions.put(BISHOP, getBishopPositions());
        positions.put(QUEEN, getQueenPositions());
        positions.put(KING, getKingPositions());
        positions.put(KNIGHT, getKnightPositions());
        positions.put(PAWN, getPawnPositions());
    }

    private static List<Position> getRookPositions() {
        List<Position> rookPositions = new ArrayList<>();
        rookPositions.add(new Position(0, 0));
        rookPositions.add(new Position(0, 7));
        rookPositions.add(new Position(7, 0));
        rookPositions.add(new Position(7, 7));
        return rookPositions;
    }

    private static List<Position> getKnightPositions() {
        List<Position> knightPositions = new ArrayList<>();
        knightPositions.add(new Position(0, 1));
        knightPositions.add(new Position(7, 1));
        knightPositions.add(new Position(0, 6));
        knightPositions.add(new Position(7, 6));
        return knightPositions;
    }

    private static List<Position> getBishopPositions() {
        List<Position> bishopPositions = new ArrayList<>();
        bishopPositions.add(new Position(0, 2));
        bishopPositions.add(new Position(7, 2));
        bishopPositions.add(new Position(0, 5));
        bishopPositions.add(new Position(7, 5));
        return bishopPositions;
    }

    private static List<Position> getQueenPositions() {
        List<Position> queenPositions = new ArrayList<>();
        queenPositions.add(new Position(7, 3));
        queenPositions.add(new Position(0, 3));
        return queenPositions;
    }

    private static List<Position> getKingPositions() {
        List<Position> kingPositions = new ArrayList<>();
        kingPositions.add(new Position(7, 4));
        kingPositions.add(new Position(0, 4));
        return kingPositions;
    }

    private static List<Position> getPawnPositions() {
        List<Position> pawnPositions = new ArrayList<>();
        pawnPositions.add(new Position(1, 0));
        pawnPositions.add(new Position(1, 1));
        pawnPositions.add(new Position(1, 2));
        pawnPositions.add(new Position(1, 3));
        pawnPositions.add(new Position(1, 4));
        pawnPositions.add(new Position(1, 5));
        pawnPositions.add(new Position(1, 6));
        pawnPositions.add(new Position(1, 7));

        pawnPositions.add(new Position(6, 0));
        pawnPositions.add(new Position(6, 1));
        pawnPositions.add(new Position(6, 2));
        pawnPositions.add(new Position(6, 3));
        pawnPositions.add(new Position(6, 4));
        pawnPositions.add(new Position(6, 5));
        pawnPositions.add(new Position(6, 6));
        pawnPositions.add(new Position(6, 7));
        return pawnPositions;
    }
}
