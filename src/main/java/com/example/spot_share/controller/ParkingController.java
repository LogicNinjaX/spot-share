package com.example.spot_share.controller;

import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.enums.ParkingStatus;
import com.example.spot_share.security.CustomUserDetails;
import com.example.spot_share.service.ParkingSpotService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.api_response.SavedParkingResponse;
import com.example.spot_share.util.api_response.UpdateParkingSpotRequest;
import com.example.spot_share.util.api_response.UpdatedParkingSpotResponse;
import com.example.spot_share.util.dto.ParkingSpotDto;
import com.example.spot_share.util.dto.ParkingSpotDtoWithoutBookings;
import com.example.spot_share.util.dto.RegisterParkingSpot;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/parking-spots")
    public ResponseEntity<ApiResponse<SavedParkingResponse>> saveParkingSpot(
            @Valid @RequestBody RegisterParkingSpot registerParkingSpot,
            @AuthenticationPrincipal CustomUserDetails user
    ){

        ParkingSpotDto parkingSpotDto = modelMapper.map(registerParkingSpot, ParkingSpotDto.class);
        parkingSpotDto.setParkingStatus(ParkingStatus.AVAILABLE);
        ParkingSpot parkingSpot = parkingSpotService.saveParkingSpot(user.getUserId(), parkingSpotDto);
        SavedParkingResponse response = modelMapper.map(parkingSpot, SavedParkingResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "parking space has been created successfully", response));
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/parking-spots/my")
    public ResponseEntity<ApiResponse<List<ParkingSpotDtoWithoutBookings>>> getParkingList(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        List<ParkingSpotDtoWithoutBookings> parkingList =  parkingSpotService.getParkingList(user.getUserId(), pageNumber, pageSize);
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

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/parking-spots/{parking-id}")
    public ResponseEntity<ApiResponse<?>> deleteParkingSpot(@PathVariable("parking-id") UUID parkingId){
        parkingSpotService.deleteParking(parkingId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "deleted successfully", null));
    }


    @GetMapping("/parking-spots")
    public ResponseEntity<ApiResponse<?>> getAvailableParkingSpot(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize){
        List<ParkingSpotDtoWithoutBookings> availableParkingSpot = parkingSpotService
                .getParkingListByStatus(pageNumber, pageSize, ParkingStatus.AVAILABLE);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "data retrieved successfully", availableParkingSpot));
    }
}
