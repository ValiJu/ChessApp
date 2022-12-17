package com.example.chess.application;

import com.example.chess.domain.model.command.AvailablePositionsCommand;
import com.example.chess.domain.model.command.MoveCommand;
import com.example.chess.domain.model.positionn.Position;
import com.example.chess.domain.model.ui.UiCheckMateResponse;
import com.example.chess.domain.service.MoveService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chess")
@RequiredArgsConstructor
public class MoveController {
    private final MoveService moveService;

    @PostMapping("makeMove")
    public void makeMove(@RequestBody MoveCommand moveCommand) {
        moveService.makeMove(moveCommand);
    }

    @PostMapping(value = "availablePositions")
    public List<Position> getAvailablePositions(@RequestBody AvailablePositionsCommand requestAvailablePositions) {
        return moveService.getAvailablePositions(requestAvailablePositions);
    }

    @SneakyThrows
    @MessageMapping("/isCheckMate")
    @SendTo("/topic/isCheckMateResponse")
    public UiCheckMateResponse checkMate(@RequestBody String userId) {
        Thread.sleep(100);
        return moveService.isCheckMate(userId);
    }

}
