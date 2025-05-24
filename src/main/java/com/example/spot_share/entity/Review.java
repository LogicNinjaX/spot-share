package com.example.spot_share.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    @ManyToOne
    private User user; // parker

    @ManyToOne
    private ParkingSpot parkingSpot;

    private String comment;

    private int rating;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Review() {
    }

    public Review(User user, ParkingSpot parkingSpot, String comment, int rating) {
        this.user = user;
        this.parkingSpot = parkingSpot;
        this.comment = comment;
        this.rating = rating;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}
