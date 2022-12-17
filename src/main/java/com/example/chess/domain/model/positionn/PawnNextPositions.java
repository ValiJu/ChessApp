package com.example.chess.domain.model.positionn;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PawnNextPositions implements Serializable {
    private Position forward;
    private Position forwardLeft;
    private Position forwardRight;
    private Position doubleForward;

}
