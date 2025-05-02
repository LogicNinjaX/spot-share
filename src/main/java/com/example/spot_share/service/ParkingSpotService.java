package com.example.spot_share.service;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.util.dto.ParkingSpotDto;

public interface ParkingSpotService {

    ParkingSpot saveParkingSpot(ParkingSpotDto parkingSpotDto);
}
