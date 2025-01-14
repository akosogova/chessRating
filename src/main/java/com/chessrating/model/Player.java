package com.chessrating.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@Builder
@ToString

public class Player {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ChessData chessData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(email, player.email) && Objects.equals(chessData, player.chessData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, chessData);
    }
}
