package com.chessrating.api.controller;

import com.chessrating.api.model.PlayerRatingResponse;
import com.chessrating.api.model.RatingUpdateRequest;
import com.chessrating.api.model.Role;
import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.Player;
import com.chessrating.service.PlayerService;
import com.chessrating.service.ValidateActionAllowedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/chess-rating")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final ValidateActionAllowedService validateActionAllowedService;

    @RequestMapping(value = "/players/rating", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerRatingResponse> getAllPlayers(){
        return playerService.getAllPlayersRating();
    }

    @RequestMapping(value = "/player", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPlayer(@RequestBody Player player) throws PlayerValidationException {
        playerService.createPlayer(player);
        return ResponseEntity.ok("The player has been successfully added");
    }

    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePlayer(@PathVariable("id") String id) throws PlayerValidationException {
        playerService.deletePlayer(id);
        return ResponseEntity.ok("The player " + id + " has been successfully deleted");
    }

    @RequestMapping(value = "/player/rating/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updatePlayerRating(@PathVariable("id") String id, @RequestBody RatingUpdateRequest ratingUpdateRequest) throws PlayerValidationException {

        if (!validateActionAllowedService.
                validateActionAllowed("ROLE_" + Role.USER, id)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Cannot update user rating with id " + id);
        }

        playerService.updateRating(id, ratingUpdateRequest);
        return ResponseEntity.ok("The rating has been successfully updated");
    }

}
