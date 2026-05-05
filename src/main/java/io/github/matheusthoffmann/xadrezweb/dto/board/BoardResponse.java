package io.github.matheusthoffmann.xadrezweb.dto.board;

public record BoardResponse(
        String[][] board,
        String currentPlayer,
        boolean check,
        boolean checkmate
) {}