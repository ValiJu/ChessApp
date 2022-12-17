package com.example.chess.domain.model.ui;

import com.example.chess.domain.model.positionn.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UiChessPiece {
  private Position position;
  private String chessPieceColor;
  private String chessPieceName;

}
