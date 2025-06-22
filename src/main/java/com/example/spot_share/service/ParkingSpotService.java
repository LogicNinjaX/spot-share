package com.example.spot_share.service;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.util.api_response.UpdateParkingSpotRequest;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import com.example.spot_share.util.dto.ParkingSpotDto;

import java.util.List;
import java.util.UUID;

public interface ParkingSpotService {

    ParkingSpot saveParkingSpot(UUID userId, ParkingSpotDto parkingSpotDto);

    List<ParkingSpotDtoWithoutBookings> getParkingList(UUID userId, int pageNumber, int pageSize);

    ParkingSpotDto updateParkingSpot(UUID parkingId, UpdateParkingSpotRequest request);

    void deleteParking(UUID parkingId);

    List<ParkingSpotDtoWithoutBookings> getParkingListByStatus(int pageNumber, int pageSize, ParkingStatus status);
}
