package com.example.chess.domain.model.positionn;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Position implements Serializable {
    private int x;
    private int y;

    public boolean isEligible(){
        return x < 8 && x >= 0 && y < 8 && y >= 0;
    }

}
