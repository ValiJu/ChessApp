package com.example.chess.domain.model.command;

import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.entity.ChessMatchRequestEntity;
import lombok.Data;

@Data
public class FindChessMatchCommand {
    private String userId;
    private Color desiredColor;

    public ChessMatchRequestEntity toEntity() {
        ChessMatchRequestEntity entity = new ChessMatchRequestEntity();
        entity.setUserId(userId);
        entity.setDesiredColor(desiredColor);
        return entity;
    }

}
