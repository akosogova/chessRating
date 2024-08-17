package com.chessrating.validator.impl;

import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;
import com.chessrating.validator.ChessDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.chessrating.config.GlobalConfig.MAX_RATING;

public class RatingValidatorImpl implements ChessDataValidator {
    private static final Logger LOG = LoggerFactory.getLogger(RatingValidatorImpl.class);
    @Override
    public void validate(ChessData chessData) throws PlayerValidationException {
        if (Objects.isNull(chessData)) {
            LOG.error("[RatingValidatorImpl::validate] Need to provide a chessData with rating");
            throw new PlayerValidationException("Please provide a chessData with rating");
        }
        int rating = chessData.getRating();
        if (rating <= 0 || rating >= MAX_RATING) {
            LOG.error("[RatingValidatorImpl::validate] Cannot set chess rating {}. Chess rating should be in range [1, {})", rating, MAX_RATING - 1);
            throw new PlayerValidationException("Cannot set chess rating " + rating + ". Chess rating should be in range [1, " + (MAX_RATING - 1) + "]");
        }
    }
}
