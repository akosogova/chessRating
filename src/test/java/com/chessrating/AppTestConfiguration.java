package com.chessrating;

import com.chessrating.repository.PlayerRepository;
import com.chessrating.service.PlayerService;
import com.chessrating.validator.ChessDataValidator;
import com.chessrating.validator.impl.RatingValidatorImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class AppTestConfiguration {
    @Bean
    public PlayerRepository playerRepository() {
        return Mockito.mock(PlayerRepository.class);
    }

    @Bean
    PlayerService playerService() {
        return new PlayerService();
    }

    @Bean
    ChessDataValidator chessDataValidator() {
        return new RatingValidatorImpl();
    }
}
