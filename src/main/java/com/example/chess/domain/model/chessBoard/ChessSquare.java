package com.example.chess.domain.model.chessBoard;


import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class ChessSquare implements Serializable {
    private ChessPiece chessPiece;
    private final Color color;

    public ChessSquare(Color color){
        this.color = color;
    }

    public boolean containsEnemy(Color color){
        if(chessPiece == null) return false;
        return chessPiece.getColor() != color;
    }
    public boolean containsFriend(Color color){
        if(chessPiece == null) return false;
        return chessPiece.getColor() == color;
    }
    public boolean containsNothing(){
        return chessPiece == null;
    }

    public void updateChessPiecePosition(Position position){
        if(chessPiece!=null) {
            chessPiece.getPosition().setX(position.getX());
            chessPiece.getPosition().setY(position.getY());
        }
    }
    public Color getChessPieceColor(){
        return chessPiece.getColor();
    }

}
