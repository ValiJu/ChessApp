package com.example.chess.domain.repository;

import com.example.chess.domain.model.entity.ChessMatchEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChessMatchRepository extends CrudRepository<ChessMatchEntity, String> {
    Optional<ChessMatchEntity> findChessMatchByWhiteUserId(String userId);
    Optional<ChessMatchEntity> findChessMatchByBlackUserId(String userId);
}
