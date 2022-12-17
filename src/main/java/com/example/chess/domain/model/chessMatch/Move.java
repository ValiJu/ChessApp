package com.example.chess.domain.model.chessMatch;

import com.example.chess.domain.model.positionn.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Move {
    private Position currentPosition;
    private Position desiredPosition;
}
