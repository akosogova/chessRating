package com.chessrating.validator;

import com.chessrating.AppTestConfiguration;
import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.chessrating.config.GlobalConfig.MAX_RATING;
import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppTestConfiguration.class})
public class RatingValidatorTest {
    @Autowired
    ChessDataValidator chessDataValidator;
    @Test
    public void validateRatingTest() {
        ChessData chessData = ChessData.builder().build();
        chessData.setRating(4999);
        assertDoesNotThrow(() -> chessDataValidator.validate(chessData));
    }

    @Test
    public void validateRating0Test() {
        ChessData chessData = ChessData.builder().build();
        chessData.setRating(0);
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                chessDataValidator.validate(chessData));
        String expectedMessage = "Cannot set chess rating 0. Chess rating should be in range [1, " + (MAX_RATING - 1) + "]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validateRating5000Test() {
        ChessData chessData = ChessData.builder().build();
        chessData.setRating(5000);
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                chessDataValidator.validate(chessData));
        String expectedMessage = "Cannot set chess rating 5000. Chess rating should be in range [1, " + (MAX_RATING - 1) + "]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
