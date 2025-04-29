package com.example.spot_share.util.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingDto {

    private UUID bookingId;

    private UserDto parker;

    private ParkingSpotDto parkingSpot;

    private LocalDateTime bookedAt;

    private boolean isActive = true;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UserDto getParker() {
        return parker;
    }

    public void setParker(UserDto parker) {
        this.parker = parker;
    }

    public ParkingSpotDto getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpotDto parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
