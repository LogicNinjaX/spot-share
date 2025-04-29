package com.example.spot_share.util.dto;

import com.example.spot_share.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserDto {

    private UUID userId;

    private String username;

    private String password;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<ParkingSpotDto> parkingSpots;

    private List<BookingDto> bookings;

    private List<ReviewDto> reviews;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ParkingSpotDto> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpotDto> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public List<BookingDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDto> bookings) {
        this.bookings = bookings;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }
}
