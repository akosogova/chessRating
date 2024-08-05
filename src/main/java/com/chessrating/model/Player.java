package com.chessrating.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ChessData chessData;

    //keycloak user id
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ChessData getChessData() {
        return chessData;
    }

    public void setChessData(ChessData chessData) {
        this.chessData = chessData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(email, player.email) && Objects.equals(chessData, player.chessData) && Objects.equals(userId, player.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, chessData, userId);
    }
}
