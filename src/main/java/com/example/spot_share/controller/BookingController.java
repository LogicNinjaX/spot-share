package com.example.spot_share.controller;

import com.example.spot_share.entity.Booking;
import com.example.spot_share.entity.ParkingSpot;
import com.example.spot_share.service.BookingService;
import com.example.spot_share.util.api_response.ApiResponse;
import com.example.spot_share.util.api_response.BookingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private final ModelMapper modelMapper;
    private final BookingService bookingService;

    public BookingController(ModelMapper modelMapper, BookingService bookingService) {
        this.modelMapper = modelMapper;
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasRole('PARKER')")
    @PostMapping("/bookings/{parking-id}")
    public ResponseEntity<ApiResponse<BookingResponse>> saveBooking(@PathVariable("parking-id") UUID parkingId){
        Booking booking = bookingService.saveBooking(parkingId);
        BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "booking confirmed", bookingResponse));
    }
}
