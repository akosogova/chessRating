package com.chessrating.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
public class ChessData {
    private int rating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessData chessData = (ChessData) o;
        return rating == chessData.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }
}
