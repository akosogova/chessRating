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
    private Role role;
    private ChessData chessData;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        return Objects.equals(id, player.id) && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(email, player.email) && role == player.role && Objects.equals(chessData, player.chessData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, role, chessData);
    }
}