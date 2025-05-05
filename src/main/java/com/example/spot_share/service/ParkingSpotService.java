package com.example.spot_share.service;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import com.example.spot_share.util.dto.ParkingSpotDto;

import java.util.List;

public interface ParkingSpotService {

    ParkingSpot saveParkingSpot(ParkingSpotDto parkingSpotDto);


    List<ParkingSpotDtoWithoutBookings> getParkingList(int pageNumber, int pageSize);
}
