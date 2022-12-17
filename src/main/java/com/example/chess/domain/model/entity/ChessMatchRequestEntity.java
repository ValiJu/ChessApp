package com.example.chess.domain.model.entity;

import com.example.chess.domain.model.enums.Color;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash
public class ChessMatchRequestEntity {
    @Id
    private String id;
    @Indexed
    private String userId;
    private Color desiredColor;
}
