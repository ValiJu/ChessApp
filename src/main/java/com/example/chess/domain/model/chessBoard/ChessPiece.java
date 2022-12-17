package com.example.chess.domain.model.chessBoard;

import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChessPiece implements Serializable {
    protected Position position;
    protected Color color;

    public ChessPiece(Position position, Color color){
        this.color = color;
        this.position = position;
    }

}
