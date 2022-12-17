package com.example.chess.domain.service;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.command.AvailablePositionsCommand;
import com.example.chess.domain.model.command.MoveCommand;
import com.example.chess.domain.model.entity.ChessMatchEntity;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import com.example.chess.domain.model.ui.UiCheckMateResponse;
import com.example.chess.domain.service.exception.NotUserTurnToMoveException;
import com.example.chess.domain.service.handler.ChessPieceHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.chess.domain.util.Constants.IS_CHECK_MATE;
import static com.example.chess.domain.util.Constants.NOT_CHECK_MATE;
import static com.example.chess.domain.util.UtilChessBoard.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveService {
    private final ChessBoardService chessBoardService;
    private final ChessMatchService chessMatchService;
    private final ChessPieceHandler chessPieceHandler;

    public void makeMove(MoveCommand moveCommand) {
        ChessMatchEntity chessMatchEntity = chessMatchService.getChessMatch(moveCommand.getUserId());
        ChessSquare[][] chessBoardAsMatrix = getChessBoardAsMatrix((Map<Position, ChessSquare>) SerializationUtils.deserialize(chessMatchEntity.getChessBoardAsByteArray()));
        Color currentChessPieceColor = getChessPiece(moveCommand.getCurrentPosition(), chessBoardAsMatrix).getColor();

        if (currentChessPieceColor != chessMatchEntity.getColorToMove()) {
            throw new NotUserTurnToMoveException(String.format("Not users turn to move for command %s", moveCommand));
        }
        chessBoardService.executeMove(moveCommand.getMove(), chessBoardAsMatrix);

        updateChessMatch(chessMatchEntity, chessBoardAsMatrix, currentChessPieceColor);
        printBoard(chessBoardAsMatrix);

    }

    private void updateChessMatch(ChessMatchEntity chessMatchEntity, ChessSquare[][] chessBoardAsMatrix, Color currentChessPieceColor) {
        chessMatchEntity.setColorToMove(switchColor(currentChessPieceColor));
        chessMatchEntity.setChessBoardAsByteArray(SerializationUtils.serialize(getChessBoardAsMap(chessBoardAsMatrix)));
        chessMatchService.saveChessMatch(chessMatchEntity);
    }

    public List<Position> getAvailablePositions(AvailablePositionsCommand command) {
        ChessMatchEntity chessMatchEntity = chessMatchService.getChessMatch(command.getUserId());
        ChessSquare[][] chessBoardAsMatrix = getChessBoardAsMatrix((Map<Position, ChessSquare>) SerializationUtils.deserialize(chessMatchEntity.getChessBoardAsByteArray()));
        ChessPiece chessPiece = getChessPiece(command.getCurrentPosition(), chessBoardAsMatrix);

        if (chessPiece == null) return Collections.emptyList();

        List<Position> allPositions = chessPieceHandler.getAllPossiblePositions(chessPiece, chessBoardAsMatrix);// fara validare sah

        return chessBoardService.removeInCheckPositions(command.getCurrentPosition(), allPositions, chessBoardAsMatrix);
    }

    public UiCheckMateResponse isCheckMate(String userId) {
        ChessMatchEntity chessMatchEntity = chessMatchService.getChessMatch(userId);
        ChessSquare[][] chessBoardAsMatrix = getChessBoardAsMatrix((Map<Position, ChessSquare>) SerializationUtils.deserialize(chessMatchEntity.getChessBoardAsByteArray()));
        String opponentId = userId.equals(chessMatchEntity.getBlackUserId()) ? chessMatchEntity.getWhiteUserId() : chessMatchEntity.getBlackUserId();
        Position kingPosition = userId.equals(chessMatchEntity.getBlackUserId()) ?
                findKingPosition(Color.WHITE, chessBoardAsMatrix) : findKingPosition(Color.BLACK, chessBoardAsMatrix);

        if (chessBoardService.isKingInCheck(kingPosition, chessBoardAsMatrix)) {
            List<Position> allPositions = chessPieceHandler.getAllPossiblePositions(getChessPiece(kingPosition, chessBoardAsMatrix), chessBoardAsMatrix);
            List<Position> validatedPositions = chessBoardService.removeInCheckPositions(kingPosition, allPositions, chessBoardAsMatrix);
            if (validatedPositions.isEmpty()) {
                return new UiCheckMateResponse(userId, opponentId, IS_CHECK_MATE);
            }
        }
        return new UiCheckMateResponse(userId, opponentId, NOT_CHECK_MATE);
    }

}
