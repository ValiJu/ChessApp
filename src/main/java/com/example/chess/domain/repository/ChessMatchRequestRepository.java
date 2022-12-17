package com.example.chess.domain.repository;

import com.example.chess.domain.model.entity.ChessMatchRequestEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChessMatchRequestRepository extends CrudRepository<ChessMatchRequestEntity, String> {

    List<ChessMatchRequestEntity> findAll();

    Optional<ChessMatchRequestEntity> findByUserId(String userId);
}
