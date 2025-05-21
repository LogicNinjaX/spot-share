package com.example.spot_share.util.api_response;


import java.time.LocalDateTime;
import java.util.UUID;

public class BookingResponse {

    private UUID bookingId;

    private LocalDateTime bookedAt;

    private boolean isActive = true;

    public BookingResponse() {
    }

    public BookingResponse(UUID bookingId, LocalDateTime bookedAt, boolean isActive) {
        this.bookingId = bookingId;
        this.bookedAt = bookedAt;
        this.isActive = isActive;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
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
