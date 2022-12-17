package com.example.chess.domain.model.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UiUser {
    private String userId;
    private String color;
    private boolean inMatch;
}
