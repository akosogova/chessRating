package com.chessrating.model;

import java.util.Objects;

public class ChessData {
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

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

    @Override
    public String toString() {
        return "ChessData{" +
                "rating=" + rating +
                '}';
    }
}
