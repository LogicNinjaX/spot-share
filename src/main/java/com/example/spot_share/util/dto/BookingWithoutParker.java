package com.example.spot_share.util.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingWithoutParker(
        UUID bookingId,
        LocalDateTime bookedAt,
        boolean isActive,
        String parkingLocation,
        double pricePerHour,
        String parkingOwner
)
{ }
