package com.chessrating.convertor;

import com.chessrating.api.model.PlayerRatingResponse;
import com.chessrating.config.GlobalConfig;
import com.chessrating.repository.entity.PlayerEntity;

import java.util.Objects;

public class PlayerToPlayerRatingResponseConvertor {

    public static PlayerRatingResponse convertPlayerToPlayerRatingResponse(PlayerEntity player) {
        return PlayerRatingResponse.builder()
                .id(player.getId())
                .email(player.getEmail())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .rating(Objects.isNull(player.getChessData()) ? GlobalConfig.INITIAL_RATING : player.getChessData().getRating())
                .build();
    }
}
