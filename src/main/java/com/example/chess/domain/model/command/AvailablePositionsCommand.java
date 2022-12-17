package com.example.chess.domain.model.command;

import com.example.chess.domain.model.positionn.Position;
import lombok.Data;

@Data
public class AvailablePositionsCommand {
    private String userId;
    private Position currentPosition;

}
