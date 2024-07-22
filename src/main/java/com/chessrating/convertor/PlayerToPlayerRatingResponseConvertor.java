package com.chessrating.convertor;

import com.chessrating.api.model.PlayerRatingResponse;
import com.chessrating.config.GlobalConfig;
import com.chessrating.model.Player;

import java.util.Objects;

public class PlayerToPlayerRatingResponseConvertor {

    public static PlayerRatingResponse convertPlayerToPlayerRatingResponse(Player player) {
        PlayerRatingResponse playerRatingResponse = new PlayerRatingResponse();
        playerRatingResponse.setId(player.getId());
        playerRatingResponse.setFirstName(player.getFirstName());
        playerRatingResponse.setLastName(player.getLastName());
        playerRatingResponse.setEmail(player.getEmail());
        if (Objects.isNull(player.getChessData())) {
            playerRatingResponse.setRating(GlobalConfig.INITIAL_RATING);
        } else {
            playerRatingResponse.setRating(player.getChessData().getRating());
        }
        return playerRatingResponse;
    }
}
