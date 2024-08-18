package com.chessrating.builder;

import com.chessrating.model.ChessData;
import com.chessrating.model.Player;

import java.util.Objects;

public class PlayerBuilder {
    private Player player;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ChessData chessData;

    public PlayerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public PlayerBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PlayerBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PlayerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public PlayerBuilder setChessData(ChessData chessData) {
        this.chessData = chessData;
        return this;
    }

    public PlayerBuilder setRating(int rating) {
        if (Objects.isNull(chessData)) {
            chessData = new ChessData();
        }
        chessData.setRating(rating);
        return this;
    }

    public PlayerBuilder withDefaultValues() {
        this.id = "1111";
        this.firstName = "First";
        this.lastName = "Last";
        this.email = "first.last@mail.com";
        ChessData chessData = new ChessData();
        chessData.setRating(1500);
        this.chessData = chessData;
        return this;
    }

    public Player build() {
        Player player = new Player();
        player.setId(id);
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setEmail(email);
        player.setChessData(chessData);
        return player;
    }
}
