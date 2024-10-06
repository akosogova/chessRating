package com.chessrating.service;

import com.chessrating.AppTestConfiguration;
import com.chessrating.api.model.RatingUpdateRequest;
import com.chessrating.builder.PlayerBuilder;
import com.chessrating.config.GlobalConfig;
import com.chessrating.convertor.PlayerConvertor;
import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.ChessData;
import com.chessrating.model.Player;
import com.chessrating.repository.PlayerRepository;
import com.chessrating.validator.ChessDataValidator;
import com.chessrating.validator.impl.RatingValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.chessrating.config.GlobalConfig.MAX_RATING;
import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {AppTestConfiguration.class})
public class PlayerServiceTest {
    private final PlayerRepository playerRepository = Mockito.mock(PlayerRepository.class);
    private final ChessDataValidator chessDataValidator = new RatingValidatorImpl();
    private final PlayerService playerService = new PlayerService(playerRepository, chessDataValidator);

    @BeforeEach
    public void resetMocks() {
        Mockito.reset(playerRepository);
    }

    @Test
    public void createPlayerTest() throws PlayerValidationException {
        Player player = new PlayerBuilder()
                .withDefaultValues()
                .build();

        playerService.createPlayer(player);
        assertEquals("1111", player.getId());
    }

    @Test
    public void createNullPlayerTest() {
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.createPlayer(null));
        String expectedMessage = "Cannot create empty player";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createPlayerWithoutIdTest() {
        Player playerWithoutId = new PlayerBuilder()
                .setFirstName("Name")
                .build();
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.createPlayer(playerWithoutId));
        String expectedMessage = "Please set player id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createPlayerWithWrongRatingTest() {
        Player player = new PlayerBuilder()
                .setId("1111")
                .setRating(-10)
                .build();

        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.createPlayer(player));
        String expectedMessage = "Cannot set chess rating -10. Chess rating should be in range [1, " + (MAX_RATING - 1) + "]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createPlayerWithExistedIdTest() {
        Player player = new PlayerBuilder()
                .withDefaultValues()
                .build();
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)));
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.createPlayer(player));
        String expectedMessage = "A player with the given id 1111 already exists, please provide a different id";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createPlayerWithoutRatingTest() throws PlayerValidationException {
        Player playerWithoutRating = new PlayerBuilder()
                .setId("1111")
                .build();

        playerService.createPlayer(playerWithoutRating);

        assertEquals(GlobalConfig.INITIAL_RATING, playerWithoutRating.getChessData().getRating());
    }

    @Test
    public void getPlayerByIdTest() throws PlayerValidationException {
        Player player = new PlayerBuilder()
                .withDefaultValues()
                .build();
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)));
        Player actualPlayer = playerService.getPlayerById("1111");

        assertTrue(player.equals(actualPlayer));
    }

    @Test
    public void getPlayerUnknownByIdTest() {
        Mockito.when(playerRepository.findById("1111")).thenReturn(Optional.empty());
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.getPlayerById("1111"));
        String expectedMessage = "Cannot find player with id 1111";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateRatingTest() throws PlayerValidationException {
        Player player = new PlayerBuilder()
                .withDefaultValues()
                .build();
        Mockito.when(playerRepository.findById(player.getId()))
                .thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)))
                .thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)));
        RatingUpdateRequest ratingUpdateRequest = new RatingUpdateRequest();
        ratingUpdateRequest.setRating(2000);

        playerService.updateRating("1111", ratingUpdateRequest);
        player.getChessData().setRating(2000);
        verify(playerRepository, times(1)).save(PlayerConvertor.convertPlayer(player));
    }

    @Test
    public void updateRatingNullRatingUpdateRequestTest() {
        Player player = new PlayerBuilder()
                .withDefaultValues()
                .build();
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)));
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.updateRating("1111", null));
        String expectedMessage = "Please provide a rating value to update";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateRatingUnknownPlayerTest() {
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.updateRating("1111", null));
        String expectedMessage = "Cannot find player with id 1111";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void updateRatingForPlayerWithoutChessDataTest() throws PlayerValidationException {
        Player player = new PlayerBuilder()
                .setId("1111")
                .build();
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)));
        RatingUpdateRequest ratingUpdateRequest = new RatingUpdateRequest();
        ratingUpdateRequest.setRating(2000);
        playerService.updateRating("1111", ratingUpdateRequest);

        player.setChessData(ChessData.builder().rating(2000).build());
        verify(playerRepository, times(1)).save(PlayerConvertor.convertPlayer(player));

    }

    @Test
    public void updateRatingWithWrongRatingTest() {
        Player player = new PlayerBuilder()
                .withDefaultValues()
                .build();
        Mockito.when(playerRepository.findById(player.getId())).thenReturn(Optional.of(PlayerConvertor.convertPlayer(player)));
        RatingUpdateRequest ratingUpdateRequest = new RatingUpdateRequest();
        ratingUpdateRequest.setRating(10000);

        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.updateRating("1111", ratingUpdateRequest));
        String expectedMessage = "Cannot set chess rating 10000. Chess rating should be in range [1, " + (MAX_RATING - 1) + "]";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void deleteUnknownPlayerTest() throws PlayerValidationException {
        Throwable exception = assertThrows(PlayerValidationException.class, () ->
                playerService.deletePlayer("1111"));
        String expectedMessage = "Cannot find player with id 1111";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
