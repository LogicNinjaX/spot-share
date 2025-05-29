package com.example.spot_share.util.api_response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ReviewRequest {

    @NotNull(message = "booking id is required")
    private UUID bookingId;

    @Min(value = 1, message = "rate between 0-5")
    @Max(value = 5, message = "rate between 0-5")
    private int rating;

    private String comment;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
