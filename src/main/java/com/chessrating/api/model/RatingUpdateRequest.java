package com.chessrating.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingUpdateRequest {
    private int rating;

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
