package com.chessrating.config;

import com.chessrating.repository.PlayerRepository;
import com.chessrating.service.PlayerService;
import com.chessrating.validator.ChessDataValidator;
import com.chessrating.validator.impl.RatingValidatorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public ChessDataValidator chessDataValidator() {
        return new RatingValidatorImpl();
    }

}
