package com.chessrating.repository.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Setter
@Getter
@Builder
@ToString
@Document(collection = "players")

public class PlayerEntity {
    @Id
    //keycloak user id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ChessDataEntity chessData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerEntity player = (PlayerEntity) o;
        return Objects.equals(id, player.id) && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(email, player.email) && Objects.equals(chessData, player.chessData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, chessData);
    }
}
