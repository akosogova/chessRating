package com.chessrating.api.controller;

import com.chessrating.api.model.PlayerRatingResponse;
import com.chessrating.api.model.RatingUpdateRequest;
import com.chessrating.api.model.Role;
import com.chessrating.exception.PlayerValidationException;
import com.chessrating.model.Player;
import com.chessrating.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/chess-rating")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping(value = "/players/rating", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerRatingResponse> getAllPlayers(){
        return playerService.getAllPlayerRating();
    }

    @RequestMapping(value = "/player", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPlayer(@RequestBody Player player) {
        try {
            playerService.createPlayer(player);
            return ResponseEntity.ok("The player has been successfully added");
        } catch (PlayerValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot add player: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot add player1: " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePlayer(@PathVariable("id") String id) {
        try {
            playerService.deletePlayer(id);
            return ResponseEntity.ok("The player " + id + " has been successfully deleted");
        } catch (PlayerValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete the player with id " + id + ". " + ex.getMessage());
        }
    }

    @PutMapping("/player/rating/{id}")
    public ResponseEntity<String> updatePlayerRating(@PathVariable("id") String id, @RequestBody RatingUpdateRequest ratingUpdateRequest) {
        try {
            boolean isUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(("ROLE_" + Role.USER)::equals);
            if (isUserRole) {
                String userId = SecurityContextHolder.getContext().getAuthentication().getName();

                if (!id.equals(userId)) {
                    throw new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED, "Cannot update user rating with id " + id);
                }
            }
            playerService.updateRating(id, ratingUpdateRequest);
            return ResponseEntity.ok("The rating has been successfully updated");
        } catch (PlayerValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot update rating: " + ex.getMessage());
        }
    }
}
