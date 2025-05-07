package com.example.spot_share.controller;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.service.ParkingSpotService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.api_response.SavedParkingResponse;
import com.example.spot_share.util.api_response.UpdateParkingSpotRequest;
import com.example.spot_share.util.api_response.UpdatedParkingSpotResponse;
import com.example.spot_share.util.dto.ParkingSpotDto;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import com.example.spot_share.util.dto.RegisterParkingSpot;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ParkingController {

    private final ModelMapper modelMapper;
    private final ParkingSpotService parkingSpotService;

    public ParkingController(ModelMapper modelMapper, ParkingSpotService parkingSpotService) {
        this.modelMapper = modelMapper;
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping("/parking-spots")
    public ResponseEntity<ApiResponse<SavedParkingResponse>> saveParkingSpot(@RequestBody RegisterParkingSpot registerParkingSpot){
        ParkingSpotDto parkingSpotDto = modelMapper.map(registerParkingSpot, ParkingSpotDto.class);
        parkingSpotDto.setParkingStatus(ParkingStatus.AVAILABLE);
        ParkingSpot parkingSpot = parkingSpotService.saveParkingSpot(parkingSpotDto);
        SavedParkingResponse response = modelMapper.map(parkingSpot, SavedParkingResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parking space has been created successfully", response));
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/parking-spots/my")
    public ResponseEntity<ApiResponse<List<ParkingSpotDtoWithoutBookings>>> getParkingList(@RequestParam int pageNumber, @RequestParam int pageSize){
        List<ParkingSpotDtoWithoutBookings> parkingList =  parkingSpotService.getParkingList(pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true,"data retrieved successfully", parkingList));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/parking-spots/{parking-id}")
    public ResponseEntity<ApiResponse<?>> updateParkingSpot(@RequestBody UpdateParkingSpotRequest updateRequest, @PathVariable("parking-id") UUID parkingId){
        ParkingSpotDto parkingSpotDto = parkingSpotService.updateParkingSpot(parkingId,updateRequest);
        UpdatedParkingSpotResponse updateResponse = modelMapper.map(parkingSpotDto, UpdatedParkingSpotResponse.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "parking details updated successfully", updateResponse));
    }
}
