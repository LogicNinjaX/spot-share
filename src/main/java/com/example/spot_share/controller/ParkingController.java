package com.example.spot_share.controller;

import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.service.ParkingSpotService;
import com.example.spot_share.util.dto.ParkingSpotDto;
import com.example.spot_share.util.dto.RegisterParkingSpot;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ParkingController {

    private final ModelMapper modelMapper;
    private final ParkingSpotService parkingSpotService;

    public ParkingController(ModelMapper modelMapper, ParkingSpotService parkingSpotService) {
        this.modelMapper = modelMapper;
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping("/parking-spaces")
    public String saveParkingSpot(@RequestBody RegisterParkingSpot registerParkingSpot){
        ParkingSpotDto parkingSpotDto = modelMapper.map(registerParkingSpot, ParkingSpotDto.class);
        parkingSpotDto.setParkingStatus(ParkingStatus.AVAILABLE);
        parkingSpotService.saveParkingSpot(parkingSpotDto);
        return "saved";
    }
}
