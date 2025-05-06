package com.example.spot_share.util.api_response;

import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.enums.VehicleType;

public class UpdateParkingSpotRequest {

    private String location;

    private double pricePerHour;

    private VehicleType vehicleType;

    private ParkingStatus parkingStatus;

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
}
