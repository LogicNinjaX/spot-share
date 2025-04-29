package com.example.spot_share.util.dto;

import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.enums.VehicleType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ParkingSpotDto {

    private UUID parkingId;

    private UserDto owner; // owner data

    private String location;

    private double pricePerHour;

    private VehicleType vehicleType;

    private ParkingStatus parkingStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<BookingDto> bookings;

    public UUID getParkingId() {
        return parkingId;
    }

    public void setParkingId(UUID parkingId) {
        this.parkingId = parkingId;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ParkingStatus getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(ParkingStatus parkingStatus) {
        this.parkingStatus = parkingStatus;
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

    public List<BookingDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDto> bookings) {
        this.bookings = bookings;
    }
}
