package com.example.chess.domain.model.enums;

public enum Color {
    WHITE("W"),
    BLACK("B"),
    NO_COLOR("");

    private String label;

    Color(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
