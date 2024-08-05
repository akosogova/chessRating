package com.chessrating.service;

import com.chessrating.api.model.PlayerRatingResponse;
import com.chessrating.api.model.RatingUpdateRequest;
import com.chessrating.config.GlobalConfig;
import com.chessrating.convertor.PlayerToPlayerRatingResponseConvertor;
import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;
import com.chessrating.model.Player;
import com.chessrating.repository.PlayerRepository;
import com.chessrating.validator.ChessDataValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    private final ChessDataValidator chessDataValidator;

    public PlayerService(PlayerRepository playerRepository, ChessDataValidator chessDataValidator) {
        this.playerRepository = playerRepository;
        this.chessDataValidator = chessDataValidator;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<PlayerRatingResponse> getAllPlayerRating() {
        return playerRepository.findAll().stream()
                .map(PlayerToPlayerRatingResponseConvertor::convertPlayerToPlayerRatingResponse)
                .collect(Collectors.toList());
    }

    public Player createPlayer(Player player) throws PlayerValidationException {
        // TODO: Add check for the same ids (if player already exists)
        if (Objects.isNull(player.getFirstName())
                || Objects.isNull(player.getLastName())
                || player.getFirstName().isEmpty()
                || player.getLastName().isEmpty()) {
            //TODO Add LOG
            throw new PlayerValidationException("Cannot create player without first/last name");
        }

        if (Objects.isNull(player.getChessData())
                || player.getChessData().getRating() == 0) {
            //TODO Add LOG
            ChessData chessData = new ChessData();
            chessData.setRating(GlobalConfig.INITIAL_RATING);
            player.setChessData(chessData);
        }
        return playerRepository.save(player);
    }

    public Player updatePlayer(String id, Player playerData) throws PlayerValidationException {
        if (Objects.isNull(id)) {
            //TODO Add LOG
            throw new PlayerValidationException("Player id cannot be null");
        }
        Player player = playerRepository.findById(id).orElse(new Player());

        player.setId(id);
        player.setFirstName(playerData.getFirstName());
        player.setLastName(playerData.getLastName());
        player.setEmail(playerData.getEmail());
        return player;
    }

    public Player getPlayerById(String id) throws PlayerValidationException {
        Optional<Player> player = playerRepository.findById(id);
        if (!player.isPresent()) {
            //todo Add LOG
            throw new PlayerValidationException("Cannot find player with id " + id);
        }
        return player.get();
    }

    public void updateRating(String id, RatingUpdateRequest ratingUpdateRequest) throws PlayerValidationException {
        Player player = getPlayerById(id);
        if (Objects.isNull(ratingUpdateRequest)) {
            throw new PlayerValidationException("Please provide a rating value to update");
        }

        if (Objects.isNull(player.getChessData())) {
            player.setChessData(new ChessData());
        }
        player.getChessData().setRating(ratingUpdateRequest.getRating());
        chessDataValidator.validate(player.getChessData());
        playerRepository.save(player);
    }

    public void deletePlayer(String id) throws PlayerValidationException {
        Player player = getPlayerById(id);
        playerRepository.delete(player);
    }
}
