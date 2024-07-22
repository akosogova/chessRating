package com.chessrating.validator.impl;

import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;
import com.chessrating.validator.ChessDataValidator;

import java.util.Objects;

import static com.chessrating.config.GlobalConfig.MAX_RATING;

public class RatingValidatorImpl implements ChessDataValidator {
    @Override
    public void validate(ChessData chessData) throws PlayerValidationException {
        if (Objects.isNull(chessData)) {
            throw new PlayerValidationException("Please provide a chessData with rating");
        }
        int rating = chessData.getRating();
        if (rating <= 0 || rating >= MAX_RATING) {
            throw new PlayerValidationException("Cannot set chess rating " + rating + ". Chess rating should be in range (0, 5000)");
        }
    }
}
