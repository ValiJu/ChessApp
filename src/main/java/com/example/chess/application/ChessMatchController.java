package com.example.chess.application;

import com.example.chess.domain.model.chessMatch.ChessMatch;
import com.example.chess.domain.model.command.FindChessMatchCommand;
import com.example.chess.domain.model.enums.Color;
import com.example.chess.domain.model.ui.UiChessMatch;
import com.example.chess.domain.model.ui.UiChessMatchPiecePositions;
import com.example.chess.domain.model.ui.UiChessPiece;
import com.example.chess.domain.model.ui.UiUser;
import com.example.chess.domain.service.ChessMatchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.chess.domain.util.Constants.NO_USER;

@Slf4j
@RestController
@RequestMapping("/chess")
@RequiredArgsConstructor
public class ChessMatchController {
    private final ChessMatchService chessMatchService;

    @MessageMapping("/chessPiecesInfo")
    @SendTo("/topic/chessPiecesInfoResponse")
    @SneakyThrows
    public UiChessMatchPiecePositions getChessPiecesInfo(String userId) {
        Thread.sleep(100); // this endpoint should be called after makeMove endpoint, but it's not because webSocket request is faster then REST
        return chessMatchService.getChessMatchPiecePositions(userId);
    }

    @MessageMapping("/searchMatch")
    @SendTo("/topic/searchMatchResponse")
    public UiChessMatch searchMatch(FindChessMatchCommand findChessMatchCommand) {
        ChessMatch chessMatch = chessMatchService.processChessMatchRequest(findChessMatchCommand);
        if (chessMatch == null) {
            return new UiChessMatch(NO_USER, NO_USER, Color.WHITE);
        }
        return new UiChessMatch(chessMatch.getWhiteUserId(), chessMatch.getBlackUserId(), chessMatch.getColorToMove());
    }

    @MessageMapping("/quitMatch")
    @SendTo("/topic/quitMatchResponse")
    @SneakyThrows
    public List<String> quitMatch(String userId) {
        return chessMatchService.quitChessMatch(userId);
    }

    @PostMapping("getChessBoard")
    public List<UiChessPiece> getChessBoard(@RequestBody String userId) {
        return chessMatchService.getCachedChessBoardPieces(userId);
    }

    @PostMapping("getUserLogged")
    public UiUser getUserLogged() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String userColor = chessMatchService.getUserColor(userId);
        return new UiUser(userId, userColor, chessMatchService.isInMatch(userId));
    }

    @PostMapping("/getColorToMove")
    public Color getColorToMove(@RequestBody String userId) {
        return chessMatchService.getColorToMove(userId);
    }

    @PostMapping("/resetMatch")
    public void reset(@RequestBody String userId) {
        chessMatchService.resetMatch(userId);
    }


}
