package com.example.spot_share.util.dto;

import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ParkingSpotDtoWithoutBookings(
        UUID parkingId,
        String location,
        double pricePerHour,
        VehicleType vehicleType,
        ParkingStatus parkingStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
