package com.example.spot_share.util.dto;

import java.util.UUID;

public class ReviewDto {

    private UUID reviewId;

    private UserDto user; // parker

    private ParkingSpotDto parkingSpot;

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public ParkingSpotDto getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpotDto parkingSpot) {
        this.parkingSpot = parkingSpot;
    }
}
