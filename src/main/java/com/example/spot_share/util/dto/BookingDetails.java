package com.example.spot_share.util.dto;

import com.example.spot_share.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingDetails(
        UUID bookingId,
        String customerName,
        UUID parkingId,
        String parkingLocation,
        VehicleType vehicleType,
        LocalDateTime bookedAt,
        boolean isActive
) {
}
