package com.chessrating.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingUpdateRequest {
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
        RatingUpdateRequest that = (RatingUpdateRequest) o;
        return rating == that.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating);
    }
}
