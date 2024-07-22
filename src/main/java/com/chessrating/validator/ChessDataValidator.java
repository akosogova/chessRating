package com.chessrating.validator;

import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;

public interface ChessDataValidator {
    public void validate(ChessData chessData) throws PlayerValidationException;
}
