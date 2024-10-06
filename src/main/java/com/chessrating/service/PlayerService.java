package com.chessrating.service;

import com.chessrating.api.model.PlayerRatingResponse;
import com.chessrating.api.model.RatingUpdateRequest;
import com.chessrating.config.GlobalConfig;
import com.chessrating.convertor.PlayerConvertor;
import com.chessrating.convertor.PlayerToPlayerRatingResponseConvertor;
import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;
import com.chessrating.model.Player;
import com.chessrating.repository.PlayerRepository;
import com.chessrating.repository.entity.PlayerEntity;
import com.chessrating.validator.ChessDataValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private static final Logger LOG = LoggerFactory.getLogger(PlayerService.class);
    private final PlayerRepository playerRepository;
    private final ChessDataValidator chessDataValidator;

    public List<PlayerRatingResponse> getAllPlayersRating() {
        return playerRepository.findAll().stream()
                .map(PlayerToPlayerRatingResponseConvertor::convertPlayerToPlayerRatingResponse)
                .collect(Collectors.toList());
    }

    public void createPlayer(Player player) throws PlayerValidationException {
        if (Objects.isNull(player)) {
            LOG.error("[PlayerService::createPlayer] Cannot create empty player");
            throw new PlayerValidationException("Cannot create empty player");
        }

        if (Objects.isNull(player.getId()) || player.getId().isEmpty()) {
            LOG.error("[PlayerService::createPlayer] Please set player id");
            throw new PlayerValidationException("Please set player id");
        }

        if (playerRepository.findById(player.getId()).isPresent()) {
            LOG.error("[PlayerService::createPlayer] A player with the given id {} already exists", player.getId());
            throw new PlayerValidationException("A player with the given id " + player.getId() + " already exists, please provide a different id");
        }

        if (Objects.isNull(player.getChessData())
                || player.getChessData().getRating() == 0) {
            LOG.warn("[PlayerService::createPlayer] The initial rating has not been set and will be set to {}", GlobalConfig.INITIAL_RATING);
            player.setChessData(ChessData.builder().rating(GlobalConfig.INITIAL_RATING).build());
        }

        chessDataValidator.validate(player.getChessData());
        playerRepository.save(PlayerConvertor.convertPlayer(player));
        LOG.info("[PlayerService::createPlayer] Player {} has been added", player);
    }

    public Player getPlayerById(String id) throws PlayerValidationException {
        Optional<PlayerEntity> player = playerRepository.findById(id);
        if (!player.isPresent()) {
            LOG.error("[PlayerService::getPlayerById] Cannot find player with id {}", id);
            throw new PlayerValidationException("Cannot find player with id " + id);
        }
        return PlayerConvertor.convertPlayer(player.get());
    }

    public void updateRating(String id, RatingUpdateRequest ratingUpdateRequest) throws PlayerValidationException {
        Player player = getPlayerById(id);

        if (Objects.isNull(ratingUpdateRequest)) {
            LOG.error("[PlayerService::getPlayerById] Cannot find player with id {}", id);
            throw new PlayerValidationException("Please provide a rating value to update");
        }

        if (Objects.isNull(player.getChessData())) {
            player.setChessData(ChessData.builder()
                    .rating(GlobalConfig.INITIAL_RATING)
                    .build());
        }
        player.getChessData().setRating(ratingUpdateRequest.getRating());
        chessDataValidator.validate(player.getChessData());
        playerRepository.save(PlayerConvertor.convertPlayer(player));
        LOG.info("[PlayerService::createPlayer] The rating of the player with id {} has been changed to {}",
                player.getId(), ratingUpdateRequest.getRating());
    }

    public void deletePlayer(String id) throws PlayerValidationException {
        Player player = getPlayerById(id);
        playerRepository.delete(PlayerConvertor.convertPlayer(player));
        LOG.info("[PlayerService::deletePlayer] Player {} has been deleted", player);
    }
}
