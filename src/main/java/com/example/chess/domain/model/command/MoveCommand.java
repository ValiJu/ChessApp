package com.example.chess.domain.model.command;

import com.example.chess.domain.model.chessMatch.Move;
import com.example.chess.domain.model.positionn.Position;
import lombok.Data;

@Data
public class MoveCommand {
    private String userId;
    private Move move;

    public Position getCurrentPosition(){
        return move.getCurrentPosition();
    }
}
