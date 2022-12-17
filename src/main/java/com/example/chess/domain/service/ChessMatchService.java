package com.example.chess.domain.service;

import com.example.chess.domain.model.chessBoard.ChessPiece;
import com.example.chess.domain.model.chessBoard.ChessSquare;
import com.example.chess.domain.model.chessMatch.ChessMatch;
import com.example.chess.domain.model.command.FindChessMatchCommand;
import com.example.chess.domain.model.entity.ChessMatchEntity;
import com.example.chess.domain.model.entity.ChessMatchRequestEntity;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.positionn.Position;
import com.example.chess.domain.model.ui.UiChessMatchPiecePositions;
import com.example.chess.domain.model.ui.UiChessPiece;
import com.example.chess.domain.repository.ChessMatchRepository;
import com.example.chess.domain.repository.ChessMatchRequestRepository;
import com.example.chess.domain.service.exception.ChessMatchNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.*;

import static com.example.chess.domain.util.UtilChessBoard.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChessMatchService {
    private final ChessMatchRequestRepository chessMatchRequestRepository;
    private final ChessMatchRepository chessMatchRepository;

    public ChessMatch processChessMatchRequest(FindChessMatchCommand findChessMatchCommand) {
        Optional<ChessMatchRequestEntity> chessMatchRequestEntity = chessMatchRequestRepository.findByUserId(findChessMatchCommand.getUserId());
        if (chessMatchRequestEntity.isPresent()) {
            updateChessMatchRequest(findChessMatchCommand, chessMatchRequestEntity.get());
            return null;
        }
        return getPossibleMatch(findChessMatchCommand);
    }

    private ChessMatch getPossibleMatch(FindChessMatchCommand findChessMatchCommand) {
        ChessMatchRequestEntity opponentChessMatchRequest = getOpponentChessMatchRequest(findChessMatchCommand);
        if (opponentChessMatchRequest == null) {
            chessMatchRequestRepository.save(findChessMatchCommand.toEntity());
            return null;
        }
        ChessMatch chessMatch = createChessMatch(findChessMatchCommand, opponentChessMatchRequest);
        chessMatchRepository.save(chessMatch.toEntity());
        chessMatchRequestRepository.deleteById(opponentChessMatchRequest.getId());
        return chessMatch;
    }

    private ChessMatchRequestEntity getOpponentChessMatchRequest(FindChessMatchCommand findChessMatchCommand) {
        return chessMatchRequestRepository.findAll()
                .stream()
                .filter(val -> val.getDesiredColor() != findChessMatchCommand.getDesiredColor())
                .findAny()
                .orElse(null);
    }

    private void updateChessMatchRequest(FindChessMatchCommand findChessMatchCommand, ChessMatchRequestEntity chessMatchRequestEntity) {
        chessMatchRequestEntity.setDesiredColor(findChessMatchCommand.getDesiredColor());
        chessMatchRequestRepository.save(chessMatchRequestEntity);
    }

    private ChessMatch createChessMatch(FindChessMatchCommand findChessMatchCommand, ChessMatchRequestEntity opponentChessMatchRequest) {
        if (findChessMatchCommand.getDesiredColor() == Color.WHITE) {
            return new ChessMatch(findChessMatchCommand.getUserId(),
                    opponentChessMatchRequest.getUserId(), Color.WHITE,
                    createInitialChessBoard());
        }
        return new ChessMatch(opponentChessMatchRequest.getUserId(),
                findChessMatchCommand.getUserId(), Color.WHITE,
                createInitialChessBoard());
    }

    public UiChessMatchPiecePositions getChessMatchPiecePositions(String userId) {
        UiChessMatchPiecePositions wrapper = new UiChessMatchPiecePositions();
        ChessMatchEntity chessMatchEntity = getChessMatch(userId);
        wrapper.setPieces(getCachedChessBoardPieces(userId));
        wrapper.setUsers(Arrays.asList(chessMatchEntity.getBlackUserId(), chessMatchEntity.getWhiteUserId()));
        return wrapper;
    }

    public List<UiChessPiece> getCachedChessBoardPieces(String userId) {
        ChessMatchEntity chessMatchEntity = getChessMatch(userId);
        ChessSquare[][] chessBoardAsMatrix = getChessBoardAsMatrix((Map<Position, ChessSquare>) SerializationUtils.deserialize(chessMatchEntity.getChessBoardAsByteArray()));
        return getPieces(chessBoardAsMatrix);
    }

    public List<String> quitChessMatch(String userId) {
        List<String> userIds = new ArrayList<>();
        ChessMatchEntity chessMatchEntity = getChessMatch(userId);
        userIds.add(chessMatchEntity.getBlackUserId());
        userIds.add(chessMatchEntity.getWhiteUserId());
        chessMatchRepository.deleteById(chessMatchEntity.getId());
        return userIds;
    }

    public void resetMatch(String userId) {
        ChessMatchEntity chessMatchEntity = getChessMatch(userId);
        chessMatchEntity.setChessBoardAsByteArray(SerializationUtils.serialize(getChessBoardAsMap(createInitialChessBoard())));
        chessMatchRepository.save(chessMatchEntity);
    }

    public Color getColorToMove(String userId) {
        ChessMatchEntity chessMatchEntity = getChessMatch(userId);
        return chessMatchEntity.getColorToMove();
    }

    public String getUserColor(String userId) {
        Optional<ChessMatchEntity> chessMatchEntity = chessMatchRepository.findChessMatchByWhiteUserId(userId);
        if (chessMatchEntity.isPresent()) {
            return Color.WHITE.getLabel();
        }
        return Color.BLACK.getLabel();
    }

    public void saveChessMatch(ChessMatchEntity chessMatchEntity) {
        chessMatchRepository.save(chessMatchEntity);
    }

    public boolean isInMatch(String userId) {
        ChessMatchEntity chessMatchEntity = findChessMatchByUserId(userId)
                .stream()
                .findAny()
                .orElse(null);
        return chessMatchEntity != null;
    }

    private List<UiChessPiece> getPieces(ChessSquare[][] chessBoard) {
        List<UiChessPiece> chessPieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece chessPiece = chessBoard[i][j].getChessPiece();
                if (chessPiece != null) {
                    chessPieces.add(new UiChessPiece(chessPiece.getPosition(), chessPiece.getColor().getLabel(), chessPiece.getClass().getSimpleName()));
                } else {
                    chessPieces.add(new UiChessPiece(new Position(i, j), "", ""));
                }
            }
        }
        return chessPieces;
    }

    public Optional<ChessMatchEntity> findChessMatchByUserId(String userId) {
        Optional<ChessMatchEntity> chessMatchEntity = chessMatchRepository.findChessMatchByWhiteUserId(userId);
        if (chessMatchEntity.isPresent()) {
            return chessMatchEntity;
        }
        return chessMatchRepository.findChessMatchByBlackUserId(userId);
    }

    public ChessMatchEntity getChessMatch(String userId) {
        return findChessMatchByUserId(userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new ChessMatchNotFoundException(
                        String.format("Chess match not found in cache for userId: %s", userId)));
    }

}
