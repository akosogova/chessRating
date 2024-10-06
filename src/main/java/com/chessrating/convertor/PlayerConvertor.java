package com.chessrating.convertor;

import com.chessrating.model.Player;
import com.chessrating.repository.entity.PlayerEntity;

public class PlayerConvertor {
    public static Player convertPlayer(PlayerEntity player) {
        return Player.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .email(player.getEmail())
                .chessData(ChessDataConvertor.convertChessData(player.getChessData()))
                .build();
    }

    public static PlayerEntity convertPlayer(Player player) {
        return PlayerEntity.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .email(player.getEmail())
                .chessData(ChessDataConvertor.convertChessData(player.getChessData()))
                .build();
    }
}
