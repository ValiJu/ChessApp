package com.example.chess.domain.model.chessMatch;

import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.entity.ChessMatchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.SerializationUtils;

import static com.example.chess.domain.util.UtilChessBoard.getChessBoardAsMap;

@Data
@AllArgsConstructor
public class ChessMatch {
    private String whiteUserId;
    private String blackUserId;
    private Color colorToMove;
    private ChessSquare[][] chessBoard;


    public ChessMatchEntity toEntity() {
        ChessMatchEntity entity = new ChessMatchEntity();
        entity.setWhiteUserId(whiteUserId);
        entity.setBlackUserId(blackUserId);
        entity.setChessBoardAsByteArray(SerializationUtils.serialize(getChessBoardAsMap(chessBoard)));
        entity.setColorToMove(colorToMove);
        return entity;
    }
}
